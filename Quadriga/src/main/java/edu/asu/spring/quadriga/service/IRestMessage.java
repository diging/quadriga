package edu.asu.spring.quadriga.service;

import javax.servlet.http.HttpServletRequest;

import edu.asu.spring.quadriga.exceptions.RestException;

public interface IRestMessage {

	public abstract String getErrorMsg(String errorMsg)
			throws RestException;

	public abstract String getErrorMsg(String errorMsg, HttpServletRequest req)
			throws RestException;

	public abstract String getSuccessMsg(String successMessage, HttpServletRequest req)
			throws RestException;

}