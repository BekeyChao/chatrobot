package xyz.bekeychao.chatrobot.domain;

import xyz.bekeychao.chatrobot.service.AlarmService;
import xyz.bekeychao.chatrobot.util.SpringContextUtil;

/**
 * @author BekeyChao@github.com
 */
public class AlarmRunnable implements Runnable{
//    private final Logger logger = LoggerFactory.getLogger(AlarmRunnable.class);

    // Alarm 的uuid
    private String uuid;

    public AlarmRunnable(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void run() {
//      手动获取bean
        SpringContextUtil.getBean(AlarmService.class).sentScheduleMessage(uuid);

    }
}
