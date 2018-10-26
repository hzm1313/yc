package com.cn.yc.utils;

/**
 * Created by DT167 on 2018/6/8.
 */


import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

/**
 * 加密解密程序示例
 * <p/>
 * <p/>
 * /**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 *
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class Base64Utils {

    public static void main(String[] args) {
        String s = "http://f.apiplus.net/fc3d-20.json";
        System.out.println("原字符串：" + "http://f.apiplus.net/fc3d-20.json");
        String encryptString = encryptBASE64(s);
        System.out.println("加密后：" + encryptString);
        System.out.println("解密后：" + decryptBASE64(encryptString));
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) {
        Decoder decoder = Base64.getDecoder();
        byte[] bt;
        bt = decoder.decode(key);
        return new String(bt);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) {
        Encoder encoder = Base64.getEncoder();
        byte[] bt = key.getBytes();
        return encoder.encodeToString(bt);
    }
}
