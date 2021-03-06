package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.bekeychao.chatrobot.domain.Alarm;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.service.ScheduleService;
import xyz.bekeychao.chatrobot.service.manager.ScheduleFutureHolder;
import xyz.bekeychao.chatrobot.util.RegularUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * @author BekeyChao@github.com
 */
@Component
public class AlarmCreateScene implements BaseSceneContext{
    private static LocalTime AM8 = LocalTime.of(8,0 );

    private Logger logger = LoggerFactory.getLogger(AlarmCreateScene.class);
//    @Autowired
//    private SceneContextHolder sceneContextHolder;

    private final ScheduleService scheduleService;


    @Autowired
    public AlarmCreateScene(ScheduleService scheduleService, ScheduleFutureHolder scheduleFutureHolder) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String sceneId() {
        return "AlarmCreate";
    }

    @Override
    public String act(String userId, BaseMsg message){

        // 中文冒号 转 英文冒号
        String text = message.getText().replace("：", ";");
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
                return "我有点笨，按指定格式回复我吧。重新发 定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 提醒内容 定制哦！";
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

            // taskService里面执行的流程比较多
            String uuid = scheduleService.scheduleOnce(userId, spilt[1].trim(), dateTime);

            return String.format("宝宝记住了，我将在 %s 发消息提醒你 %s， 任务ID %s", dateTime.toString(), spilt[1].trim(), uuid);
        } catch (DateTimeParseException e) {
            return "不按格式设置时间不是好孩子哟， 诺再给你个例子 2018-05-20 08:00:00";
        }catch (Exception e) {
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
