package com.duke.web.error;

/**
 * Created by duke on 2017/12/25
 */
public class UserNotFoundException extends RuntimeException {

    private String id;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
