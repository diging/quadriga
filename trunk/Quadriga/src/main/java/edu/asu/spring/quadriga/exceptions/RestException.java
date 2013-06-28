package edu.asu.spring.quadriga.exceptions;

/**
 * Exception to handle exceptions thrown in the REST interface.
 * @author Julia Damerow
 *
 */
public class RestException extends Exception {

	
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

	
}
