package edu.asu.spring.quadriga.exceptions;

/**
 * This is an exception thrown when there is any QStore related retrieve issue.
 * @author Lohith Dwaraka
 */
public class QStoreStorageException extends QuadrigaException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3850218568287768164L;
	
	/**
	 * default storage exception
	 */
	public QStoreStorageException() {
		super();
	}
	
	/**
	 * Custom message in the exception
	 * @param customMsg
	 */
	public QStoreStorageException(String customMsg) {
		super(customMsg);
	}


	
	public QStoreStorageException(Exception e)
	{
		super(e);
	}


	public QStoreStorageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	

}
