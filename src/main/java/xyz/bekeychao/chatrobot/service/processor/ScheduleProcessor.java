package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;
import xyz.bekeychao.chatrobot.service.scene.IntervalAlarmCreateScene;
import xyz.bekeychao.chatrobot.util.RegularUtil;

/**
 * 行程提醒处理器
 * 响应定制提醒及提醒取消
 * @author BekeyChao@github.com
 */
@Service
public class ScheduleProcessor implements TextProcessor{

    // 定制提醒格式  定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 content
    private static final String REMIND = "定制提醒";

    // 提醒取消格式 提醒取消 ID UUID
    private static final String CANCEL = "提醒取消";

    // 周期提醒格式 周期提醒 （按多次交互获得数据）
    private static final String INTERVAL_REMIND = "周期提醒";

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        String text = message.getText();
        if (text.startsWith(REMIND)) {
            return alarmCreate(message);
        }
        if (text.startsWith(CANCEL)) {
            return alarmCancel(message);
        }
        if (text.startsWith(INTERVAL_REMIND)) {
            return interval_alarm(message);
        }
        return null;
    }

    private String interval_alarm(BaseMsg message) {
        BaseSceneContext context = SceneContextHolder.getSceneBySceneId(IntervalAlarmCreateScene.SCENE_ID);
        SceneContextHolder.setArgumentsByUserId(message.getFromUserName(), new Object[]{ context });

        return "请选择循环周期，并按指定模板回复 \r\n" +
                "A 每天 模板 A 每天08:00:00 提醒我 提醒内容 \r\n" +
                "B 每星期 模板 B 每星期一 08:00:00 提醒我 提醒内容 // 周末表达为星期天或星期日 \r\n" +
                "C 每月 模板 C 每月15日 08:00:00 提醒我 提醒内容";
//                "循环周期 请回复 A每天 | B每星期 | C每月 | D每年 | E取消设置 五个选项选一";
    }

    private String alarmCreate(BaseMsg message) {
        String text = message.getText();
        BaseSceneContext context = SceneContextHolder.getSceneBySceneId("AlarmCreate");
        if (text.contains("提醒我")) {
            return context.act(message.getFromUserName(), message);
        }
        SceneContextHolder.setArgumentsByUserId(message.getFromUserName(), new Object[]{context});
        return "请按 yyyy-MM-dd HH:mm:ss 提醒我 提醒内容 格式回复，其中具体时间可选，默认为早上8点 \r\n" +
                "例如 2018-05-20 08:00:00 提醒我 情人劫到了";
    }

    private String alarmCancel(BaseMsg message) {
        BaseSceneContext context = SceneContextHolder.getSceneBySceneId("AlarmCancel");
        if (message.getText().toUpperCase().contains("ID")) {
            return context.act(message.getFromUserName(), message);
        }
        SceneContextHolder.setArgumentsByUserId(message.getFromUserName(), new Object[]{context});
        return "请按 ID 任务的UUID 格式回复，UUID指的是创建提醒时给你的ID";
    }

    @Override
    public Decision decide(BaseMsg message) {
        String text = message.getText();
        if (text.startsWith(REMIND) || text.startsWith(CANCEL) || text.startsWith(INTERVAL_REMIND)) {
            return Decision.ACCEPT;
        }
        return Decision.PASS;
    }
}
