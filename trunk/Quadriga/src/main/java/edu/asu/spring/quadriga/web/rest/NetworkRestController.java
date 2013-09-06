package edu.asu.spring.quadriga.web.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.impl.UserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

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
	private	IListWSManager wsManager;

	@Autowired
	private IEditorManager editorManager;
	
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

		if(workspaceid.isEmpty()||workspaceid == null){
			response.setStatus(500);
			return "Please provide correct workspace id.";
		}
		String projectid = networkManager.getProjectIdForWorkspaceId(workspaceid);
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
			networkId=networkManager.receiveNetworkSubmitRequest(response1,user,networkName,workspaceid,"NEW",networkId);
			
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
	 *  Rest interface for getting status of network 
	 * http://<<URL>:<PORT>>/quadriga/rest/networkstatus/{NetworkId}
	 * http://localhost:8080/quadriga/rest/networkstatus/NET_1huxp4w7p71o0
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
			response.setStatus(200);
			response.setContentType(accept);
			response.setContentType("application/xml");
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

	/**
	 *  Rest interface for getting network list belonging to a workspace 
	 * http://<<URL>:<PORT>>/quadriga/rest/workspace/{workspaceid}/networks
	 * http://localhost:8080/quadriga/rest/workspace/WS_23092339551502339/networks
	 * @param networkName
	 * @param response
	 * @param accept
	 * @param principal
	 * @return status
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/workspace/{workspaceid}/networks", method = RequestMethod.GET, produces = "application/xml")
	public String getWorkspaceNetworkList(@PathVariable("workspaceid") String workspaceId,
			HttpServletResponse response,
			String accept,Principal principal,HttpServletRequest req) throws Exception {


		List<INetwork> networkList = wsManager.getWorkspaceNetworkList(workspaceId);
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
		Template template = null;
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/networks.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("networkList", networkList);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			response.setStatus(200);
			response.setContentType(accept);
			response.setContentType("application/xml");
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



	/**
	 *  Rest interface for getting network list belonging to a workspace 
	 * http://<<URL>:<PORT>>/quadriga/rest/networkdetails/{networkid}
	 * http://localhost:8080/quadriga/rest/networkdetails/NET_1huxp4w7p71o0
	 * @param networkName
	 * @param response
	 * @param accept
	 * @param principal
	 * @return status
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/networkdetails/{networkid}", method = RequestMethod.GET, produces = "application/xml")
	public String getNetworkXmlFromQstore(@PathVariable("networkid") String networkId,
			HttpServletResponse response,
			String accept,Principal principal,HttpServletRequest req) throws Exception {

		List<INetworkNodeInfo> networkTopNodes = networkManager.getNetworkTopNodes(networkId);
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
		Template template = null;
		String networkXML="";
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/getnetworksfromqstore.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("statmentList", networkTopNodes);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			logger.debug("XML : "+ writer.toString());
			networkXML = networkManager.getWholeXMLQStore(writer.toString());
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
		

		if(networkXML.isEmpty() || networkXML == null){
			throw new RestException(404);
		}
		response.setStatus(200);
		response.setContentType(accept);
		response.setContentType("application/xml");
		return networkXML;
	}

	/**
	 *  Rest interface for re-upload a changed network ( which was rejected earlier )  
	 * http://<<URL>:<PORT>>/quadriga/rest/reuploadnetwork/{networkid}
	 * http://localhost:8080/quadriga/rest/reuploadnetwork/NET_1huxp4w7p71o0
	 * @param networkId
	 * @param response
	 * @param accept
	 * @param principal
	 * @param req
	 * @return
	 * @throws JAXBException
	 * @throws QuadrigaStorageException
	 * @throws RestException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/reuploadnetwork/{networkid}", method = RequestMethod.POST)
	public String reuploadNetwork(@PathVariable("networkid") String networkId,
			HttpServletResponse response,HttpServletRequest request,
			@RequestBody String xml,
			@RequestHeader("Accept") String accept,
			Principal principal) throws JAXBException, QuadrigaStorageException, RestException, ParserConfigurationException, SAXException, IOException {
		IUser user = userManager.getUserDetails(principal.getName());

		INetwork network = null;
		try{
			network = networkManager.getNetworkStatus(networkId,user);
		}catch(QuadrigaStorageException e){
			logger.error("DB Error :",e);
		}
		if(!(network.getStatus().equals("REJECTED"))){
			response.setStatus(500);
			return "The Network doesn't have status : REJECTED";
		}else{

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
				networkManager.archiveNetwork(networkId);
				editorManager.updateNetworkStatus(networkId, "PENDING");
				JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
				InputStream is = new ByteArrayInputStream(res.getBytes());
				JAXBElement<ElementEventsType> response1 =  unmarshaller.unmarshal(new StreamSource(is), ElementEventsType.class);
				networkId=networkManager.receiveNetworkSubmitRequest(response1,user,network.getName(),network.getWorkspaceid(),"UPDATE",networkId);
				
				response.setStatus(200);
				response.setContentType(accept);
				response.setHeader("networkid",networkId );
				return res;
			}
		}
	}
	
	
	
}
