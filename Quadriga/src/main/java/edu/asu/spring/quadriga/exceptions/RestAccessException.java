package edu.asu.spring.quadriga.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.IErrorMessageRest;


/**
 * Exception to handle exceptions thrown in the REST interface.
 * @author Lohith Dwaraka 
 *
 */
@Service
public class RestAccessException{
	
	@Autowired
	private IErrorMessageRest errorMessageRest;

	private int errorcode;
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	
	public String accessErrorMsg(int errorno, String message) throws RestException {
		setErrorcode(errorno);
		String errorMsg = errorMessageRest.getErrorMsg(message);
		return errorMsg;
	}

	
}
