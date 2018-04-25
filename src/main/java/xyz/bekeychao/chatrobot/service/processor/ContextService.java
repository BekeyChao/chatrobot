package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

/**
 * 上下文管理响应器，用于响应连续的对话内容
 * @author BekeyChao@github.com
 */
@Service
public class ContextService implements TextProcessor {
//    @Autowired
//    private SceneContextHolder sceneContextHolder;

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        try {
            // 约定 Arguments 首位是场景对象， 获取之后调用act方案实现响应
            BaseSceneContext context = (BaseSceneContext)SceneContextHolder.getArgumentsByUserId(message.getFromUserName())[0];
            String answer = context.act(message.getFromUserName(), message);
            // 默认响应后就移除场景信息， 如果返回false需自己手动处理，否则消息会一直进入上下文
            if (context.isRemovedAfterResponse()) {
                SceneContextHolder.removeArgumentsByUserId(message.getFromUserName());
            }

            return answer;

        }catch (Exception e) {
            throw new AnswerException(e);
        }
    }

    @Override
    public Decision decide(BaseMsg message) {
        // 用户场景值有消息
        if ( SceneContextHolder.getArgumentsByUserId(message.getFromUserName()) != null) {
            return Decision.ACCEPT;
        }
        return Decision.PASS;
    }
}
