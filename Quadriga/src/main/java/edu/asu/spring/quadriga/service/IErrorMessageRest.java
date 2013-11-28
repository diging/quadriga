package edu.asu.spring.quadriga.service;

import edu.asu.spring.quadriga.exceptions.RestException;

public interface IErrorMessageRest {

	public abstract String getErrorMsg(String errorMsg)
			throws RestException;

}