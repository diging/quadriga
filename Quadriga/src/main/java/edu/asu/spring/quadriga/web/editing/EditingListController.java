package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
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
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformer;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class EditingListController {

	@Autowired
	private INetworkManager networkManager;
	
	@Autowired
	private INetworkTransformationManager transformationManager;
	
	@Autowired
	private ID3Creator d3Creator;

	@Autowired
	private IEditorManager editorManager;

	@Autowired
	private IUserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(EditingListController.class);

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
		IUser user = userManager.getUser(principal.getName());

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
		IUser user = userManager.getUser(principal.getName());

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
		IUser user = userManager.getUser(principal.getName());

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
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId, INetworkManager.D3JQUERY);
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = "";
		if(transformedNetwork!=null){
			json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
		}
		model.addAttribute("jsonstring",json);

		return "auth/editing/visualize";
	}


	/**
	 * Visualize old version of network based on the version number
	 * Get the network displayed on to JSP by passing JSON string on editing page
	 * @author Lohith Dwaraka
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "/auth/editing/oldversionvisualize/{networkId}/{versionNo}", method = RequestMethod.GET)
	public String visualizeNetworksOldVersion(@PathVariable("networkId") String networkId, @PathVariable("versionNo") String versionNo, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId, INetworkManager.D3JQUERY,versionNo);
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(transformedNetwork!=null){
		    json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
		}
		model.addAttribute("jsonstring",json);
		return "auth/editing/visualize";
	}

	
	/**
	 * List all the versions of a particular network 
	 * Displays a jsp that contains a table with all the information about the different versions of the network
	 * @author Sayalee Mehendale
	 * @param networkId 						id of the particular network
	 * @param model								Model object to map values to view
	 * @param principal							current session user
	 * @return									returns a string to access the page that displays the network history
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	@RequestMapping(value = "auth/editing/versionhistory/{networkId}", method = RequestMethod.GET)
	public String viewHistory(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException {
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		List<INetwork> networkVersions = networkManager.getAllNetworkVersions(networkId);
		
		if(networkVersions!=null && !networkVersions.isEmpty()){
			model.addAttribute("Versions", networkVersions);
			return "auth/editing/history";
		}
		
		return null;
	}
	
	
	/**
	 *  This controller method would get description of the lemma to javascript when called through a Ajax call
	 * @author Lohith Dwaraka
	 * @param lemma
	 * @param request
	 * @param response
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "/sites/network/getconcept/{lemma}", method = RequestMethod.GET)
	@ResponseBody
	public String getConceptCollectionObject(@PathVariable("lemma") String lemma,HttpServletRequest request, HttpServletResponse response, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		
		// This is done as string with a dot (.) in between in the path variable 
		// is not read as expected so we could replace it by $ in the javascript
		// and revert back in our controller
		lemma = lemma.replace('$', '.');
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
