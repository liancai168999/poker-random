package com.bingo.core.toolkit;

import com.bingo.core.exceptions.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class ConvertUtil {
    private static final Log log = LogFactory.getLog(ConvertUtil.class);

    public static void objectToMap(Object src, Map<String, String> target) throws InvocationTargetException, ClassNotFoundException, IllegalAccessException {
        if ((null == src) || (null == target)) {
            return;
        }

        Method[] methods = src.getClass().getMethods();
        for (int i = 0, len = methods.length; i < len; ++i) {
            String method = methods[i].getName();
            if ((method.startsWith("get")) && (!("getClass".equals(method)))) {
                Class<?> type = methods[i].getReturnType();
                String key = method.replaceFirst("get", "");
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                Object value = methods[i].invoke(src);
                if (null == value) {
                    target.put(key, "");
                } else {
                    target.put(key, formatParamenter(type, value));
                }
            }
        }
    }

    public static void mapToObject(Map<String, String> src, Object target, boolean idInclude)  {
        if ((null == src) || (null == target)) {
            return;
        }

        try{
        Iterator<String> it = src.keySet().iterator();
        Method[] methods = target.getClass().getMethods();
        while (it.hasNext()) {
            String key = it.next();
            if ((!(idInclude)) && ("id".equals(key))) {
                continue;
            }

            String value = src.get(key);
            String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            for (int i = 0, len = methods.length; i < len; ++i) {
                if (methods[i].getName().equalsIgnoreCase(methodName)) {
                    Class<?> type = methods[i].getParameterTypes()[0];
                    Object retValue = null;
                    if ((!(type.getName().equalsIgnoreCase("java.lang.String"))) && ("".equals(value))) {
                        retValue = null;
                    } else {
                        retValue = parseParamenter(type, value);
                    }
                    methods[i].invoke(target, new Object[]{retValue});
                }
            }
        }}catch (InvocationTargetException| IllegalAccessException| ParseException| NoSuchMethodException| ClassNotFoundException e){
            e.printStackTrace();
            throw  new ServiceException("赋值转换失败。"+e.getMessage());
        }
    }

    private static String formatParamenter(Class<?> type, Object objValue) throws ClassNotFoundException {
        String typeName = type.getName();
        String ret = "";

        type = getaClass(type, typeName);

        if (("java.util.Date".equals(typeName)) || ("java.sql.Date".equals(typeName))) {
            String value = objValue.toString().trim();
            String ft = "yyyy-MM-dd";
            if (value.indexOf(":") > 0) {
                ft = ft + " HH:mm:ss";
            }
            SimpleDateFormat format = new SimpleDateFormat(ft);
            ret = format.format((Date) objValue);

        } else if (("java.time.LocalDateTime").equals(typeName)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ret = df.format((LocalDateTime) objValue);

        } else if ("java.sql.Timestamp".equals(typeName)) {
            String ft = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(ft);
            ret = format.format((Timestamp) objValue);

        } else if ((typeName.startsWith("java.math.")) || ("java.lang.Integer".equals(typeName))
                || ("java.lang.Long".equals(typeName)) || ("java.lang.Float".equals(typeName))
                || ("java.lang.Double".equals(typeName))) {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumIntegerDigits(20);
            format.setMinimumIntegerDigits(1);
            format.setGroupingUsed(false);
            ret = format.format(objValue);
        } else {
            ret = objValue.toString();
        }
        return ret;
    }

    private static Object parseParamenter(Class<?> type, Object strValue) throws ClassNotFoundException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String value;
        String typeName = type.getName();
        Object ret;
        type = getaClass(type, typeName);
        if (typeName.equals("java.math.BigDecimal")) {
            value = strValue.toString().trim();
            ret = new BigDecimal(value);

        } else if ((typeName.startsWith("java.math.")) || ("java.util.Date".equals(typeName))) {
            value = strValue.toString().trim();
            if (value.indexOf(":") == -1) {
                value = value + " 00:00:00";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ret = format.parse(value);
        } else if (typeName.equals("java.lang.String")) {
            ret = strValue.toString();
        } else if (typeName.equals("java.time.LocalDateTime")) {
            value = strValue.toString().trim();
            if (value.indexOf(":") == -1) {
                value = value + " 00:00:00";
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ret = LocalDateTime.parse(value, df);
        } else if (typeName.equals("java.time.LocalDate")) {
            value = strValue.toString().trim();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ret = LocalDate.parse(value, df);
        } else {
            if ((typeName.equals("java.sql.Timestamp")) && (strValue != null)) {
                value = strValue.toString().trim();
                int len = value.trim().length();
                if ((len > 7) && (len < 11)) {
                    value = value + " 00:00:00.0";
                } else if ((len > 11) && (value.indexOf(".") == -1)) {
                    value = value + ".0";
                }

                strValue = value;
            } else if ((typeName.equals("java.sql.Date")) && (strValue != null)) {
                value = strValue.toString().trim();
                if (value.length() > 10) {
                    value = value.substring(0, 10);
                }

                strValue = value;
            }
            Method method = type.getMethod("valueOf", new Class[]{"java.lang.String".getClass()});
            ret = method.invoke(type, new Object[]{strValue.toString()});
        }

        return ret;
    }

    private static Class<?> getaClass(Class<?> type, String typeName) throws ClassNotFoundException {
        if (type.isPrimitive()) {
            if ("int".equals(typeName)) {
                type = Class.forName("java.lang.Integer");
            } else if ("long".equals(typeName)) {
                type = Class.forName("java.lang.Long");
            } else if ("float".equals(typeName)) {
                type = Class.forName("java.lang.Float");
            } else if ("double".equals(typeName)) {
                type = Class.forName("java.lang.Double");
            } else if ("boolean".equals(typeName)) {
                type = Class.forName("java.lang.Boolean");
            } else if ("char".equals(typeName)) {
                type = Class.forName("java.lang.Character");
            } else if ("byte".equals(typeName)) {
                type = Class.forName("java.lang.Byte");
            }
        }
        return type;
    }


    public static void valueToObject(Object target, Object src, String propertyName) throws Exception {
        if ((null == src) || (null == target)) {
            return;
        }
        Method[] methods = target.getClass().getMethods();
        String methordName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        for (int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(methordName)) {
                methods[i].invoke(target, new Object[]{src});
            }
        }
    }

    public static Object getValueFromObject(Object target, String propertyName) throws Exception {
        if ((propertyName == null) || (target == null)) {
            return null;
        }

        Method[] methods = target.getClass().getMethods();
        for (int i = 0; i < methods.length; ++i) {
            String method = methods[i].getName();
            if ((method.startsWith("get")) && (!("getClass".equals(method)))) {
                /// Class<?> type = methods[i].getReturnType();
                String key = method.replaceFirst("get", "");
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                if (propertyName.equals(key)) {
                    return methods[i].invoke(target);
                }
            }
        }
        return null;
    }

    public static void convertToModelForUpdate(Object target, Object src) throws Exception {
        if ((null == src) || (null == target)) {
            return;
        }
        if (!(target.getClass().equals(src.getClass()))) {
            throw new Exception("类型不一致,本方法两个参数必须为同一类型");
        }

        Map<String, Method> map = getAllSetMethodNames(target);

        Method[] methods = src.getClass().getMethods();
        for (int i = 0; i < methods.length; ++i) {
            String method = methods[i].getName();
            if ((method.startsWith("get")) && (!("getClass".equals(method))) && (!("getId".equalsIgnoreCase(method)))) {
                Method setMethod = (Method) map.get(method.replaceFirst("get", "set"));
                if (setMethod != null) {
                    Object value = methods[i].invoke(src);
                    if (value != null) {
                        setMethod.invoke(target, new Object[]{value});
                    }
                }
            }
        }
    }

    private static Map<String, Method> getAllSetMethodNames(Object source) throws Exception {
        Method[] methods = source.getClass().getMethods();
        Map<String, Method> map = new HashMap<>(16);

        for (int i = 0; i < methods.length; ++i) {
            String method = methods[i].getName();
            if (method.startsWith("set")) {
                map.put(method, methods[i]);
            }
        }
        return map;
    }

    public static String mapToString(Map<String, String> src) {
        if (src == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer("");
        Iterator<String> ti = src.keySet().iterator();
        while (ti.hasNext()) {
            String k = ti.next().trim();
            String v = src.get(k);
            if (k.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(k + "=" + ((v == null) ? "" : v.trim()));
            }
        }
        return sb.toString();
    }

    public static Map<String, String> stringToMap(String src) {
        if (src == null) {
            return null;
        }

        Map<String, String> map = new HashMap<>(16);

        String[] str = src.split(";");
        for (int i = 0; i < str.length; ++i) {
            if (str[i].length() > 0) {
                String[] p = str[i].split("=");
                if (p.length > 1) {
                    map.put(p[0].trim(), p[1].trim());
                } else if (p.length == 1) {
                    map.put(p[0].trim(), "");
                }
            }
        }
        return map;
    }


    public static void main(String[] args) throws Exception {

    }

}
