package com.koeiol.takin.framework.utils.encrypt;

/**
 * Created by xuh on 16/9/25.
 */
public class TakinAlgorithmModeNotSupportException extends RuntimeException {

	public TakinAlgorithmModeNotSupportException() {
		super();
	}

	public TakinAlgorithmModeNotSupportException(String message) {
		super(message);
	}

	public TakinAlgorithmModeNotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public TakinAlgorithmModeNotSupportException(Throwable cause) {
		super(cause);
	}

	protected TakinAlgorithmModeNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
