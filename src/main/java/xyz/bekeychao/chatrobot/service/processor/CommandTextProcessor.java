package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;

/**
 * 强指令响应器， 用于响应固定系统基本指令， 如开启机器人， 关闭机器人等
 * 强指令响应应有最高优先级，与严格的匹配模式
 * @author BekeyChao@github.com
 */
@Service
public class CommandTextProcessor implements TextProcessor {

    private final UserFilter userFilter;

    private static final String ACTIVE = "天王盖地虎";
    private static final String DISABLE = "disable robot";
    private static final String[] KEY_WORD_ARRAY = { ACTIVE, DISABLE};

    @Autowired
    public CommandTextProcessor(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    @Override
    public String answer(BaseMsg message) throws AnswerException {
        String text = message.getText();
        if (ACTIVE.equals(text)) {
            userFilter.addUser(message.getFromUserName());
            return "很好，少年，你成功引起了我的注意";
         }
         if (DISABLE.equals(text)) {
             userFilter.removeUser(message.getFromUserName());
            return "你将永远的失去宝宝";
        }
        throw new AnswerException("不存在可以响应的命令");
    }

    @Override
    public Decision decide(BaseMsg message) {
        // 仅处理文本消息
        if (message.isGroupMsg()) {
            return Decision.PASS;
        }

        for (String command : KEY_WORD_ARRAY) {
            if (command.equals(message.getText()))
                return Decision.ACCEPT;
        }

        return Decision.PASS;
    }
}
