package com.wczx.api.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数生成工具
 */
@Component
public class UUIDUtil {




	/**
	 * 通过 UUID 生成 32 位随机字符串
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().trim().replace("-", "");
	}

	/**
	 * 生成 指定位数 大小写随机的字符串
	 * @param length 指定位数
	 */
	public static String getRandomStr(int length){
		if(length <= 0) {
			throw new IllegalArgumentException("位数指定的值 不能小于等于 0");
		}

		Random random = new Random();

		char[] randomChars = new char[length];

		for(int i = 0; i < length; i++){
			// 0 大写, 1 小写
			int strCase = random.nextInt(2);
			randomChars[i] = (char) (65 + 32 * strCase + random.nextInt(26));
		}

		return new String(randomChars);
	}


}
