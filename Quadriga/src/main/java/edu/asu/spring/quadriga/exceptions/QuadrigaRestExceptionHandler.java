package edu.asu.spring.quadriga.exceptions;

import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Resource(name = "errorMessages")
	private Properties errorProperties;
	@RequestMapping(produces="application/xml")
	@ExceptionHandler(RestException.class)
	@ResponseBody
	public String handleRestException(RestException ex, HttpServletRequest req, HttpServletResponse res) {
        logger.error("Exception:", ex);
		VelocityEngine engine=null;
		Template template = null;
		StringWriter sw = new StringWriter();
		int errorcode= ex.getErrorcode();
		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			if(errorcode==0)
				errorcode=406;
			res.setStatus(errorcode);
			template = engine
					.getTemplate("velocitytemplates/resterror.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("status", "ERROR");
			context.put("ErrorCode",errorcode);
			context.put("message",errorProperties.getProperty("error_message_"+errorcode));
			context.put("exception",ex.getMessage());
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
