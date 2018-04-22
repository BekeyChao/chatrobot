package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import xyz.bekeychao.chatrobot.exception.AnswerException;

public interface TextProcessor extends BaseProcessor {
    String answer(BaseMsg message) throws AnswerException;
}
