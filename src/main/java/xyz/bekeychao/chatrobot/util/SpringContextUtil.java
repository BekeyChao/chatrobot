package xyz.bekeychao.chatrobot.util;

import org.springframework.context.ApplicationContext;

/**
 * @author BekeyChao@github.com
 * @date 2018/5/2
 */
public class SpringContextUtil {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return getApplicationContext().getBean(clz);
    }
}
