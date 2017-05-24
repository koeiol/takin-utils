package com.koeiol.takin.framework.utils.encrypt;

import org.springframework.util.Assert;

/**
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinCrypto {

	private TakinAlgorithmType algorithmType;

	private String certificate;

	private TakinCrypto takinCryptoInstance;

	protected TakinCrypto() {

	}

	protected TakinCrypto(TakinAlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}

	public static final TakinCrypto useAlgorithm(TakinAlgorithmType algorithmType) {
		return new TakinCrypto(algorithmType);
	}

	public final TakinCrypto withCertificate(String certificate) {
		Assert.notNull(certificate, "Seed must not be null.");
		this.certificate = certificate;
		return this;
	}

	public final String encrypt(String plaintext) throws Exception {
		return getTakinCryptoInstance().doEncrypt(plaintext);
	}

	public final String decrypt(String ciphertext) throws Exception {
		return getTakinCryptoInstance().doDecrypt(ciphertext);
	}

	protected String doEncrypt(String plaintext) throws Exception {
		throw new TakinAlgorithmModeNotSupportException(algorithmType.name() + " not support encrypt.");
	}

	protected String doDecrypt(String ciphertext) throws Exception {
		throw new TakinAlgorithmModeNotSupportException(algorithmType.name() + " not support decrypt.");
	}

	protected byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	protected String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private TakinCrypto getTakinCryptoInstance() {
		if (takinCryptoInstance == null) {
			switch (algorithmType) {
				case MD5:
					takinCryptoInstance = new TakinCryptoMD5();
					break;
				case SHA1:
					takinCryptoInstance = new TakinCryptoSHA1();
					break;
				case DES:
					takinCryptoInstance = new TakinCryptoDES(certificate);
					break;
			}
		}
		return takinCryptoInstance;
	}

}
