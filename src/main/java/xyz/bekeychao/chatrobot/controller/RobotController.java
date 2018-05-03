package xyz.bekeychao.chatrobot.controller;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.controller.LoginController;
import cn.zhouyafeng.itchat4j.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.bekeychao.chatrobot.service.CentreMessageHandler;
import xyz.bekeychao.chatrobot.service.MyLoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BekeyChao@github.com
 */
@Controller
@RequestMapping("/robot")
public class RobotController {

    private final CentreMessageHandler messageHandler;

    private final MyLoginService myLoginService;

    private final Core core;

    @Autowired
    public RobotController(CentreMessageHandler messageHandler, MyLoginService myLoginService) {
        this.messageHandler = messageHandler;
        this.myLoginService = myLoginService;
        this.core = Core.getInstance();
    }

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
//        Wechat wechat = new Wechat(messageHandler, "D:");
////        wechat.login();
//        wechat.start();
    }

    @ResponseBody
    @RequestMapping("/logout")
    public String logout() {
        WechatTools.logout();
        return "关闭";
    }

    @ResponseBody
    @RequestMapping("/isAlive")
    public boolean online() {
        return core.isAlive();
    }

}
