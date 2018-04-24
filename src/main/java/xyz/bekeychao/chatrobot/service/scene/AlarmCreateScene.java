package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.TaskService;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.util.RegUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AlarmCreateScene implements BaseSceneContext{
    private static LocalTime AM8 = LocalTime.of(8,0 );

    private Logger logger = LoggerFactory.getLogger(AlarmCreateScene.class);
//    @Autowired
//    private SceneContextHolder sceneContextHolder;

    @Autowired
    private TaskService taskService;

    @Override
    public String sceneId() {
        return "AlarmCreate";
    }

    @Override
    public String act(String userId, BaseMsg message) throws SceneException{

        String text = message.getText();
        String[] spilt = text.split("提醒我");
        if (spilt.length < 2 || spilt[1].trim().equals("")) {
            return "我有点笨，按指定格式回复我吧。重新按 定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 内容 定制哦！";
        }
        // 语义分析
        try {
//            LocalDateTime date = LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate date;LocalTime time = AM8;
            String dateString = RegUtil.convertDate(spilt[0]);
            if (dateString == null) {
                return "我有点笨，按指定格式回复我吧！";
            }
            date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String timeString = RegUtil.convertTime(spilt[0]);
            if (timeString != null) {
                time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            LocalDateTime dateTime = LocalDateTime.of(date, time);

            if (isBeforeNow(dateTime)) {
                return String.format("你希望我 %s 提醒你，但是臣妾...老子并不打算告诉你你穿越了", dateTime.toString());
            }

            AlarmRunnable runnable = new AlarmRunnable(userId, spilt[1].trim());
            taskService.scheduleOnce(runnable, dateTime);

            return String.format("宝宝记住了，我将在 %s 发消息提醒 %s", dateTime.toString(), spilt[1].trim());
        }  catch (Exception e) {
            logger.warn("未知原因导致创建日程异常" , e);
            throw new SceneException(e);
        } finally {

            if (isRemovedAfterResponse()) {
                SceneContextHolder.removeArgumentsByUserId(userId);
            }
        }
    }

    private boolean isBeforeNow(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public long express() {
        return 10000;
    }
}
