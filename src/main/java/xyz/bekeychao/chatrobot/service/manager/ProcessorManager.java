package xyz.bekeychao.chatrobot.service.manager;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import xyz.bekeychao.chatrobot.service.processor.*;

import java.util.Arrays;
import java.util.List;

/**
 * 处理器管理组
 * @author BekeyChao@github.com
 */
@Component
public class ProcessorManager {
    private List<BaseProcessor> processors;

    @Autowired
    public ProcessorManager(CommandTextProcessor commandTextProcessor, ContextService contextService, TuringTextProcessor tulinTextProcessor,
                            UserFilter userFilter,ScheduleProcessor scheduleProcessor) {
        processors = Arrays.asList(commandTextProcessor,  userFilter, contextService, scheduleProcessor ,tulinTextProcessor);
    }

    /**
     * 决定使用哪个处理组件进行处理
     * @param message
     * @return
     */
    public BaseProcessor decision(BaseMsg message) {
        for (BaseProcessor filter: processors) {
            switch (filter.decide(message)) {
                case ACCEPT:
                    return filter;
                case PROCESS:
                    filter.process(message);
                    continue;
                case DENY:
                    return null;
                default:
                    // pass
            }
        }
        return null;
    }

    /**
     * 在处理链上添加一个处理组件
     */
    public ProcessorManager addProcessorBefore(BaseProcessor filter, Class<? extends BaseProcessor> clz) {
        int i = getOrder(clz);
        Assert.state(i > -1, "class not exist in processors" + clz.getName());
        processors.add(i, filter);
        return this;
    }

    /**
     * 在处理链上添加一个处理组件
     */
    public ProcessorManager addProcessorAfter(BaseProcessor filter, Class<? extends BaseProcessor> clz) {
        int i = getOrder(clz);
        Assert.state(i > -1, "class not exist in processors" + clz.getName());
        processors.add(i + 1, filter);
        return this;
    }
    /**
     * 在处理链尾部添加一个处理组件，因为尾部已经是聊天机器人了，所以很可能接不到任何内容
     */
    public ProcessorManager addProcessor(BaseProcessor filter) {
        processors.add(filter);
        return this;
    }
    /**
     * 移除指定处理组件
     */
    public ProcessorManager removeProcessor(BaseProcessor filter) {
        boolean status = processors.remove(filter);
        Assert.state(status, "class not exist in processors" + filter.getClass().getName());
        return this;
    }

    private  int getOrder(Class<? extends BaseProcessor> clz) {
        for (int i = 0; i < processors.size(); i++) {
            if (processors.get(i).getClass().equals(clz)) {
                return i;
            }
        }
        return -1;
    }


}
