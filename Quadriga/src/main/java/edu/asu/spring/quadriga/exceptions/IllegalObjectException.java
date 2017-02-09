package edu.asu.spring.quadriga.exceptions;

/**
 * Custom exception thrown when an object is improperly cast to either a string
 * or a domain object.
 * 
 * @author Nischal Samji
 */
public class IllegalObjectException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1150630721953424128L;

    public IllegalObjectException() {
        super();
    }

    public IllegalObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalObjectException(String message) {
        super(message);
    }

    public IllegalObjectException(Throwable cause) {
        super(cause);
    }

}
