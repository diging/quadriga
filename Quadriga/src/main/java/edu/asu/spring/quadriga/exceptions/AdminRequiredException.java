package edu.asu.spring.quadriga.exceptions;

public class AdminRequiredException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3304547577398828819L;

    public AdminRequiredException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AdminRequiredException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public AdminRequiredException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public AdminRequiredException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public AdminRequiredException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
