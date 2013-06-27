package edu.asu.spring.quadriga.exceptions;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;

/**
 * This class handles exceptions thrown in Controller classes.
 * 
 * @author Julia Damerow
 *
 */
@ControllerAdvice
public class QuadrigaExceptionHandler {

	@Autowired
	private IRestVelocityFactory restVelocityFactory;
	private static final Logger logger = LoggerFactory.getLogger(QuadrigaExceptionHandler.class);
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
		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}
	
	@ExceptionHandler(QuadrigaStorageException.class)
	public ModelAndView handleSQLException(QuadrigaStorageException ex) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("auth/storageissue");
		logger.error(ex.getMessage(), ex);
		return modelAndView;
	}
	
	@RequestMapping(produces="application/xml")
	@ExceptionHandler(RestException.class)
	@ResponseBody
	public String handleRestException(RestException ex, HttpServletRequest req) {
		logger.error("Exception:", ex);
		VelocityEngine engine=null;
		Template template = null;
		StringWriter sw = new StringWriter();
		
		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			
			template = engine
					.getTemplate("velocitytemplates/resterror.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("status", "ERROR");
			context.put("message",ex.getMessage());
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			
		} catch (ParseErrorException e) {
			logger.error("Exception:", e);
			
		} catch (MethodInvocationException e) {
			logger.error("Exception:", e);
			
		}  
		catch (Exception e) {
			logger.error("Exception:", e);
			
		}
		return sw.toString();
	}
}
