package com.bingo.core.toolkit;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;
import java.util.UUID;

public class CommonUtils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter dateTimeNotSecondFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter dateNotSplitterFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        System.out.println(getMonthEndTime());
    }

    public static String md5(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String codeMd5(String code, String salt) {
        StringBuffer result = new StringBuffer();
        result.append(StringUtils.substring(salt, 3, 10));
        result.append(code);
        result.append(StringUtils.substring(salt, 7));
        for (int i = 0; i < 15; i++) {
            result.append(DigestUtils.md5Hex(result.toString()));
        }
        return md5(result.toString());
    }

    public static boolean checkMd5(String code, String salt, String val) {
        return StringUtils.equals(val, codeMd5(code, salt));
    }

    /**
     * 校验输入字符串强度
     *
     * @param input
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static boolean testPasswordStrength(String input) {
        // "密码至少要由包括大小写字母、数字、标点符号的其中两项,共计8-16位编码组成！"
        String regStr = "^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$";
        return input.matches(regStr);
    }

    /**
     * UUID 去除-
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取一天的开始时间
     *
     * @return
     */
    public static LocalDateTime getTodayStartTime() {
        LocalDateTime beginTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return beginTime;
    }

    /**
     * 获取一天的结束时间
     *
     * @return
     */
    public static LocalDateTime getTodayEndTime() {
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return endTime;
    }

    /**
     * 本周开始时间
     *
     * @return
     */
    public static LocalDateTime getWeekStartTime() {
        LocalDate inputDate = LocalDate.now();

        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));

        return LocalDateTime.of(inputDate.with(FIRST_OF_WEEK), LocalTime.MIN);
    }

    /**
     * 本周结束时间
     *
     * @return
     */
    public static LocalDateTime getWeekEndTime() {
        LocalDate inputDate = LocalDate.now();

        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));

        return LocalDateTime.of(inputDate.with(LAST_OF_WEEK), LocalTime.MAX);
    }

    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static LocalDateTime getMonthStartTime() {
        LocalDate inputDate = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(LocalDate.of(inputDate.getYear(), inputDate.getMonth(), 1), LocalTime.MIN);
        return beginTime;
    }

    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static LocalDateTime getMonthEndTime() {
        LocalDate inputDate = LocalDate.now();
        LocalDateTime endTime = LocalDateTime.of(inputDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
        return endTime;
    }


    /**
     * 返回当前日期时间字符串
     *
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static String currentDateTimeReSecondStr() {
        return LocalDateTime.now().format(dateTimeNotSecondFormatter);
    }

    /**
     * 返回当前日期时间字符串
     *
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static String currentDateTimeStr() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * 返回当前日期字符串
     *
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static String currentDateStr() {
        return LocalDate.now().format(dateFormatter);
    }

    /**
     * 返回前一天日期字符串
     *
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static String beforeDateStr() {
        return LocalDate.now().minusDays(1).format(dateFormatter);
    }

    public static int randomInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    /**
     * 大于0
     * @author: 郑海育
     * @date: 2018年10月9日
     * @param bigDecimal
     * @return
     */
    public static boolean greaterZero(BigDecimal bigDecimal) {
        return bigDecimal.compareTo(BigDecimal.ZERO) == 1;
    }


    /**
     * 判断是不是整数
     *
     * @param bigDecimal
     * @return
     * @author: 郑海育
     * @date: 2018年9月20日
     */
    public static boolean isInteger(BigDecimal bigDecimal) {
        return new BigDecimal(bigDecimal.intValue()).compareTo(bigDecimal) == 0;
    }

    /**
     * 判断是不是负数
     *
     * @param bigDecimal
     * @return
     * @author: 郑海育
     * @date: 2018年9月22日
     */
    public static boolean isNegative(BigDecimal bigDecimal) {
        return BigDecimal.ZERO.compareTo(bigDecimal) >= 0;
    }

    /**
     * 字符串转Int
     *
     * @param val
     * @return
     */
    public static Integer toInteger(String val) {
        if (StringUtils.isBlank(val) || !StringUtils.isNumeric(val)) {
            return 0;
        }
        return Integer.valueOf(val);
    }

    public static BigDecimal toBigDecimal(String val) {
        if (StringUtils.isBlank(val)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(val);
    }

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static Long toLong(String val) {
        if (StringUtils.isBlank(val) || !StringUtils.isNumeric(val)) {
            return 0L;
        }
        return Long.valueOf(val);
    }
}
