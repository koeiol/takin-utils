package com.koeiol.takin.framework.utils.qrcode;

/**
 * Created by koeiol@github.com on 16/9/25.
 */
public class TakinQRCodeException extends RuntimeException {

    public TakinQRCodeException() {
        super();
    }

    public TakinQRCodeException(String message) {
        super(message);
    }

    public TakinQRCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TakinQRCodeException(Throwable cause) {
        super(cause);
    }

    protected TakinQRCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
