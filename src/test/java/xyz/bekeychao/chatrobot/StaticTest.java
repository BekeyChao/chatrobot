package xyz.bekeychao.chatrobot;

import org.junit.Test;
import xyz.bekeychao.chatrobot.service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StaticTest {
    @Test
    public void test() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

//        System.out.println(CronUtil.atOnceTime(date, time));
    }

    @Test
    public void test2() {
//        System.out.println(TaskService.convertLDTtoDate(LocalDateTime.now()));
    }
}
