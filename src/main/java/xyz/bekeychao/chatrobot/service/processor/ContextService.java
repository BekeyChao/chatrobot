package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;

/**
 * 上下文管理响应器，用于响应连续的对话内容，未实现
 */
@Service
public class ContextService implements TextProcessor {
    @Override
    public String answer(BaseMsg messaget) throws AnswerException {
        return null;
    }

    @Override
    public Decision decide(BaseMsg message) {
        return Decision.PASS;
    }
}
