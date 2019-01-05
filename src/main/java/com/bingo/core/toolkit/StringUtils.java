package com.bingo.core.toolkit;

import com.bingo.core.toolkit.sql.StringEscape;

import java.util.Collection;
import java.util.Random;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * String 工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符
     */
    public static final String EMPTY = "";
    /**
     * 字符串 is
     */
    public static final String IS = "is";
    /**
     * 下划线字符
     */
    public static final char UNDERLINE = '_';
    /**
     * 占位符
     */
    public static final String PLACE_HOLDER = "{%s}";
    /**
     * 验证字符串是否是数据库字段
     */
    private static final Pattern P_IS_COLUMN = Pattern.compile("^\\w\\S*[\\w\\d]*$");

    private StringUtils() {
    }

    /**
     * 判断字符串是否为空
     * @param cs
     * @return
     */
    public static boolean isEmpty(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串是否符合数据库字段的命名
     * @param str
     * @return
     */
    public static boolean isNotColumnName(String str) {
        return !P_IS_COLUMN.matcher(str).matches();
    }

    /**
     * 字符串驼峰转下划线格式
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 解析 getMethodName -> propertyName
     * @param getMethodName
     * @return
     */
    public static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith(IS)) {
            getMethodName = getMethodName.substring(2);
        }
        // 小写第一个字母
        return StringUtils.firstToLowerCase(getMethodName);
    }

    /**
     * 字符串下划线转驼峰格式
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 首字母转换小写
     * @param param
     * @return
     */
    public static String firstToLowerCase(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }

    /**
     * 判断字符串是否为纯大写字母
     * @param str
     * @return
     */
    public static boolean isUpperCase(String str) {
        return matches("^[A-Z]+$", str);
    }

    /**
     * 正则表达式匹配
     * @param regex
     * @param input
     * @return
     */
    public static boolean matches(String regex, String input) {
        if (null == regex || null == input) {
            return false;
        }
        return Pattern.matches(regex, input);
    }

    /**
     * SQL 参数填充
     * @param content
     * @param args
     * @return
     */
    public static String sqlArgsFill(String content, Object... args) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (args != null) {
            int length = args.length;
            if (length >= 1) {
                for (int i = 0; i < length; i++) {
                    content = content.replace(String.format(PLACE_HOLDER, i), sqlParam(args[i]));
                }
            }
        }
        return content;
    }

    /**
     * 获取SQL PARAMS字符串
     * @param obj
     * @return
     */
    public static String sqlParam(Object obj) {
        String repStr;
        if (obj instanceof Collection) {
            repStr = StringUtils.quotaMarkList((Collection<?>) obj);
        } else {
            repStr = StringUtils.quotaMark(obj);
        }
        return repStr;
    }

    /**
     * 使用单引号包含字符串
     * @param obj
     * @return
     */
    public static String quotaMark(Object obj) {
        String srcStr = String.valueOf(obj);
        if (obj instanceof CharSequence) {
            // fix #79
            return StringEscape.escapeString(srcStr);
        }
        return srcStr;
    }

    /**
     * 使用单引号包含字符串
     * @param coll
     * @return
     */
    public static String quotaMarkList(Collection<?> coll) {
        return coll.stream().map(StringUtils::quotaMark).collect(joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }

    /**
     * 拼接字符串第二个字符串第一个字母大写
     * @param concatStr
     * @param str
     * @return
     */
    public static String concatCapitalize(String concatStr, final String str) {
        if (isEmpty(concatStr)) {
            concatStr = EMPTY;
        }
        if (str == null || str.length() == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            // already capitalized
            return str;
        }

        return concatStr + Character.toTitleCase(firstChar) + str.substring(1);
    }

    /**
     * 字符串第一个字母大写
     * @param str
     * @return
     */
    public static String capitalize(final String str) {
        return concatCapitalize(null, str);
    }

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean checkValNotNull(Object object) {
        if (object instanceof CharSequence) {
            return isNotEmpty((CharSequence) object);
        }
        return object != null;
    }

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean checkValNull(Object object) {
        return !checkValNotNull(object);
    }

    /**
     * 包含大写字母
     * @param word
     * @return
     */
    public static boolean containsUpperCase(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为CharSequence类型
     * @param clazz
     * @return
     */
    public static boolean isCharSequence(Class<?> clazz) {
        return clazz != null && CharSequence.class.isAssignableFrom(clazz);
    }

    /**
     * 随机数字，返回固定5位长度
     */
    public static String generateFixdLengthString() {
        return String.format("%05d", new Random().nextInt(9999));
    }

    /**
     * 校验输入字符串强度
     *
     * @author: 郑海育
     * @date: 2018年9月20日
     * @param input
     * @return
     */
    public static boolean testPasswordStrength(String input) {
        // "密码至少要由包括大小写字母、数字、标点符号的其中两项,共计8-16位编码组成！"
        String regStr = "^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$";
        return input.matches(regStr);
    }

    /**
     * <p>
     * 驼峰转连字符<br>
     * StringUtils.camelToHyphen( "managerUserService" ) = manager-user-service
     * </p>
     *
     * @param input
     * @return 以'-'分隔
     */
    public static String camelToHyphen(String input) {
        return wordsToHyphenCase(wordsAndHyphenAndCamelToConstantCase(input));
    }

    private static String wordsAndHyphenAndCamelToConstantCase(String input) {
        boolean betweenUpperCases = false;
        boolean containsLowerCase = containsLowerCase(input);

        StringBuilder buf = new StringBuilder();
        char previousChar = ' ';
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            boolean isUpperCaseAndPreviousIsUpperCase = (Character.isUpperCase(previousChar)) && (Character.isUpperCase(c));
            boolean isUpperCaseAndPreviousIsLowerCase = (Character.isLowerCase(previousChar)) && (Character.isUpperCase(c));

            boolean previousIsWhitespace = Character.isWhitespace(previousChar);
            boolean lastOneIsNotUnderscore = (buf.length() > 0) && (buf.charAt(buf.length() - 1) != '_');
            boolean isNotUnderscore = c != '_';
            if (lastOneIsNotUnderscore && (isUpperCaseAndPreviousIsLowerCase || previousIsWhitespace
                    || (betweenUpperCases && containsLowerCase && isUpperCaseAndPreviousIsUpperCase))) {
                buf.append(StringPool.UNDERSCORE);
            } else if ((Character.isDigit(previousChar) && Character.isLetter(c))) {
                buf.append('_');
            }
            if ((shouldReplace(c)) && (lastOneIsNotUnderscore)) {
                buf.append('_');
            } else if (!Character.isWhitespace(c) && (isNotUnderscore || lastOneIsNotUnderscore)) {
                buf.append(Character.toUpperCase(c));
            }
            previousChar = c;
        }
        if (Character.isWhitespace(previousChar)) {
            buf.append(StringPool.UNDERSCORE);
        }
        return buf.toString();
    }

    public static boolean containsLowerCase(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldReplace(char c) {
        return (c == '.') || (c == '_') || (c == '-');
    }

    private static String wordsToHyphenCase(String s) {
        StringBuilder buf = new StringBuilder();
        char lastChar = 'a';
        for (char c : s.toCharArray()) {
            if ((Character.isWhitespace(lastChar)) && (!Character.isWhitespace(c)) && ('-' != c) && (buf.length() > 0)
                    && (buf.charAt(buf.length() - 1) != '-')) {
                buf.append(StringPool.DASH);
            }
            if ('_' == c) {
                buf.append('-');
            } else if ('.' == c) {
                buf.append('-');
            } else if (!Character.isWhitespace(c)) {
                buf.append(Character.toLowerCase(c));
            }
            lastChar = c;
        }
        if (Character.isWhitespace(lastChar)) {
            buf.append(StringPool.DASH);
        }
        return buf.toString();
    }

    /**
     * <p>
     * 前n个首字母小写,之后字符大小写的不变
     * </p>
     *
     * @param rawString 需要处理的字符串
     * @param index     多少个字符(从左至右)
     * @return
     */
    public static String prefixToLower(String rawString, int index) {
        String beforeChar = rawString.substring(0, index).toLowerCase();
        String afterChar = rawString.substring(index);
        return beforeChar + afterChar;
    }

    /**
     * <p>
     * 删除字符前缀之后,首字母小写,之后字符大小写的不变<br>
     * StringUtils.removePrefixAfterPrefixToLower( "isUser", 2 )     = user
     * StringUtils.removePrefixAfterPrefixToLower( "isUserInfo", 2 ) = userInfo
     * </p>
     *
     * @param rawString 需要处理的字符串
     * @param index     删除多少个字符(从左至右)
     * @return
     */
    public static String removePrefixAfterPrefixToLower(String rawString, int index) {
        return prefixToLower(rawString.substring(index), 1);
    }

    /**
     * <p>
     * 第一个首字母小写,之后字符大小写的不变<br>
     * StringUtils.firstCharToLower( "UserService" )     = userService
     * StringUtils.firstCharToLower( "UserServiceImpl" ) = userServiceImpl
     * </p>
     *
     * @param rawString 需要处理的字符串
     * @return
     */
    public static String firstCharToLower(String rawString) {
        return prefixToLower(rawString, 1);
    }

    /**
     * <p>
     * 是否为驼峰下划线混合命名
     * </p>
     *
     * @param word 待判断字符串
     * @return
     */
    public static boolean isMixedMode(String word) {
        return matches(".*[A-Z]+.*", word) && matches(".*[/_]+.*", word);
    }

    /**
     * <p>
     * 是否为大写命名
     * </p>
     *
     * @param word 待判断字符串
     * @return
     */
    public static boolean isCapitalMode(String word) {
        return null != word && word.matches("^[0-9A-Z/_]+$");
    }





    public static void main(String[] args) {
        System.out.println(isCapitalMode("USS"));
    }

}
