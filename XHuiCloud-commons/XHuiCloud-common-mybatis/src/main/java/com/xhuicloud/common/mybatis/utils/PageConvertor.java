package com.xhuicloud.common.mybatis.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/10/20
 */
@UtilityClass
public class PageConvertor {

    public <T> Page<T> convertPage(Page<T> pageInfo, Class<T> targetClazz) {
        return convertPage(pageInfo, targetClazz, "");
    }

    public <T> Page<T> convertPage(Page<?> page, Class<T> targetClazz, String... ignoreProperties) {
        if (null == page) {
            return null;
        } else {
            List<T> list = convertObjects(page.getRecords(), targetClazz, ignoreProperties);
            Page<T> newPageInfo = (Page) convert(page, Page.class);
            newPageInfo.setRecords(list);
            return newPageInfo;
        }
    }

    public static <T> List<T> convertObjects(List<?> objects, Class<T> targetClazz, String... ignoreProperties) {
        List<T> targetObjects = new ArrayList();
        objects.stream().forEach((object) -> {
            T targetObject = convert(object, targetClazz, ignoreProperties);
            targetObjects.add(targetObject);
        });
        return targetObjects;
    }

    public static <T> T convert(Object object, Class<T> targetClazz, String... ignoreProperties) {
        if (null == object) {
            return null;
        } else {
            Object target = null;
            try {
                target = targetClazz.newInstance();
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
            BeanUtils.copyProperties(object, target, ignoreProperties);
            return (T) target;
        }
    }
}
