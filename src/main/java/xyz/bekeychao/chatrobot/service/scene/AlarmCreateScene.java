package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bekeychao.chatrobot.domain.AlarmRunnable;
import xyz.bekeychao.chatrobot.exception.SceneException;
import xyz.bekeychao.chatrobot.service.TaskService;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AlarmCreateScene implements BaseSceneContext{
    private static LocalTime time = LocalTime.of(8,0 );

    @Autowired
    private SceneContextHolder sceneContextHolder;

    @Autowired
    private TaskService taskService;

    @Override
    public String sceneId() {
        return "AlarmCreate";
    }

    @Override
    public String act(String userId, BaseMsg message) throws SceneException {
//        if (sceneContextHolder.getArgumentsByUserId(userId) == null) {
//            sceneContextHolder.setArgumentsByUserId(userId, new Object[]{ Boolean.FALSE });
//        }
        String text = message.getText();
        // 语义分析
        try {
            LocalDateTime date = LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            AlarmRunnable runnable = new AlarmRunnable(userId, "提醒事项");
            taskService.scheduleOnce(runnable, date);

            if (isRemovedAfterResponse()) {
                sceneContextHolder.removeArgumentsByUserId(userId);
            }

            return "提醒已创建";
        }catch (DateTimeParseException e) {

            return "解析异常";
        }
    }


    @Override
    public long express() {
        return 10000;
    }
}
