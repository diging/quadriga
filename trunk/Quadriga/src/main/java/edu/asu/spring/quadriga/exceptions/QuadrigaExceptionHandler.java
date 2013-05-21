package edu.asu.spring.quadriga.exceptions;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class QuadrigaExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleNotImplementedEx(NotImplementedException ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("auth/notImplemented");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", ex.getMessage());
		
		return modelAndView;
	}
	
}
