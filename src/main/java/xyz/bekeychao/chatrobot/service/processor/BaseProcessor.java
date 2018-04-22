package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理器接口， 定义decide用于指示处理器是否响应消息
 *
 * 提供默认的process方法， 用于提供在 {@link Decision} 响应PROCESS时处理消息
 */
public interface BaseProcessor {
    Logger logger = LoggerFactory.getLogger(BaseProcessor.class);

    Decision decide(BaseMsg message);

    default void process(BaseMsg message) {
        if ( logger.isDebugEnabled() ) {
            logger.debug("你请求处理了一个消息， 但是却没有实现 消息ID = " + message.getMsgId());
        }
        // ignore
    }

    enum Decision {
        /**
         * 处理并结束流程
         */
        ACCEPT,
        /**
         * 调用process方法处理消息但不结束流程
         */
        PROCESS,
        /**
         * 不处理但不结束流程
         */
        PASS,
        /**
         * 拒绝并结束流程
         */
        DENY;
    }
}
