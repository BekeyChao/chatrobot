package xyz.bekeychao.chatrobot.service.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 管理任务的池子， 方便查询任务状态和取消任务
 * @author BekeyChao@github.com
 */
@Component
public class ScheduleFutureHolder {
    private Map<String, ScheduledFuture<?>> futurePool = new HashMap<>();

    public void putFuture(String uuid, ScheduledFuture<?> future) {
//        if (futurePool.containsKey(uuid)) {
//            throw new IllegalArgumentException("任务Id已存在，请勿重复添加任务");
//        }
        futurePool.put(uuid, future);
    }

    public ScheduledFuture<?> getFuture(String uuid) {
        return futurePool.get(uuid);
    }
}
