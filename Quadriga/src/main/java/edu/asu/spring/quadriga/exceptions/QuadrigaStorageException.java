package edu.asu.spring.quadriga.exceptions;

/**
 * This is an exception thrown when there is a storage problem.
 * @author Julia Damerow
 * @author Lohith Dwaraka
 */
public class QuadrigaStorageException extends QuadrigaException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3850218568287768164L;
	
	/**
	 * default storage exception
	 */
	public QuadrigaStorageException() {
		super();
	}
	
	/**
	 * Custom message in the exception
	 * @param customMsg
	 */
	public QuadrigaStorageException(String customMsg) {
		super(customMsg);
	}


	
	public QuadrigaStorageException(Exception e)
	{
		super(e);
	}


	public QuadrigaStorageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	

}
