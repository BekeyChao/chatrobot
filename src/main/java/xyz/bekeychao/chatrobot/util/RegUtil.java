package xyz.bekeychao.chatrobot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {

    /**
     * 正则表达式， 提取日期时间  或日期
     * @param s
     * @return
     */
    public static String convertDateTime(String s) {
        Pattern p = Pattern.compile("20\\d{2}-[1,2]?\\d-[1-3]?\\d(\\s+\\d{2}:\\d{2}:\\d{2})?");
        return match(s, p);
    }

    public static String convertDate(String s) {
        Pattern p = Pattern.compile("20\\d{2}-[1,2]?\\d-[1-3]?\\d");
        return match(s, p);
    }

    public static String convertTime(String s) {
        Pattern p = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
        return match(s, p);
    }

    private static String match(String s, Pattern p) {
        Matcher matcher = p.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}