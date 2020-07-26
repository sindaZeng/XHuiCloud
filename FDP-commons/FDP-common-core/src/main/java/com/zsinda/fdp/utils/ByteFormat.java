package com.zsinda.fdp.utils;

/**
 * @program: mall
 * @description: 格式化操作类
 * @author: Sinda
 * @create: 2020-01-06 17:53
 **/
public class ByteFormat {
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);

        for (int i = 0; i < bArray.length; ++i) {
            String sTemp = Integer.toHexString(255 & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }

            sb.append(sTemp.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else {
            char[] hex = str.toCharArray();
            int length = hex.length / 2;
            byte[] raw = new byte[length];

            for (int i = 0; i < length; ++i) {
                int high = Character.digit(hex[i * 2], 16);
                int low = Character.digit(hex[i * 2 + 1], 16);
                int value = high << 4 | low;
                if (value > 127) {
                    value -= 256;
                }

                raw[i] = (byte) value;
            }

            return raw;
        }
    }
}
