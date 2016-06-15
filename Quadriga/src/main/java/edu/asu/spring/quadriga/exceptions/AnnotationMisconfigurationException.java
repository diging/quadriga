package edu.asu.spring.quadriga.exceptions;

public class AnnotationMisconfigurationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -149263156347301334L;

    public AnnotationMisconfigurationException() {
        super();
    }

    public AnnotationMisconfigurationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnnotationMisconfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationMisconfigurationException(String message) {
        super(message);
    }

    public AnnotationMisconfigurationException(Throwable cause) {
        super(cause);
    }

}
