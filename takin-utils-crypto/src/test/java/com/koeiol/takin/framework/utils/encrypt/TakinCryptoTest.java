package com.koeiol.takin.framework.utils.encrypt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xuh on 16/9/26.
 */
public class TakinCryptoTest {

	private String salt = "http://www.koeiol.com";

	private long startTime;
	private String algorithm;

	@Before
	public void setUp() throws Exception {
		startTime = System.currentTimeMillis();
	}

	@After
	public void tearDown() throws Exception {
		long endTime = System.currentTimeMillis();
		System.out.println("algorithm(" + algorithm + ").cost====" + (endTime - startTime));
	}

	@Test
	public void testDES_E() throws Exception {
		algorithm = "DES-Encrypt";
		String encrypt = TakinCrypto.useAlgorithm(TakinAlgorithmType.DES).withCertificate(salt).encrypt("111111");
		Assert.assertEquals("F06A5A207BE3DA2A", encrypt);
	}

	@Test
	public void testDES_D() throws Exception {
		algorithm = "DES-Decrypt";
		String decrypt = TakinCrypto.useAlgorithm(TakinAlgorithmType.DES).withCertificate(salt).decrypt("F06A5A207BE3DA2A");
		Assert.assertEquals("111111", decrypt);
	}

	@Test
	public void testMD5() throws Exception {
		algorithm = "MD5";
		String encrypt = TakinCrypto.useAlgorithm(TakinAlgorithmType.MD5).encrypt("111111");
		Assert.assertEquals("96e79218965eb72c92a549dd5a330112".toUpperCase(), encrypt);
	}

	@Test
	public void testSHA1() throws Exception {
		algorithm = "SHA-1";
		String encrypt = TakinCrypto.useAlgorithm(TakinAlgorithmType.SHA1).encrypt("111111");
		Assert.assertEquals("3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d".toUpperCase(), encrypt);
	}
}