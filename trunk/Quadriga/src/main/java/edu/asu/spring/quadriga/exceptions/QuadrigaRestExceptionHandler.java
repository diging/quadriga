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

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;

@ControllerAdvice
public class QuadrigaRestExceptionHandler {
	@Autowired
	private IRestVelocityFactory restVelocityFactory;
	private static final Logger logger = LoggerFactory.getLogger(QuadrigaRestExceptionHandler.class);
	
	@RequestMapping(produces="application/xml")
	@ExceptionHandler(RestException.class)
	@ResponseBody
	public String handleRestException(RestException ex, HttpServletRequest req) {
		
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
