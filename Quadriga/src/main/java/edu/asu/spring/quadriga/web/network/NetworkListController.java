package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
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

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * This class will handle list {@link INetwork} of the {@link IUser} and fetch the {@link INetwork} details from DB and QStore.
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListController {

	@Autowired
	INetworkManager networkManager;

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("jaxbMarshaller")
	Jaxb2Marshaller jaxbMarshaller;

	@Autowired
	IListWSManager wsManager;

	@Autowired
	@Qualifier("marshallingConverter")
	MarshallingHttpMessageConverter marshallingConverter;

	@Autowired
	IRetrieveProjectManager projectManager;

	@Autowired
	IUserManager userManager;

	@Autowired
	IEditingNetworkAnnotationManager editingNetworkAnnotationManager;

	@Autowired
	@Qualifier("qStoreURL")
	private String qStoreURL;

	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;

	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkListController.class);

	/*
	 * Prepare the QStore GET URL
	 */
	public String getQStoreGetURL() {
		return qStoreURL+""+qStoreURL_Get;
	}

	/**
	 * This method helps in listing of network belonging to the user in tree view. 
	 * @author Lohith Dwaraka
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@RequestMapping(value = "auth/networks", method = RequestMethod.GET)
	public String listNetworksTreeView(ModelMap model, Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUserDetails(principal.getName());
		String jsTreeData = null;
		jsTreeData = networkManager.getNetworkJSTreeJson(user.getUserName());
		model.addAttribute("tableview",0);
		model.addAttribute("core", jsTreeData);
		model.addAttribute("userId", user.getUserName());

		return "auth/networks";
	}

	/**
	 * Get the list of network belonging to the user
	 * @author Lohith Dwaraka
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@RequestMapping(value = "auth/networks/table", method = RequestMethod.GET)
	public String listNetworksTableView(ModelMap model, Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUserDetails(principal.getName());
		List<INetwork> networkList = null;
		try{

			networkList=networkManager.getNetworkList(user);

			if(networkList!= null){
				for(INetwork network : networkList){

					if(network.getNetworkWorkspace() != null){
						logger.info("network workspace is not null");
						IWorkSpace workspace=  network.getNetworkWorkspace().getWorkspace();
						if(workspace != null){
							logger.info("workspace is not null");
							logger.info(workspace.getWorkspaceName());
						}
					}

				}
			}

		}catch(QuadrigaStorageException e){
			logger.error("Something wrong on DB Side",e);
		}
		model.addAttribute("core", "{\"core\": {\"data\": []}}");
		model.addAttribute("tableview",1);
		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());

		return "auth/networks";
	}


	/**
	 * Get the network displayed on to JSP by passing JSON string
	 * @author Lohith Dwaraka
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "auth/networks/visualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		INetworkJSon networkJSon= networkManager.getJsonForNetworks(networkId, INetworkManager.D3JQUERY);
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(networkJSon!=null){
			json = networkJSon.getJson();
		}
		model.addAttribute("jsonstring",json);

		return "auth/networks/visualize";
	}




	/**
	 * Get the network displayed on to JSP by passing JSON string and allow to add annotations 
	 * @author Lohith Dwaraka
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 * @throws JSONException 
	 * @throws QStoreStorageException 
	 */
	@RequestMapping(value = "auth/editing/editnetworks/{networkId}", method = RequestMethod.GET)
	public String visualizeAndEditNetworksByD3(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException, JSONException, QStoreStorageException {
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		INetworkJSon networkJSon= networkManager.getJsonForNetworks(networkId, INetworkManager.D3JQUERY);

		logger.info("Source reference ID " + networkManager.getSourceReferenceURL(networkId, networkManager.getLatestVersionOfNetwork(networkId)));
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		List<ID3Node> nodeList = null;
		if(networkJSon!=null){
			json = networkJSon.getJson();
			nodeList =networkJSon.getNodeList();
		}
		model.addAttribute("jsonstring",json);
		model.addAttribute("nodeList",nodeList);
		logger.info("json : "+json);
		return "auth/editing/editnetworks";
	}

	/**
	 * Method for ajax call to generate the JSON for JStree to show the networks under project and workspace hiearchy  
	 * @param model							Use to fetch the {@link ModelMap} object
	 * @param principal						Used to fetch {@link IUser} details
	 * @param res							Set the status of response			
	 * @return								Returns the JStree JSON to the client
	 * @throws QuadrigaStorageException
	 * @throws JSONException
	 */
	@RequestMapping(value = "auth/networks/jstree", method = RequestMethod.GET)
	public @ResponseBody String listNetworksJson(ModelMap model, Principal principal, HttpServletResponse res) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUserDetails(principal.getName());
		String jsTreeData = null;
		jsTreeData = networkManager.getNetworkJSTreeJson(user.getUserName());
		logger.info("JSon : "+ jsTreeData);
		res.setStatus(200);
		return jsTreeData;
	}

	
	
}
