package edu.asu.spring.quadriga.exceptions;
/**
 * This is used to handle the exception occurred by trying to access the pages they are not authorized
 * @author satyaswaroop Boddu
 *
 */
public class QuadrigaAccessException extends QuadrigaException {

	private static final long serialVersionUID = -6019469278891056730L;

	public QuadrigaAccessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuadrigaAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public QuadrigaAccessException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public QuadrigaAccessException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
