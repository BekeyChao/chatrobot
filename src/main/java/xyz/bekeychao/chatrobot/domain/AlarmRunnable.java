package xyz.bekeychao.chatrobot.domain;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlarmRunnable implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(AlarmRunnable.class);

    private String userId;
    private String content;


    public AlarmRunnable(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    @Override
    public void run() {
//        if (WechatTools.usernameExists(userId)) {
            // 在发送消息前，其实应该检查好友是否存在（发送给不存在的好友可能引发封号），但是 WechatTools 好友列表维护并不保证完整，故暂时不验证
            MessageTools.sendMsgById(content, userId);
//        }
//        logger.warn("");
    }
}
