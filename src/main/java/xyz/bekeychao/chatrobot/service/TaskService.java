package xyz.bekeychao.chatrobot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.domain.AlarmFuture;
import xyz.bekeychao.chatrobot.service.manager.ScheduleFutureHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskService {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private ScheduleFutureHolder futureHolder;

    /**
     * 以corn表达式创建一个任务
     * 因为有时区问题， 所以以corn表达式创建任务可能因为时区而产生异常
     * 所以尽量避免
     * @param runnable
     * @param cron
     * @return
     */
    @Deprecated
    public AlarmFuture scheduleCron(Runnable runnable, String cron) {
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        return put(future, cron, null, null);
    }

    /**
     * 以固定时间创建一个单次任务
     * @param runnable
     * @param time
     * @return
     */
    public AlarmFuture scheduleOnce(Runnable runnable, LocalDateTime time) {
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnable, convertLDTtoDate(time));
        return put(future, null, time, null);
    }

    /**
     * 以固定时间为开始， 创建一个循环任务
     * @param runnable
     * @param time
     * @param rate
     * @return
     */
    public AlarmFuture scheduleFixedRate(Runnable runnable, LocalDateTime time, long rate) {
        ScheduledFuture<?> future = threadPoolTaskScheduler.scheduleAtFixedRate(runnable, convertLDTtoDate(time), rate);
        return put(future, null, time, rate);
    }

    private AlarmFuture put(ScheduledFuture<?> future, String cron, LocalDateTime time, Long rate) {
//        if (futureHolder == null)
        String uuid = UUID.randomUUID().toString();
        futureHolder.putFuture(uuid, future);
        return new AlarmFuture(uuid, null, cron, null);
    }

    /**
     * 将{@link LocalDateTime} 对象转化为 {@link Date} 北京时间
     * @return Data instant
     */
    private static Date convertLDTtoDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public boolean stopTask(String uuid) {
        return futureHolder.getFuture(uuid).cancel(false);
    }

}
