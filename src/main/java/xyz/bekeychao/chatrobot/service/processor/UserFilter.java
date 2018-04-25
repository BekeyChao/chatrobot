package xyz.bekeychao.chatrobot.service.processor;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务响应过滤器， 可以用于指定服务于特定用户
 * 默认位于强指令响应器之后
 * @author BekeyChao@github.com
 */

@Component
public class UserFilter implements BaseProcessor {
//    private Logger logger = LoggerFactory.getLogger(UserFilter.class);

    private Set<String> users = new HashSet<>();

    public void addUser(String user) {
        logger.info("New User Add : " + user);
        users.add(user);
    }

    public void removeUser(String user) {
        logger.info("A USER LEFT : " + user);
        users.remove(user);
    }

    public Set<String> getUsers() {
        return users;
    }

    @Override
    public Decision decide(BaseMsg message) {
        if (users.contains(message.getFromUserName())) {
            return Decision.PASS;
        }
        return Decision.DENY;
    }
}
