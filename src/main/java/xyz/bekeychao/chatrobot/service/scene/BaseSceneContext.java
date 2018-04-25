package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import xyz.bekeychao.chatrobot.exception.SceneException;


/**
 * 场景信息接口
 * @author BekeyChao@github.com
 */
public interface BaseSceneContext {
    // 定义场景Id
    String sceneId();

    String act(String userId, BaseMsg message);

    /**
     * 定义是否在响应后自动移除场景值，默认true
     * @return true 自动移除
     */
    default boolean isRemovedAfterResponse() {
        return true;
    }

    /**
     * 定义会话过期时间 毫秒值 未实现
     * @return -1 永不过期
     */
    long express();
}
