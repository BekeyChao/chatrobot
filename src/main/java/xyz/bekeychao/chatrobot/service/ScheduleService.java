package xyz.bekeychao.chatrobot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.domain.Alarm;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.repository.AlarmRepository;
import xyz.bekeychao.chatrobot.service.manager.ScheduleFutureHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

/**
 * @author BekeyChao@github.com
 */
@Service
public class ScheduleService {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final ScheduleFutureHolder futureHolder;
    // 存在循环引用，只好直接依赖dao层
//    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;

    @Autowired
    public ScheduleService(ThreadPoolTaskScheduler threadPoolTaskScheduler, ScheduleFutureHolder futureHolder, AlarmRepository alarmRepository) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.futureHolder = futureHolder;
        this.alarmRepository = alarmRepository;
    }

    /**
     * 以corn表达式创建一个任务
     * 因为有时区问题， 所以以corn表达式创建任务可能因为时区而产生异常
     * @param cron
     * @return 任务的UUID
     */
    public String scheduleCron(String userId, String content, String cron) {
        AlarmRunnable runnable = productRunnableAndSaveAlarm(userId, content, cron, null, null);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        put(future, runnable.getUuid());
        return runnable.getUuid();
    }

    /**
     * 以固定时间为开始， 创建一个循环任务
     * @return 任务的UUID
     */
    public String scheduleFixedRate(String userId, String content, LocalDateTime time, long rate) {
        AlarmRunnable runnable = productRunnableAndSaveAlarm(userId, content, null, time, rate);
        ScheduledFuture<?> future = threadPoolTaskScheduler.scheduleAtFixedRate(runnable, convertLDTtoDate(time), rate);
        put(future, runnable.getUuid());
        return runnable.getUuid();
    }

    private void put(ScheduledFuture<?> future, String uuid) {
        futureHolder.putFuture(uuid, future);
    }

    /**
     * 按照任务uuid取消定时任务
     * @param uuid
     * @return
     */
    public boolean cancelSchedule(String uuid) {
        ScheduledFuture<?> future = futureHolder.getFuture(uuid);
        if (future == null) {
            return false;
        }
        return future.cancel(false);
    }

    public String scheduleOnce(String userId, String content, LocalDateTime dateTime) {
        AlarmRunnable runnable = productRunnableAndSaveAlarm(userId, content, null, dateTime, null);
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(runnable, convertLDTtoDate(dateTime));
        put(schedule, runnable.getUuid());
        return runnable.getUuid();
    }

    /**
     */
    private AlarmRunnable productRunnableAndSaveAlarm(String userId, String content, String cron, LocalDateTime dateTime, Long rate) {
        Alarm alarm = new Alarm(userId, content, cron, dateTime, rate, null, LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        alarm.setUuid(uuid);
        // 持久化 -- 目的是在重启之后可以恢复数据 但是还没实现
        alarmRepository.save(alarm);
        return new AlarmRunnable(uuid);
    }

    /**
     * 将{@link LocalDateTime} 对象转化为 {@link Date} 北京时间
     * @return Data instant
     */
    private static Date convertLDTtoDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
