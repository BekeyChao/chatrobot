package xyz.bekeychao.chatrobot.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import xyz.bekeychao.chatrobot.service.manager.SceneContextHolder;
import xyz.bekeychao.chatrobot.service.scene.BaseSceneContext;

@Configuration
public class ChatrobotConfig implements BeanPostProcessor{
//    @Autowired
//    SceneContextHolder sceneContextHolder;

    // 将场景 保存到 holder中
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseSceneContext) {
            BaseSceneContext context = (BaseSceneContext) bean;
            SceneContextHolder.addSceneContext(context);
        }
        return bean;
    }
}
