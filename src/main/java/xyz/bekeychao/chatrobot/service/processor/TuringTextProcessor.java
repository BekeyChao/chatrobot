package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.service.TuringService;

/**
 * 图灵机器人响应器， 调用图灵机器人接口与用户聊天响应， 目前只实现文本聊天
 */
@Service
public class TuringTextProcessor implements TextProcessor {

    private final Logger logger = LoggerFactory.getLogger(TuringTextProcessor.class);

    @Autowired
    private TuringService turingService;


    public String answer(BaseMsg message) {
        String text = message.getText();
        return turingService.chat(text);
    }


    @Override
    public Decision decide(BaseMsg message) {
        return MsgCodeEnum.MSGTYPE_TEXT.getCode() == message.getMsgType() ? Decision.ACCEPT : Decision.PASS;
    }
}
