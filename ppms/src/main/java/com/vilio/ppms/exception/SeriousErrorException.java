package com.vilio.ppms.exception;

import java.util.Map;

/**
 * 需监控异常
 */
public class SeriousErrorException extends ErrorException {

	public SeriousErrorException(String msg) {
		super(msg);
	}

	public SeriousErrorException(String message, Map<String, Object> returndata) {
		super(message, returndata);
	}

	public SeriousErrorException(String erroCode, String msg) {
		super(erroCode, msg);
	}
}
