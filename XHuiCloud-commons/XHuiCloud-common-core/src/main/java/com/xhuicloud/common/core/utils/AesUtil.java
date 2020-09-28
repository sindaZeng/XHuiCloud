package com.xhuicloud.common.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/20 1:06 上午
 */
public class AesUtil {

    private static String encodeKey = "KjQlb%1aV1Jrli79";  //16位

    private static String KEY_ALGORITHM = "AES";

    public static String decrypt(String arg) {
        AES aes = new AES(Mode.CBC, Padding.NoPadding, new SecretKeySpec(encodeKey.getBytes(), KEY_ALGORITHM),
                new IvParameterSpec(encodeKey.getBytes()));
        // 获取请求密码并解密
        return new String(aes.decrypt(Base64.decode(arg.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8).trim();
    }


}

