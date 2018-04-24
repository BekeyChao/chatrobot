package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

/**
 * 上下文管理响应器，用于响应连续的对话内容，未实现
 */
@Service
public class ContextService implements TextProcessor {
//    @Autowired
//    private SceneContextHolder sceneContextHolder;

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        try {
            BaseSceneContext context = (BaseSceneContext)SceneContextHolder.getArgumentsByUserId(message.getFromUserName())[0];
            String answer = context.act(message.getFromUserName(), message);

            if (context.isRemovedAfterResponse()) {
                SceneContextHolder.removeArgumentsByUserId(message.getFromUserName());
            }

            return answer;

        }catch (Exception e) {
            e.printStackTrace();
            throw new AnswerException(e.getMessage());
        }
    }

    @Override
    public Decision decide(BaseMsg message) {
        // 用户场景值有消息   arguments 第一位值为boolean值， 指示用户场景处理状态
        if ( SceneContextHolder.getArgumentsByUserId(message.getFromUserName()) != null) {
            return Decision.ACCEPT;
        }
        return Decision.PASS;
    }
}
