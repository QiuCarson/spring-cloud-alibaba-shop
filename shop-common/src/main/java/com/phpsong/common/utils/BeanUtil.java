package com.phpsong.common.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BeanUtils 对象属性复制
 *
 * @author itoak
 * @date 2019-10-09
 * @time 17:11
 * @desc 支持单个对象属性复制、列表对象的属性复制
 */
public class BeanUtil {

    /**
     * 单个对象属性复制
     *
     * @param source 复制源
     * @param clazz  目标对象class
     * @param <T>    目标对象类型
     * @param <M>    源对象类型
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> clazz) {
        if (Objects.isNull(source) || Objects.isNull(clazz))
            throw new IllegalArgumentException();
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 列表对象属性复制
     *
     * @param sources 源对象列表
     * @param clazz   目标对象class
     * @param <T>     目标对象类型
     * @param <M>     源对象类型
     * @return 目标对象列表
     */
    public static <T, M> List<T> copyList(List<M> sources, Class<T> clazz) {
        if (Objects.isNull(sources) || Objects.isNull(clazz))
            throw new IllegalArgumentException();
        return Optional.of(sources)
                .orElse(Lists.newArrayList())
                .stream().map(m -> copyProperties(m, clazz))
                .collect(Collectors.toList());
    }
}