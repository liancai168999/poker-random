package com.bingo.core.enums;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 枚举处理工具类
 */
public class EnumUtils {

    public static void main(String[] args) {


        System.out.println(GenderEnum.MAN.Name());
    }




    /**
     * <p>
     * 值映射为枚举
     * </p>
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       对应枚举
     * @return
     */
    public static <E extends Enum<?> & IEnum> E valueOf(Class<E> enumClass, Object value) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            if (Objects.equals(e.getValue(), value)) {
                return e;
            }
        }
        return null;
    }

    public static <E extends Enum<?>> E valueOf(Class<E> enumClass, Object value, Field enumField) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            try {
                if (Objects.equals(enumField.get(e), value)) {
                    return e;
                }
            } catch (IllegalAccessException e1) {

            }
        }
        return null;
    }

}
