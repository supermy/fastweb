package org.supermy.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.providers.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

/**
 * 加密管理类
 * 
 * 
 */
public class PasswordProcessing {

	private static final Log log = LogFactory.getLog(PasswordProcessing.class);

	public static void processMd5() {
		log.info("以MD5方式加密......................");

		// 直接指定待采用的加密算法（MD5）
		MessageDigestPasswordEncoder mdpeMd5 = new MessageDigestPasswordEncoder(
				"MD5");
		// 生成32位的Hex版, 这也是encodeHashAsBase64的默认值
		mdpeMd5.setEncodeHashAsBase64(false);
		log.info(mdpeMd5.encodePassword("123456", null));
		// 生成24位的Base64版
		mdpeMd5.setEncodeHashAsBase64(true);
		log.info(mdpeMd5.encodePassword("123456", null));

		// 等效于上述代码
		Md5PasswordEncoder mpe = new Md5PasswordEncoder();
		mpe.setEncodeHashAsBase64(false);
		log.info(mpe.encodePassword("123456", null));
		mpe.setEncodeHashAsBase64(true);
		log.info(mpe.encodePassword("123456", null));
	}

	public static void processSha() {
		log.info("以SHA方式加密......................");

		// 直接指定待采用的加密算法（SHA）及加密强度（256）
		MessageDigestPasswordEncoder mdpeSha = new MessageDigestPasswordEncoder(
				"SHA-256");
		mdpeSha.setEncodeHashAsBase64(false);
		log.info(mdpeSha.encodePassword("123456", null));
		mdpeSha.setEncodeHashAsBase64(true);
		log.info(mdpeSha.encodePassword("123456", null));

		// 等效于上述代码
		ShaPasswordEncoder spe = new ShaPasswordEncoder(256);
		spe.setEncodeHashAsBase64(false);
		log.info(spe.encodePassword("123456", null));
		spe.setEncodeHashAsBase64(true);
		log.info(spe.encodePassword("123456", null));
	}

	public static void processSalt() {
		log.info("以MD5方式加密、加私钥(盐)......................");

		Md5PasswordEncoder mpe = new Md5PasswordEncoder();
		mpe.setEncodeHashAsBase64(false);

		// 等效的两行地代码
		log.info(mpe.encodePassword("password{javaee}", null)); // javaee为密码私钥
		log.info(mpe.encodePassword("password", "javaee")); // javaee为密码私钥
		// 结果：87ce7b25b469025af0d5c6752038fb56
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		processMd5();
		processSha();
		processSalt();
	}

}
