package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
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
import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

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
	private IEditingNetworkAnnotationManager editingNetworkAnnoManager;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IEmailNotificationManager emailManager;


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


		if(workspaceid == null||workspaceid.isEmpty()){
			response.setStatus(404);
			return "Please provide correct workspace id.";
		}
		String projectid = networkManager.getProjectIdForWorkspaceId(workspaceid);
		if(projectid == null || projectid.isEmpty()){
			response.setStatus(404);
			return "Please provide correct workspace id.";
		}

		if(networkName == null ||  networkName.isEmpty()){
			response.setStatus(404);
			return "Please provide network name as a part of post parameters";
		}


		xml=xml.trim();
		if (xml.isEmpty()) {
			response.setStatus(406);
			return "Please provide XML in body of the post request.";

		} else {
			String res=networkManager.storeXMLQStore(xml);
			if(res.equals("")){
				response.setStatus(406);
				return "Please provide correct XML in body of the post request. Qstore system is not accepting ur XML";
			}
			JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			InputStream is = new ByteArrayInputStream(res.getBytes());
			JAXBElement<ElementEventsType> response1 =  unmarshaller.unmarshal(new StreamSource(is), ElementEventsType.class);
			networkId=networkManager.receiveNetworkSubmitRequest(response1,user,networkName,workspaceid,"NEW",networkId);

			if(networkId.isEmpty()){
				response.setStatus(404);
				return "Text files don't belong to this workspace.";
			}

			//			Below code would help in printing XML from qstore
			Marshaller marshaller = context.createMarshaller();
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			marshaller.marshal(response1, os);

			//			String s = os.toString();
			//			String r=networkManager.prettyFormat(s,2);
			//			logger.info("checking this "+r);


			//TODO: Send email to all editors of a workspace
			//TODO: Get the workspace editors from the workspaceid


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
	@RequestMapping(value = "rest/networkstatus/{NetworkId}", method = RequestMethod.GET, produces = "application/xml")
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
			if(status == null)
				context.put("status", "UNKNOWN");
			else
				context.put("status", status);
			context.put("networkid",networkId);
			List<NetworkAnnotation>  networkAnnoList= editingNetworkAnnoManager.getAllAnnotationOfNetwork(user.getUserName(), networkId);
			context.put("networkAnnoList",networkAnnoList);
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
	 *  Rest interface for getting all the network list belonging to a workspace 
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
	 *  Rest interface for getting rejected network list belonging to a workspace 
	 * http://<<URL>:<PORT>>/quadriga/rest/workspace/{workspaceid}/rejectednetworks
	 * http://localhost:8080/quadriga/rest/workspace/WS_23092339551502339/rejectednetworks
	 * @param networkName
	 * @param response
	 * @param accept
	 * @param principal
	 * @return status
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/workspace/{workspaceid}/rejectednetworks", method = RequestMethod.GET, produces = "application/xml")
	public String getWorkspaceRejectedNetworkList(@PathVariable("workspaceid") String workspaceId,
			HttpServletResponse response,
			String accept,Principal principal,HttpServletRequest req) throws Exception {


		List<INetwork> networkList = wsManager.getWorkspaceRejectedNetworkList(workspaceId);
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

		String networkName = request.getParameter("networkname");

		
		if(networkId == null||networkId.isEmpty()){
			response.setStatus(404);
			return "Please provide network id.";
		}
		
		INetwork network = null;
		try{
			network = networkManager.getNetworkStatus(networkId,user);
			if(network==null){
				logger.info("network is null");
				response.setStatus(404);
				return "Please provide correct network id.";
			}else{
				logger.info(network.getName());
			}
		}catch(QuadrigaStorageException e){
			logger.error("DB Error :",e);
		}
//		logger.info("Old name : "+network.getName()+"  new name : "+networkName);
		if(!(network.getStatus().equals(INetworkStatus.REJECTED))){
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
				String networkNameUpdateStatus="";
				if(!(networkName == null ||networkName.equals(network.getName()) || networkName.equals(""))){
					networkNameUpdateStatus = networkManager.updateNetworkName(networkId,networkName);
					if(!(networkNameUpdateStatus.equals("success"))){
						response.setStatus(500);
						return "DB Issue, please try after sometime";
					}
				}
				
				networkManager.archiveNetwork(networkId);
				editorManager.updateNetworkStatus(networkId, INetworkStatus.PENDING);
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
