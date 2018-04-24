package xyz.bekeychao.chatrobot;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgCodeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.bekeychao.chatrobot.service.CentreMessageHandler;
import xyz.bekeychao.chatrobot.service.TaskService;
import xyz.bekeychao.chatrobot.service.manager.ProcessorManager;
import xyz.bekeychao.chatrobot.service.processor.BaseProcessor;
import xyz.bekeychao.chatrobot.service.processor.TextProcessor;

import java.util.concurrent.ScheduledFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatrobotApplicationTests {

	@Autowired
	ProcessorManager manager;

	@Autowired
    CentreMessageHandler handler;

	@Test
	public void contextLoads() throws Exception{
		String[] text = {"天王盖地虎", "日程提醒", "2018-04-24 17:24:00", "聊天机器人"};
		for (String s : text) {
		    BaseMsg baseMsg = new BaseMsg();
		    baseMsg.setFromUserName("username");
		    baseMsg.setMsgType(MsgCodeEnum.MSGTYPE_TEXT.getCode());
		    baseMsg.setText(s);
            System.out.println(handler.textMsgHandle(baseMsg));
        }
        Thread.sleep(100000);
//		BaseMsg baseMsg = new BaseMsg();
//		baseMsg.setText(text);
//		BaseProcessor decision = manager.decision(baseMsg);

	}

//	@Autowired
//    TaskService taskService;
	@Test
    public void task() throws InterruptedException {
//        ScheduledFuture<?> log_some = taskService.createTask(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("log some");
//            }
//        }, "* * * * * *");
//        Thread.sleep(10000);

//        taskService.stopTask(log_some);
    }

}
