package com.koeiol.takin.framework.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

/**
 * <ul>
 * Sample:
 * <li>TakinQRCode.from(String context).withXXX().withXXX().toFile([name])</li>
 * <li>TakinQRCode.from(String context).withXXX().withXXX().toStram([stream])</li>
 * </ul>
 * <p>
 * <p>
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinQRCode {

    private final String content;

    private TakinImageType imageType = TakinImageType.PNG;

    protected static final int DEFAULT_SIZE = 125;
    private int width = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;
    private File logo;

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

    public TakinQRCode withLogo(String filename) {
        return withLogo(new File(filename));
    }

    private TakinQRCode withLogo(File file) {
        logo = file;
        return this;
    }


    public File toFile() {
        return toFile(DEFAULT_TEMP_FILENAME);
    }

    public File toFile(String name) {
        try {
            File file = createTempFile(name);
            OutputStream outputStream = new FileOutputStream(file);
            toStream(outputStream);
            outputStream.close();
//			MatrixToImageWriter.writeToPath(createBitMatrix(), imageType.toString(), file.toPath(), matrixToImageConfig);
            return file;
        } catch (IOException e) {
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
            if (logo != null) {
                int logoTop = 50, logoLeft = 50;

                BufferedImage logoImage = zoomImage(ImageIO.read(logo));
                Point logoPosition = calcPosition(logoImage);

                BufferedImage combinedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) combinedImage.getGraphics();
                g.drawImage(MatrixToImageWriter.toBufferedImage(createBitMatrix()), 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g.drawImage(logoImage, logoPosition.x, logoPosition.y, null);
                ImageIO.write(combinedImage, imageType.name(), outputStream);
            } else {
                MatrixToImageWriter.writeToStream(createBitMatrix(), imageType.toString(), outputStream, matrixToImageConfig);
            }
        } catch (IOException | WriterException e) {
            throw new TakinQRCodeException("Failed to create QR image from text due to underlying exception", e);
        }
    }

    private Point calcPosition(BufferedImage image) {
        return new Point((width - image.getWidth()) / 2, (height - image.getHeight()) / 2);
    }

    private BufferedImage zoomImage(BufferedImage image) {
        float maxWidth = width / 3.5f, maxHeight = height / 3.5f;
        float rate = Math.min(maxWidth / image.getWidth(), maxHeight / image.getHeight());
        BufferedImage newImage = new BufferedImage((int) (image.getWidth() * rate), (int) (image.getHeight() * rate), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.scale(rate, rate);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
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
