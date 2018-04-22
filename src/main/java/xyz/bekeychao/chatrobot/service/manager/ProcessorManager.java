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
 */
@Component
public class ProcessorManager {
    private List<BaseProcessor> processors;

    @Autowired
    public ProcessorManager(CommandTextProcessor commandTextProcessor, ContextService contextService, TuringTextProcessor tulinTextProcessor,
                            UserFilter userFilter) {
        processors = Arrays.asList(commandTextProcessor,  userFilter, contextService, tulinTextProcessor);
    }


    public ProcessorManager addProcessorBefore(BaseProcessor filter, Class<? extends BaseProcessor> clz) {
        int i = getOrder(clz);
        Assert.state(i > -1, "class not exist in processors" + clz.getName());
        processors.add(i, filter);
        return this;
    }

    public ProcessorManager addProcessorAfter(BaseProcessor filter, Class<? extends BaseProcessor> clz) {
        int i = getOrder(clz);
        Assert.state(i > -1, "class not exist in processors" + clz.getName());
        processors.add(i + 1, filter);
        return this;
    }

    public ProcessorManager addProcessor(BaseProcessor filter) {
        processors.add(filter);
        return this;
    }

    public ProcessorManager removeProcessor(BaseProcessor filter) {
        processors.remove(filter);
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
}
