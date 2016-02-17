package edu.asu.spring.quadriga.exceptions;

/**
 * This exception indicates that the requested or provided role doesn't exist.
 * 
 * @author Julia Damerow
 *
 */
public class NoSuchRoleException extends QuadrigaException {

    /**
     * 
     */
    private static final long serialVersionUID = 7620899286009954445L;

    public NoSuchRoleException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public NoSuchRoleException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public NoSuchRoleException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public NoSuchRoleException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
