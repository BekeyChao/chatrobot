package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;

/**
 * 模块指令响应器， 用于开启或关闭模块功能
 * 初步打算实现 日程提醒及 聊天机器人模块
 */
@Service
public class ModuleProcess implements TextProcessor{
    private static final String OPEN_CHAT = "聊天机器人";

    private static final String CLOSE_CHAT = "你可以滚了";

    private static final String[] KEY_WORD_LIST = {OPEN_CHAT, CLOSE_CHAT};

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        String text = message.getText();
        if (text.equals(OPEN_CHAT)) {
            return "如果可以，我想陪你聊到天荒地老";
        }
        if (text.equals(CLOSE_CHAT)) {
            return "我挥一挥衣袖，不带走一片云彩";
        }
        throw new AnswerException("不存在可以响应的命令");
    }

    @Override
    public Decision decide(BaseMsg message) {
        String text = message.getText();
        for (String key : KEY_WORD_LIST) {
            if (key.equals(text)) {
                return Decision.ACCEPT;
            }
        }
        return Decision.PASS;
    }
}
