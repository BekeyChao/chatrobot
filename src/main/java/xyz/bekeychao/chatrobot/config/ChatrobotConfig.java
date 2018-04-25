package xyz.bekeychao.chatrobot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

/**
 * @author BekeyChao@github.com
 */
@Configuration
public class ChatrobotConfig implements BeanPostProcessor{
//    @Autowired
//    SceneContextHolder sceneContextHolder;
    private final Logger logger = LoggerFactory.getLogger(ChatrobotConfig.class);

    // 将场景 保存到 holder中
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseSceneContext) {
            BaseSceneContext context = (BaseSceneContext) bean;
            SceneContextHolder.addSceneContext(context);
        }
        return bean;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setErrorHandler((exception) -> {
            logger.warn("An exception happened in task schedule", exception);
        });
        return scheduler;
    }
}
