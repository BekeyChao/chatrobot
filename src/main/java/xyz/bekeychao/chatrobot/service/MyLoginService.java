package xyz.bekeychao.chatrobot.service;

import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.service.impl.LoginServiceImpl;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.URLEnum;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 重写LoginServiceImpl的getQR方法，以实现自己获取二维码并发送到前台
 * @author BekeyChao@github.com
 */
@Service
public class MyLoginService extends LoginServiceImpl {
    private byte[] qrData;

    private final Logger logger = LoggerFactory.getLogger(MyLoginService.class);

    private MyHttpClient httpClient = MyHttpClient.getInstance();

    private Core core = Core.getInstance();

    private HttpServletResponse response;

//    @Autowired
//    private RestTemplate restTemplate;


    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public boolean getQR(String qrPath) {
//        qrPath = qrPath + File.separator + "QR.jpg";
        String qrUrl = URLEnum.QRCODE_URL.getUrl() + core.getUuid();
        HttpEntity entity = httpClient.doGet(qrUrl, null, true, null);
        try {
            OutputStream out = response.getOutputStream();
            byte[] bytes = EntityUtils.toByteArray(entity);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
//            LOG.info(e.getMessage());
            logger.info(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
