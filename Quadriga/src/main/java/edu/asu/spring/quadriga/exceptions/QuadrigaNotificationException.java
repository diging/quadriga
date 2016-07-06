package edu.asu.spring.quadriga.exceptions;

public class QuadrigaNotificationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5203233497728362534L;

    public QuadrigaNotificationException() {
        super();
    }

    public QuadrigaNotificationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QuadrigaNotificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuadrigaNotificationException(String message) {
        super(message);
    }

    public QuadrigaNotificationException(Throwable cause) {
        super(cause);
    }

}
