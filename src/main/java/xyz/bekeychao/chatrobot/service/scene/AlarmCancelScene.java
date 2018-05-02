package xyz.bekeychao.chatrobot.service.scene;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.bekeychao.chatrobot.service.ScheduleService;
import xyz.bekeychao.chatrobot.util.RegularUtil;

/**
 * @author BekeyChao@github.com
 * @date 2018/4/25
 */
@Component
public class AlarmCancelScene implements BaseSceneContext{
    @Autowired
    private ScheduleService scheduleService;

    @Override
    public String sceneId() {
        return "AlarmCancel";
    }

    @Override
    public String act(String userId, BaseMsg message) {
        String text = message.getText();
        String uuid = RegularUtil.convertUUID(text);
        if (uuid == null) {
            return "不存在可以识别的UUID， 请按 提醒取消 ID 任务的ID 的格式发送给我";
        }
        if (scheduleService.cancelSchedule(uuid)) {
            return "OK，已取消";
        }
        return "任务可能不存在，或者我已经提醒过你了";
    }

    @Override
    public long express() {
        return -1L;
    }
}
