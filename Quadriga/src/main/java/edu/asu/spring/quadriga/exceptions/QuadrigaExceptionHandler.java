package edu.asu.spring.quadriga.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class handles exceptions thrown in Controller classes.
 * 
 * @author Julia Damerow
 *
 */
@ControllerAdvice
public class QuadrigaExceptionHandler {

	/**
	 * For now this method handles all exceptions thrown in Controller classes. Eventually this method can be
	 * replaced by methods that handle individual exceptions.
	 * 
	 * @param ex The exception thrown in a controller.
	 * @return Information about the exception page.
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleNotImplementedEx(Exception ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("auth/notImplemented");
		modelAndView.addObject("ex_name", ex.getClass().getName());
		modelAndView.addObject("ex_message", ex.getMessage());
		
		return modelAndView;
	}
	
}
