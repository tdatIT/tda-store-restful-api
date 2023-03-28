package com.webapp.tdastore.data.exception;

public class CustomExceptionRuntime extends RuntimeException {
    private int code;

    public CustomExceptionRuntime(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomExceptionRuntime(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionRuntime(Throwable cause) {
        super(cause);
    }

    protected CustomExceptionRuntime(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
