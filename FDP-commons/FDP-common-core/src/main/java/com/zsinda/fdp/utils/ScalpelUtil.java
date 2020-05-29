package com.zsinda.fdp.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: FDPlatform
 * @description: ScalpelUtil
 * @author: Sinda
 * @create: 2020-05-29 17:19
 */
@UtilityClass
public class ScalpelUtil {

    /**
     * 对图片进行添加域名
     * 有域名的则跳过
     *
     * @param source 原始字符串
     */
    public String addDomain(String source, String domain) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        if (source.contains("http")) {
            return source;
        }
        return domain + source;
    }
}
