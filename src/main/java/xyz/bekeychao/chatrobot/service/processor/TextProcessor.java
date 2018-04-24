package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import xyz.bekeychao.chatrobot.exception.AnswerException;

public interface TextProcessor extends BaseProcessor {
    /**
     * 文本型处理器
     * @param message
     * @return 返回给消息来源方的内容， null if you want not to answer anything
     * @throws AnswerException
     */
    String answer(BaseMsg message) throws AnswerException;
}
