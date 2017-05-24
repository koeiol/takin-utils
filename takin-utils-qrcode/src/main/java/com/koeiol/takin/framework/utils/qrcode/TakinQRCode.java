package com.koeiol.takin.framework.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 *
 *
 * <ul>
 *     Sample:
 *     <li>TakinQRCode.from(String context).withXXX().withXXX().toFile([name])</li>
 *     <li>TakinQRCode.from(String context).withXXX().withXXX().toStram([stream])</li>
 * </ul>
 *
 *
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinQRCode {

	private final String content;

	private TakinImageType imageType = TakinImageType.PNG;

	protected static final int DEFAULT_SIZE = 125;
	private int width = DEFAULT_SIZE;
	private int height = DEFAULT_SIZE;

	protected static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();
	private MatrixToImageConfig matrixToImageConfig = DEFAULT_CONFIG;

	protected Writer qrWriter;

	protected static final String DEFAULT_TEMP_FILENAME = "TakinQRCode";
	protected final HashMap<EncodeHintType, Object> hints = new HashMap<>();

	protected TakinQRCode(String content) {
		this.content = content;
		this.qrWriter = new QRCodeWriter();
	}

	public static TakinQRCode from(String content) {
		return new TakinQRCode(content);
	}

	public static TakinQRCode from(Object toString) {
		return new TakinQRCode(toString.toString());
	}

	public TakinQRCode withType(TakinImageType imageType) {
		this.imageType = imageType;
		return this;
	}

	public TakinQRCode withSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public TakinQRCode withColor(int onColor, int offColor) {
		matrixToImageConfig = new MatrixToImageConfig(onColor, offColor);
		return this;
	}

	public TakinQRCode withCharset(String charset) {
		return withHint(EncodeHintType.CHARACTER_SET, charset);
	}

	public TakinQRCode withHint(EncodeHintType hintType, Object value) {
		hints.put(hintType, value);
		return this;
	}

	public File toFile() {
		return toFile(DEFAULT_TEMP_FILENAME);
	}

	public File toFile(String name) {
		try {
			File file = createTempFile(name);
			MatrixToImageWriter.writeToPath(createBitMatrix(), imageType.toString(), file.toPath(), matrixToImageConfig);
			return file;
		} catch (IOException | WriterException e) {
			throw new TakinQRCodeException("Failed to create QR image from text due to underlying exception", e);
		}
	}

	public ByteArrayOutputStream toStream() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		toStream(stream);
		return stream;
	}

	public void toStream(OutputStream outputStream) {
		try {
			MatrixToImageWriter.writeToStream(createBitMatrix(), imageType.toString(), outputStream, matrixToImageConfig);
		} catch (IOException | WriterException e) {
			throw new TakinQRCodeException("Failed to create QR image from text due to underlying exception", e);
		}
	}

	protected File createTempFile() throws IOException {
		return createTempFile(DEFAULT_TEMP_FILENAME);
	}

	protected BitMatrix createBitMatrix() throws WriterException {
		return qrWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
	}

	protected File createTempFile(String name) throws IOException {
		File file = File.createTempFile(name, "." + imageType.toString().toLowerCase());
		file.deleteOnExit();
		return file;
	}

}
