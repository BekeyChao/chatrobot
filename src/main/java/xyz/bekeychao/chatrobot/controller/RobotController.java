package xyz.bekeychao.chatrobot.controller;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.controller.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.bekeychao.chatrobot.service.CentreMessageHandler;
import xyz.bekeychao.chatrobot.service.MyLoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    CentreMessageHandler messageHandler;

    @Autowired
    MyLoginService myLoginService;

    @RequestMapping("/start")
    public void start(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        myLoginService.setResponse(response);
        LoginController.setLoginService(myLoginService);
        Wechat wechat = new Wechat(messageHandler, "D:");
//        wechat.setLoginService(myLoginService);
//        wechat.login();
//        myLoginService.img(response);
        wechat.start();
    }

    @RequestMapping("/old")
    public void old() {
        Wechat wechat = new Wechat(messageHandler, "D:");
//        wechat.login();
        wechat.start();
    }

    @RequestMapping("/logout")
    public void logout() {
        WechatTools.logout();
    }


}
