package com.zsinda.fdp.utils;

import com.zsinda.fdp.data.Scalpel;
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

    /**
     * 对数据进行打码
     *
     * @param source 原始字符串
     */
    public String mosaic(String source, Scalpel scalpel) {
        if (source == null) {
            return null;
        }
        if (source.length() <= scalpel.before() + scalpel.after()) {
            return source;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = source.length(); i < n; i++) {
            if (i < scalpel.before()) {
                sb.append(source.charAt(i));
                continue;
            }
            if (i > (n - scalpel.after() - 1)) {
                sb.append(source.charAt(i));
                continue;
            }
            sb.append(scalpel.mosaicStr());
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        String abc = "全国禁毒工作先进集体和先进个人表彰会议23日上午在北京召开";
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0, n = abc.length(); i < n; i++) {
//            if (i < 4) {
//                sb.append(abc.charAt(i));
//                continue;
//            }
//            if (i > (n - 0 - 1)) {
//                sb.append(abc.charAt(i));
//                continue;
//            }
//            sb.append("*");
//        }
//        System.out.println();
//    }
}
