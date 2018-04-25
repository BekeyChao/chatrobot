package xyz.bekeychao.chatrobot.util;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author BekeyChao@github.com
 */
public class CronUtil {

    private static final String ex = "0 0 0 1 1 * *";

    // corn表达式 秒 分 时 日 月 星期 年
    //import java.util.List;
    public static String atOnceTime(LocalDateTime time) {

        Object[] express = {time.getSecond(), time.getMinute(), time.getHour(), time.getDayOfMonth(),
                time.getMonthValue(), "*", time.getYear()};

        return join(express, " ");
    }

    public static String fixedRate(LocalDateTime start, long rate, TimeUnit timeUnit) {
        String express = atOnceTime(start);
        return express;
    }

    private static String join(Object[] a, String split) {
        if (a == null)
            return "";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
//        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
            b.append(split);
        }
    }

}
