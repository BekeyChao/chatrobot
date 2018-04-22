package xyz.bekeychao.chatrobot.service;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.exception.AnswerException;
import xyz.bekeychao.chatrobot.service.manager.ProcessorManager;
import xyz.bekeychao.chatrobot.service.processor.BaseProcessor;
import xyz.bekeychao.chatrobot.service.processor.TextProcessor;

@Service
public class CentreMessageHandler implements IMsgHandlerFace {
    private final Logger logger = LoggerFactory.getLogger(CentreMessageHandler.class);

    @Autowired
    ProcessorManager processorManager;


    @Override
    public String textMsgHandle(BaseMsg baseMsg) {
        BaseProcessor filter = processorManager.decision(baseMsg);
        if (filter != null && filter instanceof TextProcessor) {
            TextProcessor processor = (TextProcessor)filter;
            try {
                return processor.answer(baseMsg);
            }catch (AnswerException e) {
                logger.error("An answerException happened", e);
                return  null;
            }
        }
        return null;
    }

    @Override
    public String picMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    @Override
    public String voiceMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    @Override
    public String viedoMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    @Override
    public String nameCardMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    @Override
    public void sysMsgHandle(BaseMsg baseMsg) {

    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    @Override
    public String mediaMsgHandle(BaseMsg baseMsg) {
        return null;
    }
}
