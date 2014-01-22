package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;

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
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("jaxbMarshaller")
	Jaxb2Marshaller jaxbMarshaller;

	@Autowired
	@Qualifier("marshallingConverter")
	MarshallingHttpMessageConverter marshallingConverter;

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

	/*
	 * Prepare the QStore GET URL
	 */
	public String getQStoreGetURL() {
		return qStoreURL+""+qStoreURL_Get;
	}

	/**
	 * Get the list of network belonging to the user
	 * @author Lohith Dwaraka
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
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
		StringBuffer jsonstring=new StringBuffer();
		logger.debug("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.debug("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		jsonstring.append("[");
		networkManager.setIntialValueForD3JSon();
		List<List<Object>>relationEventPredicateMapping = new ArrayList<List<Object>>();
		networkManager.setRelationEventPredicateMapping(relationEventPredicateMapping);
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.debug("Node id "+networkNodeInfo.getId());
			logger.debug("Node statement type "+networkNodeInfo.getStatementType());
			jsonstring.append(networkManager.generateJsontoJQuery(networkNodeInfo.getId(), networkNodeInfo.getStatementType()));
		}
		logger.info(networkManager.getD3JSon());
		String jsonstring1 = jsonstring.toString();
		if(jsonstring1.charAt(jsonstring1.length()-1) == ','){
			jsonstring1 = jsonstring1.substring(0, jsonstring1.length()-1);
		}
		jsonstring1 = jsonstring1+"]";
		logger.info(jsonstring1);
	
		logger.info("Json object formed and sent to the JSP");

		String nwId = "\""+networkId+"\"";
		model.addAttribute("jsonstring",jsonstring1);
		model.addAttribute("networkid",nwId);
		logger.debug("json string:"+ jsonstring1);
		logger.info("network id:"+nwId);
		
		return "auth/networks/visualize";
	}
	
	/**
	 * Get the network displayed  on to JSP by passing JSON string and allow to add annotations 
	 * @author Sowjanya Ambati
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "auth/editing/editnetworks/{networkId}", method = RequestMethod.GET)
	public String visualizeAndEditNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
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
		
		String nwId = "\""+networkId+"\"";
		model.addAttribute("jsonstring",jsonstring1);
		model.addAttribute("networkid",nwId);
		logger.info("json string:"+jsonstring1);
		logger.info("network id:"+nwId);
		
		return "auth/editing/editnetworks";
	}
	
	/**
	 * Get the network displayed  on to JSP by passing JSON string and allow to add annotations 
	 * @author Sowjanya Ambati
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "auth/editing/editnetworksnew/{networkId}", method = RequestMethod.GET)
	public String visualizeAndEditNetworksNew(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		StringBuffer jsonstring=new StringBuffer();
		logger.debug("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.debug("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
		networkManager.setIntialValueForD3JSon();
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		List<List<Object>>relationEventPredicateMapping = new ArrayList<List<Object>>();
		networkManager.setRelationEventPredicateMapping(relationEventPredicateMapping);
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.debug("Node id "+networkNodeInfo.getId());
			logger.debug("Node statement type "+networkNodeInfo.getStatementType());
			networkManager.generateJsontoJQuery(networkNodeInfo.getId(), networkNodeInfo.getStatementType());
		}

		logger.info("--------------------");
		logger.info(networkManager.getD3JSon());
		
		logger.info("Json object formed and sent to the JSP");
		
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		
		return "auth/editing/editnetworksnew";
	}

	
	
}
