package xyz.bekeychao.chatrobot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class ChatrobotApplication {

	public static void main(String[] args) {
		// 系统默认时区设定为东八区
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("+8")));

		SpringApplication.run(ChatrobotApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		Logger logger = LoggerFactory.getLogger(ThreadPoolTaskScheduler.class);
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setErrorHandler((exception) -> {
			logger.warn("An exception happened in task schedule", exception);
		});
		return scheduler;
	}
}
