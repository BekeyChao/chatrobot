package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;
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

    // 提醒取消
    private static final String CANCEL = "提醒取消";

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        String text = message.getText();
        if (text.startsWith(REMIND)) {
            return alarmCreate(message);
        }
        if (text.startsWith(CANCEL)) {
            return alarmCancel(message);
        }
        return null;
    }

    private String alarmCreate(BaseMsg message) {
        String text = message.getText();
        BaseSceneContext context = SceneContextHolder.getSceneBySceneId("AlarmCreate");
        if (text.contains("提醒我")) {
            return context.act(message.getFromUserName(), message);
        }
        SceneContextHolder.setArgumentsByUserId(message.getFromUserName(), new Object[]{context});
        return "请按 yyyy-MM-dd HH:mm:ss 提醒我 提醒内容 格式回复，其中具体时间可选，默认为早上8点";
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
        if (text.startsWith(REMIND) || text.startsWith(CANCEL)) {
            return Decision.ACCEPT;
        }
        return Decision.PASS;
    }
}
