package com.koeiol.takin.framework.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * Created by xuh on 16/9/26.
 */
public class TakinCryptoDES extends TakinCrypto {

	private final static String ALGORITHM = "DES";

	private String cryptKey = "www.koeiol.com";

	private SecureRandom secureRandom = new SecureRandom();
	private SecretKey secretKey;
	private Cipher cipher;

	protected TakinCryptoDES(String cryptKey) {
		if (cryptKey != null && cryptKey.trim().length() > 0) {
			this.cryptKey = cryptKey;
		}
		try {
			secretKey = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new DESKeySpec(this.cryptKey.getBytes()));
			cipher = Cipher.getInstance(ALGORITHM);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String doEncrypt(String plaintext) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
		return new String(byte2hex(cipher.doFinal(plaintext.getBytes())));
	}

	@Override
	protected String doDecrypt(String ciphertext) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom);
		return new String(cipher.doFinal(hex2byte(ciphertext.getBytes())));
	}

}
