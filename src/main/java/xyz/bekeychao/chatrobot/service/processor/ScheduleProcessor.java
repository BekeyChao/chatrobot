package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

/**
 * 行程提醒处理器
 * 响应定制提醒及提醒取消
 */
@Service
public class ScheduleProcessor implements TextProcessor{

    // 定制提醒格式  定制提醒 yyyy-MM-dd HH:mm:ss 提醒我 content
    private static final String REMIND = "定制提醒";

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        String text = message.getText();
        BaseSceneContext context = SceneContextHolder.getSceneBySceneId("AlarmCreate");
        if (text.contains("提醒我")) {
            try {
                return context.act(message.getFromUserName(), message);
            } catch (SceneException e) {
                return "创建异常";
            }
        }
        SceneContextHolder.setArgumentsByUserId(message.getFromUserName(), new Object[]{context});
        return "请按 yyyy-MM-dd HH:mm:ss 提醒我 提醒内容 格式回复，其中具体时间可选，默认为早上8点";
    }

    @Override
    public Decision decide(BaseMsg message) {
        String text = message.getText();
        if (text.startsWith(REMIND)) {
            return Decision.ACCEPT;
        }
        return Decision.PASS;
    }
}
