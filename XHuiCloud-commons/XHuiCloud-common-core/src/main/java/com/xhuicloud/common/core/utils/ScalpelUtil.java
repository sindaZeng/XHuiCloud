/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.core.utils;

import com.xhuicloud.common.core.annotation.Scalpel;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: XHuiCloud
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
        StringBuilder sb = new StringBuilder();
        if (scalpel.length() > 0) {
            for (int i = 0; i < scalpel.length(); i++) {
                sb.append(scalpel.mosaicStr());
            }
            return sb.toString();
        }
        if (source.length() <= scalpel.before() + scalpel.after()) {
            return source;
        }
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
