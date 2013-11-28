package edu.asu.spring.quadriga.service;

import javax.servlet.http.HttpServletRequest;

import edu.asu.spring.quadriga.exceptions.RestException;

public interface IErrorMessageRest {

	public abstract String getErrorMsg(String errorMsg, HttpServletRequest req)
			throws RestException;

}