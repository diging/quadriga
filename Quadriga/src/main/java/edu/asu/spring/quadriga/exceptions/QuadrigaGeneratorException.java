package edu.asu.spring.quadriga.exceptions;

public class QuadrigaGeneratorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6971457774407890159L;

    public QuadrigaGeneratorException() {
        super();
    }

    public QuadrigaGeneratorException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QuadrigaGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuadrigaGeneratorException(String message) {
        super(message);
    }

    public QuadrigaGeneratorException(Throwable cause) {
        super(cause);
    }

    
}
