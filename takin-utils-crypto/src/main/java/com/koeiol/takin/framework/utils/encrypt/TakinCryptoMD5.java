package com.koeiol.takin.framework.utils.encrypt;

import java.security.MessageDigest;

/**
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinCryptoMD5 extends TakinCrypto {

	private final static String ALGORITHM = "MD5";

	protected TakinCryptoMD5() {
	}

	@Override
	protected String doEncrypt(String plaintext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
		messageDigest.update(plaintext.getBytes());
		return byte2hex(messageDigest.digest());
	}

}
