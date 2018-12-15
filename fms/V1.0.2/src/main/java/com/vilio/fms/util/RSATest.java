package com.vilio.fms.util;

public class RSATest {

    public static void main(String[] args) throws Exception {
        String filepath="C:\\Users\\xiezhilei\\Desktop\\rsa\\";

//        RSAEncrypt.genKeyPair(filepath);


//        System.out.println("--------------公钥加密私钥解密过程-------------------");
//        String plainText="ihep_公钥加密私钥解密";
//        String publickKey = RSAEncrypt.loadPublicKeyByFile(filepath);
//        System.out.println("公钥："+publickKey);
//        //公钥加密过程
//        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(publickKey),plainText.getBytes());
//        String cipher=Base64.encode(cipherData);
//        String privateKey = RSAEncrypt.loadPrivateKeyByFile(filepath);
//        System.out.println("私钥："+privateKey);
//        //私钥解密过程
//        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(privateKey), Base64.decode(cipher));
//        String restr=new String(res);
//        System.out.println("原文："+plainText);
//        System.out.println("加密："+cipher);
//        System.out.println("解密："+restr);
//        System.out.println();
//
//        System.out.println("--------------私钥加密公钥解密过程-------------------");
//        plainText="ihep_私钥加密公钥解密";
//        //私钥加密过程
//        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());
//        cipher=Base64.encode(cipherData);
//        //公钥解密过程
//        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));
//        restr=new String(res);
//        System.out.println("原文："+plainText);
//        System.out.println("加密："+cipher);
//        System.out.println("解密："+restr);
//        System.out.println();

        String content = "{\"Key\":\"e6cb999f2f0ccb41040e08d30546dad2\",\"CaseId\":\"cbcdb42e287141fbb29caefa8cb66870\",\"Ar eaCode\":\"410202\",\"ProjectName\":\"香樟公寓\",\"Address\":\"走人工 \",\"BuildArea\":1,\"PropertyID\":\"Residential\",\"ThirdProperty\":\"Apartment\",\"FloorId\":999,\"TotalFloor\":99 9,\"Toward\":\"未知\",\"TypeCode\":0}";
        String password = "slpgenquirysvcxx";
        //加密
        System.out.println("AES加密前：" + content);
        byte[] encryptResult = AESEncrypt.encrypt(content, password);
        String encryptResultStr = AESEncrypt.parseByte2HexStr(encryptResult);
        System.out.println("AES加密后：" + encryptResultStr);

        System.out.println("---------------私钥签名过程------------------");
        String signstr=RSASignature.sign(encryptResultStr,RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("签名原串："+encryptResultStr);
        System.out.println("签名串："+signstr);
        System.out.println();

        if(RSASignature.doCheck(encryptResultStr, signstr, RSAEncrypt.loadPublicKeyByFile(filepath))){
            //解密
            byte[] decryptFrom = AESEncrypt.parseHexStr2Byte(encryptResultStr);
            byte[] decryptResult = AESEncrypt.decrypt(decryptFrom,password);
            System.out.println("AES解密后：" + new String(decryptResult));
        }else{
            System.out.println("验签失败");
        }

    }
}
