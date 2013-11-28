package edu.asu.spring.quadriga.exceptions;


/**
 * Exception to handle exceptions thrown in the REST interface.
 * @author Julia Damerow
 *
 */
public class RestAccessException extends Exception {

	private int errorcode;
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	private static final long serialVersionUID = 8092941011459848011L;

	public RestAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RestAccessException(Throwable arg0) {
		super(arg0);
	}
	
	public RestAccessException(String message) {
		super(message);
	}
	public RestAccessException(int errorno) {
		super();
		errorcode = errorno;
		
	}
	public RestAccessException(int errorno, String message) {
		super();
		errorcode = errorno;
		
	}

	
}
