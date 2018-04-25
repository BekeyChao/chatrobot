package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.domain.AlarmFuture;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.TaskService;
import xyz.bekeychao.chatrobot.service.manager.ScheduleFutureHolder;
import xyz.bekeychao.chatrobot.util.RegularUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author BekeyChao@github.com
 */
@Component
public class AlarmCreateScene implements BaseSceneContext{
    private static LocalTime AM8 = LocalTime.of(8,0 );

    private Logger logger = LoggerFactory.getLogger(AlarmCreateScene.class);
//    @Autowired
//    private SceneContextHolder sceneContextHolder;

    private final TaskService taskService;


    @Autowired
    public AlarmCreateScene(TaskService taskService, ScheduleFutureHolder scheduleFutureHolder) {
        this.taskService = taskService;
    }

    @Override
    public String sceneId() {
        return "AlarmCreate";
    }

    @Override
    public String act(String userId, BaseMsg message){

        String text = message.getText();
        String[] spilt = text.split("提醒我");
        if (spilt.length < 2 || spilt[1].trim().equals("")) {
            return "我有点笨，按指定格式回复我吧，缺少提醒内容，请重新按 定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 内容 定制哦！";
        }
        // 语义分析
        try {
//            LocalDateTime date = LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate date;LocalTime time = AM8;
            String dateString = RegularUtil.convertDate(spilt[0]);
            if (dateString == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(Arrays.toString(spilt));
                }
                return "我有点笨，按指定格式回复我吧。重新按 定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 内容 定制哦！";
            }
            date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String timeString = RegularUtil.convertTime(spilt[0]);
            if (timeString != null) {
                time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            LocalDateTime dateTime = LocalDateTime.of(date, time);

            if (isBeforeNow(dateTime)) {
                return String.format("你希望我 %s 提醒你，但是臣妾...老子并不打算告诉你你穿越了", dateTime.toString());
            }

            AlarmRunnable runnable = new AlarmRunnable(userId, spilt[1].trim());
            // taskService
            AlarmFuture alarmFuture = taskService.scheduleOnce(runnable, dateTime);
            // TODO 可以将alarmFuture 持久化，达到任务取消的目的
            return String.format("宝宝记住了，我将在 %s 发消息提醒你 %s， 任务ID %s", dateTime.toString(), spilt[1].trim(), alarmFuture.getUuid());
        }  catch (Exception e) {
            logger.warn("未知原因导致创建日程异常" , e);
            return "我可能遇到了什么麻烦，回头再来找我吧";
        }
    }

    private boolean isBeforeNow(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public long express() {
        return -1L;
    }
}
