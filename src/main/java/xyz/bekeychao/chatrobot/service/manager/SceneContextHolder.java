package xyz.bekeychao.chatrobot.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.service.scene.AlarmCreateScene;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

import java.util.HashMap;
import java.util.Map;

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
