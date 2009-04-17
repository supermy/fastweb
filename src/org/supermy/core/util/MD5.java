package org.supermy.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.providers.encoding.MessageDigestPasswordEncoder;

/**
 * @author my
 * 
 */
public class MD5 {
	private static Logger log = LoggerFactory.getLogger(MD5.class);

	public static void main(String[] args) {
		log.debug(getMd5("123456"));
	}

	// 直接指定待采用的加密算法（MD5）
	private static final MessageDigestPasswordEncoder mdpeMd5 = new MessageDigestPasswordEncoder(
			"MD5");

	public static String getMd5(String xxx) {
		// 生成32位的Hex版, 这也是encodeHashAsBase64的默认值
		mdpeMd5.setEncodeHashAsBase64(false);
		String encodePassword = mdpeMd5.encodePassword(xxx, null);
		log.info("{} *md5={}", xxx, encodePassword);
		return encodePassword;

	}

}
