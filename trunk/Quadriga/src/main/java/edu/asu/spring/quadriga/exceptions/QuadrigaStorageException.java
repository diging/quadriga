package edu.asu.spring.quadriga.exceptions;

public class QuadrigaStorageException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3850218568287768164L;
	
	public QuadrigaStorageException() {
		super();
	}
	
	public QuadrigaStorageException(String customMsg) {
		super(customMsg);
	}
}
