package edu.asu.spring.quadriga.web.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * Controller for networks related rest api's exposed to other clients
 * 
 * @author LohithDwaraka
 * 
 */
@Controller
public class NetworkRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkRestController.class);

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	private UserManager userManager;


	/**
	 * Rest interface for uploading XML for networks
	 * http://<<URL>:<PORT>>/quadriga/rest/uploadnetworks
	 * http://localhost:8080/quadriga/rest/uploadnetworks
	 * 
	 * @author Lohith Dwaraka
	 * @param request
	 * @param response
	 * @param xml
	 * @param accept
	 * @return XML
	 * @throws QuadrigaException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws JAXBException 
	 * @throws TransformerException 
	 * @throws QuadrigaStorageException 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/uploadnetworks", method = RequestMethod.POST)
	public String getNetworkFromClients(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			@RequestHeader("Accept") String accept,Principal principal) throws QuadrigaException, ParserConfigurationException, SAXException, IOException, JAXBException, TransformerException, QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		String networkName = request.getParameter("networkname");
		String networkId="";
		String workspaceid = request.getParameter("workspaceid");
		logger.info(" Network Name : "+ networkName);
		logger.info(" Workspace id : "+ workspaceid);
		String projectid = networkManager.getProjectIdForWorkspaceId(workspaceid);
		if(workspaceid.isEmpty()||workspaceid == null){
			response.setStatus(500);
			return "Please provide correct workspace id.";
		}
		if(projectid.isEmpty()){
			response.setStatus(500);
			return "Please provide correct workspace id.";
		}

		if(networkName.isEmpty()||networkName == null){
			response.setStatus(500);
			return "Please provide network name as a part of post parameters";
		}else{

			boolean result=networkManager.hasNetworkName(networkName,user);
			logger.debug(" Network name status : "+result);
			if(result){
				response.setStatus(500);
				return "Network Name already Exist";
			}
		}

		xml=xml.trim();
		if (xml.isEmpty()) {
			response.setStatus(500);
			return "Please provide XML in body of the post request.";

		} else {
			String res=networkManager.storeXMLQStore(xml);
			if(res.equals("")){
				response.setStatus(500);
				return "Please provide correct XML in body of the post request. Qstore system is not accepting ur XML";
			}
			JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			InputStream is = new ByteArrayInputStream(res.getBytes());
			JAXBElement<ElementEventsType> response1 =  unmarshaller.unmarshal(new StreamSource(is), ElementEventsType.class);
			networkId=networkManager.receiveNetworkSubmitRequest(response1,user,networkName,workspaceid);

			//			Below code would help in printing XML from qstore
			Marshaller marshaller = context.createMarshaller();
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			marshaller.marshal(response1, os);

			//			String s = os.toString();
			//			String r=networkManager.prettyFormat(s,2);
			//			logger.info("checking this "+r);

			response.setStatus(200);
			response.setContentType(accept);
			response.setHeader("networkid",networkId );
			return res;
		}

	}

	/**
	 *  Rest interface for uploading XML for networks
	 * http://<<URL>:<PORT>>/quadriga/rest/networkstatus/{NetworkName}
	 * http://localhost:8080/quadriga/rest/networkstatus/firstNetwork
	 * @param networkName
	 * @param response
	 * @param accept
	 * @param principal
	 * @return status
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/networkstatus/{NetworkId}", method = RequestMethod.GET)
	public String getNetworkStatus(@PathVariable("NetworkId") String networkId,
			HttpServletResponse response,
			String accept,Principal principal,HttpServletRequest req) throws Exception {
		IUser user = userManager.getUserDetails(principal.getName());
		String status="UNKNOWN";
		INetwork network =networkManager.getNetworkStatus(networkId,user);
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
		Template template = null;
		status = network.getStatus();
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/networkstatus.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("status", status);
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
		}

	}

}
