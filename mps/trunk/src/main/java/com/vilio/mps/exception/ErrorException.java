package com.vilio.mps.exception;

import java.util.Map;

/**
 * Created by dell on 2017/6/9/0009.
 */
public class ErrorException extends Exception {

    /**
     *
     */
    private static final long	serialVersionUID	= 1L;
    private String erroCode = null;
    private Map<String, Object> returndata;

    /**
     * @param msg
     */
    public ErrorException(String msg) {
        super(msg);
    }

    public ErrorException(String message, Map<String, Object> returndata) {
        super(message);
        this.returndata = returndata;
    }

    public ErrorException(String erroCode, String msg) {
        super(msg);
        this.erroCode = erroCode;
    }

    public String getErroCode() {
        return erroCode;
    }

    public Map<String, Object> getReturndata() {
        return returndata;
    }
}
