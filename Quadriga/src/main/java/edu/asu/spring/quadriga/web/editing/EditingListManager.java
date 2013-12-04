package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class EditingListManager {

	@Autowired
	INetworkManager networkManager;

	@Autowired
	IEditorManager editorManager;

	@Autowired
	IUserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(EditingListManager.class);

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("jaxbMarshaller")
	Jaxb2Marshaller jaxbMarshaller;

	@Autowired
	@Qualifier("marshallingConverter")
	MarshallingHttpMessageConverter marshallingConverter;

	@Autowired
	@Qualifier("qStoreURL")
	private String qStoreURL;

	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;

	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;


	@Autowired
	private IConceptCollectionManager conceptCollectionManager;
	/*
	 * Prepare the QStore GET URL
	 */
	public String getQStoreGetURL() {
		return qStoreURL+""+qStoreURL_Get;
	}

	/**
	 * List of networks available to editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 0, userRole = {RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR})
	,@ElementAccessPolicy(type=CheckedElementType.NETWORK,paramIndex=0,userRole={})})
	@RequestMapping(value = "auth/editing", method = RequestMethod.GET)
	public String listNetworkAvailableToEditors(ModelMap model, Principal principal) throws QuadrigaStorageException
	,QuadrigaAccessException{
		IUser user = userManager.getUserDetails(principal.getName());

		List<INetwork> assignedNetworkList=null;
		try{
			assignedNetworkList = editorManager.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		List<INetwork> networkList=null;
		try{
			networkList = editorManager.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("assignedNetworkList", assignedNetworkList);
		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}

	/**
	 * List of networks assigned to other editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 0, userRole = {RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN,RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.NETWORK,paramIndex=0,userRole={})})
	@RequestMapping(value = "auth/networksOtherEditors", method = RequestMethod.GET)
	public String listNetworkAssignedToOtherEditors(ModelMap model, Principal principal) throws QuadrigaStorageException
	,QuadrigaAccessException
	{
		IUser user = userManager.getUserDetails(principal.getName());

		List<INetwork> assignedNetworkList=null;
		try{
			assignedNetworkList = editorManager.getAssignedNetworkListOfOtherEditors(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("assignedNetworkList", assignedNetworkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}

	/**
	 * List of networks finished by other editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 0, userRole = {RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN,RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.NETWORK,paramIndex=0,userRole={})})
	@RequestMapping(value = "auth/finishednetworksOtherEditors", method = RequestMethod.GET)
	public String listFinishedNetworksByOtherEditors(ModelMap model, Principal principal) throws QuadrigaStorageException,QuadrigaAccessException {
		IUser user = userManager.getUserDetails(principal.getName());

		List<INetwork> finishedNetworkList=null;
		try{
			finishedNetworkList = editorManager.getfinishedNetworkListOfOtherEditors(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("finishedNetworkList", finishedNetworkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}


	/**
	 * Get the network displayed on to JSP by passing JSON string on editing page
	 * @author Lohith Dwaraka
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 0, userRole = {RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN,RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.NETWORK,paramIndex=1,userRole={})})
	@RequestMapping(value = "auth/editing/visualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		StringBuffer jsonstring=new StringBuffer();
		logger.debug("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.debug("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		jsonstring.append("[");
		List<List<Object>>relationEventPredicateMapping = new ArrayList<List<Object>>();
		networkManager.setRelationEventPredicateMapping(relationEventPredicateMapping);
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.debug("Node id "+networkNodeInfo.getId());
			logger.debug("Node statement type "+networkNodeInfo.getStatementType());
			jsonstring.append(networkManager.generateJsontoJQuery(networkNodeInfo.getId(), networkNodeInfo.getStatementType()));
		}
		String jsonstring1 = jsonstring.toString();
		if(jsonstring1.charAt(jsonstring1.length()-1) == ','){
			jsonstring1 = jsonstring1.substring(0, jsonstring1.length()-1);
		}
		jsonstring1 = jsonstring1+"]";
		logger.debug(jsonstring1);
		logger.info("Json object formed and sent to the JSP");
		//model.addAttribute("json", "[{\"adjacencies\": [\"1\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [\"2\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [{\"nodeTo\": \"4\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}},{\"nodeTo\": \"3\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}}],\"data\": {\"$color\": \"#EBB056\",\"$type\": \"circle\",\"$dim\": 11},\"id\": \"5\",\"name\": \"5\"}, {\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"4\",\"name\": \"4\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"1\",\"name\": \"1\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"2\",\"name\": \"2\"}]");
		model.addAttribute("jsonstring",jsonstring1);
		return "auth/editing/visualize";
	}



	/**
	 * Visualize old version of network
	 * Get the network displayed on to JSP by passing JSON string on editing page
	 * @author Lohith Dwaraka
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "/auth/editing/oldversionvisualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworksOldVersion(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		StringBuffer jsonstring=new StringBuffer();
		logger.debug("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.debug("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkOldVersionTopNodes(networkId);
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		jsonstring.append("[");
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.debug("Node id "+networkNodeInfo.getId());
			logger.debug("Node statement type "+networkNodeInfo.getStatementType());
			jsonstring.append(networkManager.generateJsontoJQuery(networkNodeInfo.getId(), networkNodeInfo.getStatementType()));
		}
		String jsonstring1 = jsonstring.toString();
		if(jsonstring1.charAt(jsonstring1.length()-1) == ','){
			jsonstring1 = jsonstring1.substring(0, jsonstring1.length()-1);
		}
		jsonstring1 = jsonstring1+"]";
		logger.debug(jsonstring1);
		logger.info("Json object formed and sent to the JSP");
		//model.addAttribute("json", "[{\"adjacencies\": [\"1\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [\"2\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [{\"nodeTo\": \"4\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}},{\"nodeTo\": \"3\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}}],\"data\": {\"$color\": \"#EBB056\",\"$type\": \"circle\",\"$dim\": 11},\"id\": \"5\",\"name\": \"5\"}, {\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"4\",\"name\": \"4\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"1\",\"name\": \"1\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"2\",\"name\": \"2\"}]");
		model.addAttribute("jsonstring",jsonstring1);
		return "auth/editing/visualize";
	}

	@RequestMapping(value = "/rest/editing/getconcept/{lemma}", method = RequestMethod.GET)
	@ResponseBody
	public String getConceptCollectionObject(@PathVariable("lemma") String lemma,HttpServletRequest request, HttpServletResponse response, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {

		ConceptpowerReply conceptPowerReply = conceptCollectionManager.search(lemma, "NOUN");
		List <ConceptEntry> conceptList = conceptPowerReply.getConceptEntry();
		Iterator <ConceptEntry> conceptListIterator = conceptList.iterator();
		while(conceptListIterator.hasNext()){

			ConceptEntry ce = conceptListIterator.next();
			if(ce.getLemma().equalsIgnoreCase(lemma)){
				response.setStatus(200);
				return ce.getDescription();
			}
		}
		conceptPowerReply = conceptCollectionManager.search(lemma, "VERB");
		conceptList = conceptPowerReply.getConceptEntry();
		conceptListIterator = conceptList.iterator();
		while(conceptListIterator.hasNext()){

			ConceptEntry ce = conceptListIterator.next();
			if(ce.getLemma().equalsIgnoreCase(lemma)){
				response.setStatus(200);
				return ce.getDescription();
			}
		}
		
		conceptPowerReply = conceptCollectionManager.search(lemma, "adverb");
		conceptList = conceptPowerReply.getConceptEntry();
		conceptListIterator = conceptList.iterator();
		while(conceptListIterator.hasNext()){

			ConceptEntry ce = conceptListIterator.next();
			if(ce.getLemma().equalsIgnoreCase(lemma)){
				response.setStatus(200);
				return ce.getDescription();
			}
		}
		
		conceptPowerReply = conceptCollectionManager.search(lemma, "adjective");
		conceptList = conceptPowerReply.getConceptEntry();
		conceptListIterator = conceptList.iterator();
		while(conceptListIterator.hasNext()){

			ConceptEntry ce = conceptListIterator.next();
			if(ce.getLemma().equalsIgnoreCase(lemma)){
				response.setStatus(200);
				return ce.getDescription();
			}
		}
		
		conceptPowerReply = conceptCollectionManager.search(lemma, "others");
		conceptList = conceptPowerReply.getConceptEntry();
		conceptListIterator = conceptList.iterator();
		while(conceptListIterator.hasNext()){

			ConceptEntry ce = conceptListIterator.next();
			if(ce.getLemma().equalsIgnoreCase(lemma)){
				response.setStatus(200);
				return ce.getDescription();
			}
		}

		return "";
	}

}
