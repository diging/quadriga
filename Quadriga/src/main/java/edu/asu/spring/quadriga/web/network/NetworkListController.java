package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

/**
 * This class will handle list {@link INetwork} of the {@link IUser} and fetch the {@link INetwork} details from DB and QStore.
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListController {

	@Autowired
	private INetworkManager networkManager;
	
	@Autowired
    private INetworkTransformationManager transformationManager;
    
    @Autowired
    private ID3Creator d3Creator;

	@Autowired
	private IUserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkListController.class);

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
    public String listNetworks(ModelMap model, Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUser(principal.getName());
		List<INetwork> networkList=networkManager.getNetworkList(user);
		model.addAttribute("userId", user.getUserName());
		model.addAttribute("networkList", networkList);
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
		ITransformedNetwork transformedNetwork= transformationManager.getTransformedNetwork(networkId);
		
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(transformedNetwork!=null){
			json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
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
		ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId);

		logger.info("Source reference ID " + networkManager.getSourceReferenceURL(networkId, networkManager.getLatestVersionOfNetwork(networkId)));
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(transformedNetwork!=null){
		    json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
		}
		model.addAttribute("jsonstring",json);
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
		IUser user = userManager.getUser(principal.getName());
		String jsTreeData = null;
		jsTreeData = networkManager.getNetworkJSTreeJson(user.getUserName());
		logger.info("JSon : "+ jsTreeData);
		res.setStatus(200);
		return jsTreeData;
	}

	
	
}
