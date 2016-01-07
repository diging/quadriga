package edu.asu.spring.quadriga.service.impl;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;

@Service
public class RestMessage implements IRestMessage {
	
	@Autowired
	private IRestVelocityFactory restVelocityFactory;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IErrorMessageRest#getErrorMsg(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getErrorMsg(String errorMsg) throws RestException {
		VelocityEngine engine = null;
		Template template = null;
		StringWriter sw = new StringWriter();

		try {
			engine = restVelocityFactory.getVelocityRestEngine();
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/error.vm");
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
			context.put("errMsg", errorMsg);
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			throw new RestException(404, e);
		} catch (Exception e) {
			throw new RestException(400, e);
		}
	}
	
	@Override
	public String getErrorMsg(String errorMsg,HttpServletRequest req) throws RestException {
		VelocityEngine engine = null;
		Template template = null;
		StringWriter sw = new StringWriter();

		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/error.vm");
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
			context.put("errMsg", errorMsg);
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			throw new RestException(404, e);
		} catch (Exception e) {
			throw new RestException(400, e);
		}
	}
	
	@Override
	public String getSuccessMsg(String successMessage,HttpServletRequest req) throws RestException {
		VelocityEngine engine = null;
		Template template = null;
		StringWriter sw = new StringWriter();

		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/success.vm");
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
			context.put("successMsg", successMessage);
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			throw new RestException(404, e);
		} catch (Exception e) {
			throw new RestException(400, e);
		}
	}
}
