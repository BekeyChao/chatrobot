package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.bekeychao.chatrobot.domain.Alarm;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.service.ScheduleService;
import xyz.bekeychao.chatrobot.util.CronUtil;
import xyz.bekeychao.chatrobot.util.RegularUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 需要考虑的东西有点多
 * @author BekeyChao@github.com
 * @date 2018/4/25
 */
@Component
public class IntervalAlarmCreateScene implements BaseSceneContext {
    private final Logger logger = LoggerFactory.getLogger(IntervalAlarmCreateScene.class);
    public static final String SCENE_ID = "IntervalAlarmCreate";

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public String sceneId() {
        return SCENE_ID;
    }

    @Override
    public String act(String userId, BaseMsg message) {
        // 中文冒号 转 英文冒号
        String text = message.getText().replace("：", ";");

        // 根据 提醒我 分隔， 第一段为时间参数 第二段为内容参数
        String[] spilt = text.split("提醒我");

        if (spilt.length < 2 || spilt[1].trim().equals("")) {
            return "知道我为什么叫人工智障吗？因为你不按我要求的格式来，我就听不懂，┭┮﹏┭┮";
        }
        char modal = text.charAt(0);
        switch (modal) {
            case 'A':
                return dailyAlarm(spilt[0],spilt[1], userId);
            case 'B':
                return weeklyAlarm(spilt[0],spilt[1], userId);
            case 'C':
                return monthlyAlarm(spilt[0],spilt[1], userId);
            default:
                return "莫非你选择了D？ 请在第一个字母就告诉宝宝循环周期好不";
        }
    }

    private String monthlyAlarm(String daytime, String content, String userId) {
        String timeString = RegularUtil.convertTime(daytime);
        if (timeString == null) {
            return "嗯~ o(*￣▽￣*)o  我没看出来你每天提醒的时间啊 ~~~";
        }

        try {
            LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
            String dayOfMonth = daytime.substring(daytime.indexOf("月") + 1, daytime.indexOf("日"));
            Integer day = Integer.parseInt(dayOfMonth);
            if (day < 1 || day > 31) {
                return "设置一个不存在的日期并不好玩";
            }
            String cron = CronUtil.monthlyCorn(time, dayOfMonth);
            logger.info(cron);

            String uuid = scheduleService.scheduleCron(userId, content, cron);
            return String.format("宝宝记住了，我将在每月%s日 %s 发消息提醒你 %s， 任务ID %s", dayOfMonth, time.toString(), content, uuid);
        }catch (NumberFormatException e) {
            return "嗯~ o(*￣▽￣*)o 我没看出来你打算每月几号啊";
        }catch (DateTimeParseException e) {
            return "不按格式设置时间不是好孩子哟， 诺再给你个例子 每月1日 08:00:00";
        }
    }

    private String weeklyAlarm(String daytime, String content, String userId) {
        String timeString = RegularUtil.convertTime(daytime);
        if (timeString == null) {
            return "嗯~ o(*￣▽￣*)o  我没看出来你每天提醒的时间啊 ~~~";
        }
        try {
            LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
            char weekChar = daytime.charAt(daytime.indexOf("星期") + 2);
            String week = convertWeek(weekChar);
            if (week == null) {
                return "嗯~ o(*￣▽￣*)o  我没看出来你每周提醒的日期啊 ~~~";
            }

            String cron = CronUtil.weeklyCorn(time, week);
            logger.info(cron);

            // 创建一个cron任务
            String uuid = scheduleService.scheduleCron(userId, content, cron);

            return String.format("宝宝记住了，我将在每%s %s 发消息提醒你 %s， 任务ID %s", week, time.toString(), content, uuid);
        }catch (DateTimeParseException e) {
            return "不按格式设置时间不是好孩子哟， 诺再给你个例子 每星期天 08:00:00";
        }

    }

    /**
     * 转化星期
     * @param weekChar
     * @return
     */
    private String convertWeek(char weekChar) {
        switch (weekChar) {
            case '一' : return "MON";
            case '二' : return "TUE";
            case '三' : return "WED";
            case '四' : return "THU";
            case '五' : return "FRI";
            case '六' : return "SAT";
            case '天' : return "SUN";
            case '日' : return "SUN";
            default: return null;
        }
    }

    private String dailyAlarm(String daytime, String content, String userId) {
        String timeString = RegularUtil.convertTime(daytime);
        if (timeString == null) {
            return "嗯~ o(*￣▽￣*)o  我没看出来你每天提醒的时间啊 ~~~";
        }
        try {
            LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
            String cron = CronUtil.dailyCorn(time);
            logger.info(cron);

            String uuid = scheduleService.scheduleCron(userId, content, cron);
            return String.format("宝宝记住了，我将在每天 %s 发消息提醒你 %s， 任务ID %s", time.toString(), content, uuid);
        }catch (DateTimeParseException e) {
            return "不按格式设置时间不是好孩子哟， 诺再给你个例子 每天08:00:00";
        }
    }

    @Override
    public long express() {
        return -1L;
    }

    @Override
    public boolean isRemovedAfterResponse() {
        return true;
    }
}
