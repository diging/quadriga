package edu.asu.spring.quadriga.exceptions;

/**
 * Exception to handle exceptions thrown in the REST interface.
 * 
 * @author Julia Damerow
 *
 */
public class RestException extends Exception {

    private int errorcode;
    
    private static final long serialVersionUID = 8092941011459848011L;
    
    public RestException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RestException(Throwable arg0) {
        super(arg0);
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(int errorno) {
        super();
        errorcode = errorno;

    }

    public RestException(int errorno, String message) {
        super();
        errorcode = errorno;

    }

    public RestException(int errorno, Throwable error) {
        super(error);
        errorcode = errorno;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

}
