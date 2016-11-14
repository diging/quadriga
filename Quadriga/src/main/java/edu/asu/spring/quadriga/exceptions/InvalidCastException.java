package edu.asu.spring.quadriga.exceptions;

/**
 * @author Nischal Samji Custom exception thrown when an object is improperly
 *         cast to either a string or a domain object.
 */
public class InvalidCastException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1150630721953424128L;

    public InvalidCastException() {
        super();

    }

    public InvalidCastException(String message, Throwable cause) {
        super(message, cause);

    }

    public InvalidCastException(String message) {
        super(message);

    }

    public InvalidCastException(Throwable cause) {
        super(cause);

    }

}
