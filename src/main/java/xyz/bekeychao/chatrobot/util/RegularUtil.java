package xyz.bekeychao.chatrobot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BekeyChao@github.com
 */
public class RegularUtil {

    /**
     * 正则表达式， 提取日期时间  或日期
     * @param s
     * @return
     */
    public static String convertDateTime(String s) {
        Pattern p = Pattern.compile("20\\d{2}-[0-2]\\d-[0-3]\\d(\\s+\\d{2}:\\d{2}:\\d{2})?");
        return match(s, p);
    }

    public static String convertDate(String s) {
        Pattern p = Pattern.compile("20\\d{2}-[0-2]\\d-[0-3]\\d");
        return match(s, p);
    }

    public static String convertTime(String s) {
        Pattern p = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
        return match(s, p);
    }

    /**
     * 匹配{@link java.util.UUID} 随机生成的字符串，匹配小写
     * @param s
     * @return
     */
    public static String convertUUID(String s) {
        Pattern p = Pattern.compile("[a-z0-9]{8}-[a-z0-9]{4}-4[a-z0-9]{3}-[a-z0-9]{4}-[a-z0-9]{12}");
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