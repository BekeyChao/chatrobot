package xyz.bekeychao.chatrobot.service;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.bekeychao.chatrobot.domain.Alarm;
import xyz.bekeychao.chatrobot.repository.AlarmRepository;
import xyz.bekeychao.chatrobot.service.processor.UserFilter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BekeyChao@github.com
 * @date 2018/5/3
 */
@Service
public class AlarmService {
    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private UserFilter userFilter;

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 保存或更新方法
     * @param alarm
     * @return 对象的uuid
     */
    public String saveOrUpdateAlarm(Alarm alarm) {
        Assert.notNull(alarm, "the entity can not be null");
        Assert.notNull(alarm.getUuid(), "alarm uuid can not be null");
        return alarmRepository.save(alarm).getUuid();
    }

    public Alarm getAlarmByUUID(String uuid) {
        Assert.notNull(uuid, "uuid can not be null");
        return alarmRepository.getOne(uuid);
    }

    /**
     * 发送消息提醒的功能
     * @param uuid
     */
    public void sentScheduleMessage(String uuid) {
        Alarm alarm = getAlarmByUUID(uuid);
        // 检查是否处于服务序列
        if (userFilter.getUsers().contains(alarm.getUserId())) {
            // TODO 应检查微信程序是否处于在线状态 及好友是否在列表
            WechatTools.sendMsgByUserName(alarm.getContent(), alarm.getUserId());
            alarm.setLastRunnableDate(LocalDateTime.now());
            saveOrUpdateAlarm(alarm);
        } else {
            // 不处于服务序列， 将该用户相关的所有提醒设置为删除状态 并从任务池中移除
            List<Alarm> alarms = alarmRepository.getAllByUserId(alarm.getUserId());
            alarms.forEach( it -> {
                it.setDeleted(true);
                scheduleService.cancelSchedule(it.getUuid());
            } );
            alarmRepository.saveAll(alarms);
        }
    }
}
