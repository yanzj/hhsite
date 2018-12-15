package com.vilio.bps.inquiry.util;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class CaadDemo {
	public static void main(String[] args) throws Exception {
		Map map = new HashMap();
		map.put("CaseId",	UUID.randomUUID().toString().toLowerCase().replace("-", ""));
		map.put("Key", "a1b20c2a3f10c235962408d37e0206d5");
		map.put("TypeCode", 0);
		map.put("AreaCode", "410202");
		map.put("PropertyID ", "Residential");
		map.put("ThirdProperty", "Apartment");
		map.put("Address", "香樟公寓2期2栋");
		map.put("BuildArea", 100);
		map.put("ProjectName", "香樟公寓");
		String content = JSONObject.fromObject(map).toString();
		System.out.println(content);
		String privateKey = "<RSAKeyValue><Modulus>p3hLthaU4ei0dWI1QP99JaVYbghOooJWet7bNXyOWyOAQQzfITRbMCGaBZXg7M0w2SMtxyPr+w54kcsQQfDpMn61BXpWLg/5lYvYKkIG5DzFmhjyUwUC+g91bR7hHfj55YOFehvUwX6x6CFC9CQ2jJRsIWfqT3pTFfrFmfmgdiM=</Modulus><Exponent>AQAB</Exponent><P>0JvjbXXIb6ndTeDVlIWZeJZ3CYxMIXII8qvKQ0cYh3K0NOQLFZJIcCQitJ7rL2gNadDLijr5p+fp9ovZlbKX6Q==</P><Q>zYPgWmK47mje4MUF+KzZNQ05SPj/iU/nL+X3v90d9eat1Rtf7iito0qsdFp3LgTfle294jrEJy6ji5CZpFQiKw==</Q><DP>w2Vg70RlzAHlom64X3eMOyFkunLJVIKF0xgKSl4roaNVHD2GDFyKsU+HmntIe40RE05ZeE6pThayVRbFZax1EQ==</DP><DQ>UlNqsypq5G40IhwqyTQMirjyYq4ER3AvrztTJJOiJdgzeHPP2OqIrCoErVNz/IZNPpUPBKn/26ZOM2FIetCNIw==</DQ><InverseQ>rPqhs20F8gxmLbgqIj0EOloUaQIe+AdIrgqNQYr67J7YE4m7+pKutOt+TMl8HsOQR0Gpyf7FsoPouIJpJMy2vw==</InverseQ><D>NYKiCnYPr1loI+Oz6WdZSQiah1n/KjzkPhFsUJxSbjubNO3Uc+sjQe9So/s+adusgo0TiQBo3AjFLKyKLs+36wavmlTahC45+XUmaHCuOvTFnMlquAE2K+I14swToZFmeewAEl2cjFtjDpzzAPHYJbXfe+L8Xbktt9t6DOgGysk=</D></RSAKeyValue>";
		String publicKey = "<RSAKeyValue><Modulus>6TugMLi2nrkd7WGgBA9o4rgQ73Xk9SzTWqaNfH+f8ahTulMGYvmPWv6mF3xMkRhVLwQWX0zu3+d9Ofzni000XyfQsnk9GPfJ4o/yxaU6axB6ys8oy23ON+k1+571bPu8Ifh4shSxTHpzv5CTFkefsHDqQI3NXp/pA1ArzCsNsVE=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
		String aesKey = "slpgenquirysvcxx";
		// 加密
		String cryptContent = AES.encrypt(content, aesKey);
		String dCryptContent = AES.decrypt(cryptContent, aesKey);
		// 加签
		String signData = Sha1RSA.sign(cryptContent, privateKey);

		// 组合
		String data = cryptContent + "," + signData;
		System.out.println(data);
		Map mapParam = new HashMap();
		mapParam.put("Data", data);
		String tmp = JSONObject.fromObject(mapParam).toString();
		//测试环境内网ip	"http://172.16.2.1:8077/api/webapiaccess/PublicTestEnquiry/Enquiry",
		//测试环境外网ip    "http://116.228.48.92:8077/api/webapiaccess/PublicTestEnquiry/Enquiry",
		String result =
				WebUtil.sendPost(
						"http://116.228.48.92:8077/api/webapiaccess/PublicTestEnquiry/Enquiry",tmp
						);
        System.out.println(result);
		if (!Sha1RSA.doCheck(result.split(",")[0], result.split(",")[1],publicKey)) {
			System.out.println("验签失败");
		} else {
			System.out.println(AES.decrypt(result.split(",")[0], aesKey));
		}

	}
}
