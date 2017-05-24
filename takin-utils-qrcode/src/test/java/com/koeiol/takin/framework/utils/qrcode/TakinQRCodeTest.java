package com.koeiol.takin.framework.utils.qrcode;

import org.junit.Test;

import java.io.File;

/**
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinQRCodeTest {

	@Test
	public void toFile() throws Exception {
		File f = TakinQRCode.from("http://www.koeiol.com").withSize(512,512).toFile();
		// FIXME move to some path. cause TakinQRCode will remove the temp-file.
		System.out.println(f.getAbsolutePath());
		System.out.println("---");
	}

}