package xyz.bekeychao.chatrobot.service;

import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BekeyChao@github.com
 */
@Service
public class TuringService {
    // 图灵机器人的应用key， 可以自己申请
    @Value("${chatrobot.turing.key}")
    private String key;

    private static final String URL = "http://www.tuling123.com/openapi/api";

    private final Logger logger = LoggerFactory.getLogger(TuringService.class);

    private final MyHttpClient httpClient = MyHttpClient.getInstance();

    public String chat(String text, String user) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("info", text);
        paramMap.put("userid", user);
        String paramStr = JSON.toJSONString(paramMap);

        try {
            HttpEntity entity = httpClient.doPost(URL, paramStr);
            String result = EntityUtils.toString(entity, "UTF-8");
            JSONObject obj = JSON.parseObject(result);
            if (obj.getString("code").equals("100000")) {
                return obj.getString("text");
            } else {
                return  "你可能希望我说些什么，但是我并不知道说什么，换个话题吧";
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return  "我可能遇到了点什么麻烦，回头再聊吧";
        }
    }
}
