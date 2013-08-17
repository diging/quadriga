package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.rest.NetworkRestController;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListManager {
	
	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	IUserManager userManager;
	
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
			.getLogger(NetworkListManager.class);

	public String getQStoreGetURL() {
		return qStoreURL+""+qStoreURL_Get;
	}
	
	@RequestMapping(value = "auth/networks", method = RequestMethod.GET)
	public String listNetworks(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		List<INetwork> networkList=null;
		try{
		networkList=networkManager.getNetworkList(user);
		
		}catch(QuadrigaStorageException e){
			logger.error("Something wrong on DB Side",e);
		}
		
		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		
		return "auth/networks";
	}
	
	
	@RequestMapping(value = "auth/networks/visualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException {
		
		logger.info("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.info("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.info("Node id "+networkNodeInfo.getId());
			logger.info("Node statement type "+networkNodeInfo.getStatementType());
			
		}
		model.addAttribute("json", "[{\"adjacencies\": [\"1\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [\"2\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [{\"nodeTo\": \"4\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}},{\"nodeTo\": \"3\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}}],\"data\": {\"$color\": \"#EBB056\",\"$type\": \"circle\",\"$dim\": 11},\"id\": \"5\",\"name\": \"5\"}, {\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"4\",\"name\": \"4\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"1\",\"name\": \"1\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"2\",\"name\": \"2\"}]");
		return "auth/networks/visualize";
	}
	
}
