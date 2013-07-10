package edu.asu.spring.quadriga.exceptions;

/**
 * This is an exception thrown when there is a storage problem.
 * @author Lohith Dwaraka
 *
 */
public class QuadrigaUIAccessException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3850218568287788164L;
	
	/**
	 * default storage exception
	 */
	public QuadrigaUIAccessException() {
		super();
	}
	
	/**
	 * Custom message in the exception
	 * @param customMsg
	 */
	public QuadrigaUIAccessException(String customMsg) {
		super(customMsg);
	}


	
	public QuadrigaUIAccessException(Exception e)
	{
		super(e);
	}


	public QuadrigaUIAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	

}
