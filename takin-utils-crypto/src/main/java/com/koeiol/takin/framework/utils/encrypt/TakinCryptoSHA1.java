package com.koeiol.takin.framework.utils.encrypt;

import java.security.MessageDigest;

/**
 * Created by xuh on 16/9/25.
 */
public class TakinCryptoSHA1 extends TakinCrypto {


	private final static String ALGORITHM = "SHA-1";

	protected TakinCryptoSHA1() {
	}

	@Override
	protected String doEncrypt(String plaintext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
		messageDigest.update(plaintext.getBytes());
		return byte2hex(messageDigest.digest());
	}

}
