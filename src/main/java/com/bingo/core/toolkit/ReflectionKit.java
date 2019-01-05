package com.bingo.core.toolkit;

import com.bingo.core.exceptions.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

/**
 * 反射工具类
 */
public class ReflectionKit {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionKit.class);
    /**
     * 反射 method 方法名，例如 getId
     * @param field
     * @param str
     * @return
     */
    public static String getMethodCapitalize(Field field, final String str) {
        Class<?> fieldType = field.getType();
        // fix #176
        return StringUtils.concatCapitalize(boolean.class.equals(fieldType) ? "is" : "get", str);
    }

    /**
     * 反射 method 方法名，例如 setVersion
     * @param field
     * @param str
     * @return
     */
    public static String setMethodCapitalize(Field field, final String str) {
        Class<?> fieldType = field.getType();

        return StringUtils.concatCapitalize("set", str);
    }

    /**
     *  获取 public get方法的值
     * @param cls
     * @param entity
     * @param str
     * @return
     */
    public static Object getMethodValue(Class<?> cls, Object entity, String str) {
        Map<String, Field> fieldMaps = getFieldMap(cls);
        try {
            if (CollectionUtils.isEmpty(fieldMaps)) {
                throw ExceptionUtils.mbg(String.format("Error: NoSuchField in %s for %s.  Cause:", cls.getSimpleName(), str));
            }
            Method method = cls.getMethod(getMethodCapitalize(fieldMaps.get(str), str));
            return method.invoke(entity);
        } catch (NoSuchMethodException e) {
            throw ExceptionUtils.mbg(String.format("Error: NoSuchMethod in %s.  Cause:", cls.getSimpleName()) + e);
        } catch (IllegalAccessException e) {
            throw ExceptionUtils.mbg(String.format("Error: Cannot execute a private method. in %s.  Cause:",
                    cls.getSimpleName()) + e);
        } catch (InvocationTargetException e) {
            throw ExceptionUtils.mbg("Error: InvocationTargetException on getMethodValue.  Cause:" + e);
        }
    }

    /**
     * 获取 public get方法的值
     * @param entity
     * @param str
     * @return
     */
    public static Object getMethodValue(Object entity, String str) {
        if (null == entity) {
            return null;
        }
        return getMethodValue(entity.getClass(), entity, str);
    }

    /**
     * 反射对象获取泛型
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                    clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 获取该类的所有属性列表
     * @param clazz
     * @return
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);
        if (CollectionUtils.isNotEmpty(fieldList)) {
            Map<String, Field> fieldMap = new LinkedHashMap<>();
            fieldList.forEach(field -> fieldMap.put(field.getName(), field));
            return fieldMap;
        }
        return Collections.emptyMap();
    }

    /**
     * 获取该类的所有属性列表
     * @param clazz
     * @return
     */
    public static List<Field> getFieldList(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        List<Field> fieldList = Stream.of(clazz.getDeclaredFields())
                /* 过滤静态属性 */
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                /* 过滤 transient关键字修饰的属性 */
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .collect(toCollection(LinkedList::new));
        /* 处理父类字段 */
        Class<?> superClass = clazz.getSuperclass();
        if (superClass.equals(Object.class)) {
            return fieldList;
        }
        /* 排除重载属性 */
        return excludeOverrideSuperField(fieldList, getFieldList(superClass));
    }

    /**
     * 排序重置父类属性
     * @param fieldList
     * @param superFieldList
     * @return
     */
    public static List<Field> excludeOverrideSuperField(List<Field> fieldList, List<Field> superFieldList) {
        // 子类属性
        Map<String, Field> fieldMap = fieldList.stream().collect(toMap(Field::getName, identity()));
        superFieldList.stream().filter(field -> fieldMap.get(field.getName()) == null).forEach(fieldList::add);
        return fieldList;
    }


}
