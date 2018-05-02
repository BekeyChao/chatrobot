package xyz.bekeychao.chatrobot;

import org.junit.Test;
import xyz.bekeychao.chatrobot.util.RegularUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class StaticTest {
    @Test
    public void test() {
//        LocalDate date = LocalDate.now();
//        LocalTime time = LocalTime.now();
        for (int i = 0; i < 20000; i++) {
            String uuid = UUID.randomUUID().toString();
            String uid = RegularUtil.convertUUID(uuid);
            if (uid == null) {
                System.out.println(uuid);
            }
        }


//        System.out.println(CronUtil.atOnceTime(date, time));
    }

    @Test
    public void test2() {
//        System.out.println(TaskService.convertLDTtoDate(LocalDateTime.now()));
//        Map<String,String> map = new HashMap<>();
//
//        map.get("2233");
        String date = "dfsf2019-08-20 12:20:30";

        String s = RegularUtil.convertDateTime(date);
        System.out.println(s);
//        String err = "dfdjkldjfs";
//
//        LocalDate localDate = LocalDate.parse(date ,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        System.out.println(localDate);
//        LocalDate localDate1 = LocalDate.parse(err ,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        System.out.println(localDate1);
    }
}
