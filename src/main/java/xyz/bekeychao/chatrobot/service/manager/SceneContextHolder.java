package xyz.bekeychao.chatrobot.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.service.scene.AlarmCreateScene;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景信息储存组件
 * 组件共持有两个内容  场景对象 方便通过sceneId获取
 * 用户场景临时消息， Object[]  约定数组首位是场景对象
 * @author BekeyChao@github.com
 */
public class SceneContextHolder {
    private static Map<String, BaseSceneContext> sceneContextMap = new HashMap<>();

    // 储存场景值参数， 第一个参数固定为场景对象
    private static Map<String, Object[]> argumentsMap = new HashMap<>();

//    @Autowired
//    public SceneContextHolder(AlarmCreateScene alarmCreateScene) {
//        sceneContextMap.put(alarmCreateScene.sceneId(), alarmCreateScene);
//    }

    public static BaseSceneContext getSceneBySceneId(String sceneId) {
        if (sceneContextMap.containsKey(sceneId))
            return sceneContextMap.get(sceneId);
        throw new IllegalArgumentException("不存在指定场景ID" + sceneId);
    }

    public static Object[] getArgumentsByUserId(String userId) {
        return argumentsMap.get(userId);
    }

    public static void setArgumentsByUserId(String userId, Object[] args) {
        argumentsMap.put(userId, args);
    }

    public static void addSceneContext(BaseSceneContext context) {
        sceneContextMap.put(context.sceneId(), context);
    }

    public static void removeArgumentsByUserId(String userId) {
        argumentsMap.remove(userId);
    }

}
