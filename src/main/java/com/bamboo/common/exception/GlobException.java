package com.bamboo.common.exception;

public class GlobException extends RuntimeException {
    private Error error;
    private Object data;

    public GlobException(Error error) {
        this((Error)error, (Object)null);
    }

    public GlobException(Error error, Object data) {
        super("code=" + error.getCode() + ",msg=" + error.getMsg() + ",data=" + data);
        this.error = error;
        this.data = data;
    }

    public GlobException(String code, Object msg) {
        this(new Error(code, msg));
    }

    public GlobException(String code, Object msg, Object data) {
        this(new Error(code, msg), data);
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Object getData() {
        return this.data;
    }

    public GlobException setData(Object data) {
        this.data = data;
        return this;
    }
}
