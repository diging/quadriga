package edu.asu.spring.quadriga.web.rest;

import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;


@Controller
public class DspaceRestController {


	private static final Logger logger = LoggerFactory
			.getLogger(DspaceRestController.class);

	@Autowired
	private IDBConnectionListWSManager dbConnect;
	
	@Autowired
	private IRestVelocityFactory restVelocityFactory;


	@RequestMapping(value = "rest/workspace/{workspaceId}/files", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listWorkspaceConceptCollections(@PathVariable("workspaceId") String workspaceId, ModelMap model, Principal principal, HttpServletRequest req) throws RestException
	{
		VelocityEngine engine = null;
		Template template = null;

		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();

			// Retrieve the list of bitstreams for this workspace
			List<IBitStream> bitstreams = dbConnect.getBitStreams(workspaceId, principal.getName());
			
			template = engine
					.getTemplate("velocitytemplates/dspacefiles.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", bitstreams);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (ParseErrorException e) {

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (MethodInvocationException e) {

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(403);
		}


	}

}
