package com.limai.user.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {

    /**
     * 获取32UUID字符串
     *
     * @param
     * @return String
     */
    public static String getUUIDStr() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取指定长度的随机数字
     *
     * @param length
     * @return String
     */
    public static String getRandomNum(int length) {
        String num = Math.random() + "";

        return num.substring(num.indexOf(".") + 1, num.indexOf(".") + length) + 1;
    }

    /**
     * 获取64位随机不重复字符串, UUID去"-" + 17位时间戳 + 15位数字
     *
     * @param
     * @return String
     */
    public static String get64RandomStr() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String timestamp = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
        String random = getRandomNum(15);

        return uuid + timestamp + random;
    }

    /**
     * 去除所有空格
     *
     * @param str
     * @return String
     */
    public static String removeBlank(String str) {

        return str.replaceAll("\\s*", "");
    }

    /**
     * 判断字符串为空, 包含null, "null", "", 空格
     *
     * @param str
     * @return boolean
     */
    public static boolean isNull(String str) {
        boolean flag = StringUtils.isBlank(str);

        // 当str不是null, "", 空格时, 判断是否为"null"
        if (!flag && "null".equals(str.replace(" ", ""))) {
            flag = true;
        }

        return flag;
    }

    /**
     * 判断字符串不为空, 包含null, "null", "", 空格
     *
     * @param str
     * @return boolean
     */
    public static boolean isNotNull(String str) {

        return !isNull(str);
    }

    /**
     * 字符串转unicode
     *
     * @param str
     * @return String
     */
    public static String string2Unicode(String str) {
        StringBuffer re = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            re.append("\\u").append(Integer.toHexString(str.charAt(i)));
        }

        return re.toString();
    }

    /**
     * unicode转字符串
     *
     * @param dataStr
     * @return String
     */
    public static String unicode2String(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }

        return buffer.toString();
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return String
     */
    public static String replaceSpecialChar(String str) {

        return str.replaceAll("[%|(|)|`|‘|’|@|-|+|=|.|*|/]", "")
                .replace("\\", "").replace("-", "").replace("on", "on'")
                .replace("alert", "al'ert").replace("prompt", "pro'mpt")
                .replace("eval", "eva'l'").replace("confirm", "con'firm'")
                .replace("script", "scr'ipt'");
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }

        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);

        return s;
    }

    /**
     * 过滤表情符号
     * @create by ldw on 2016-10-25
     * @param str
     * @return str(去掉表情符号的字符串)
     * @version 1.0
     * */
    public static String filter(String str) {
        if (str.trim().isEmpty()) {
            return str;
        }
        String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr = "";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        str = emojiMatcher.replaceAll(reStr);
        return str;
    }

}
