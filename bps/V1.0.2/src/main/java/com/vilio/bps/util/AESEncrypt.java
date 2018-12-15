package com.vilio.bps.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by xiezhilei on 2017/4/6.
 */
public class AESEncrypt {

    /**
     * AES加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        String content = "ihep_公钥加密私钥解密sadfasdfasdfasdfsadfasdfsdadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "sdaffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的" +
                "撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的" +
                "发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬" +
                "阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的" +
                "撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的" +
                "发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿" +
                "斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发" +
                "生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到" +
                "发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发" +
                "生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发" +
                "阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到" +
                "发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿" +
                "斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发" +
                "送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯" +
                "蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒" +
                "发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到" +
                "发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿" +
                "发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发" +
                "生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发" +
                "送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬" +
                "阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到" +
                "的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥" +
                "的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂" +
                "芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到" +
                "的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥" +
                "的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯" +
                "蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送" +
                "到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥" +
                "的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬" +
                "阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒" +
                "发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送" +
                "到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿" +
                "发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发" +
                "阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发" +
                "送到发斯蒂芬阿斯顿发生大阿斯顿发送到的撒发生的发阿斯顿发送到啥的发送到发送到发送到发斯蒂芬阿斯顿发生";
        String password = "12345678";
        //加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        //解密
        byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = decrypt(decryptFrom,password);
        System.out.println("解密后：" + new String(decryptResult));
    }

}
