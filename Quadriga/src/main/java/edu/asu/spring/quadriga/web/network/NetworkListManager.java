package edu.asu.spring.quadriga.web.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
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
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		String jsonstring="";
		logger.info("Network id "+networkId);
		String qstoreGetURL = getQStoreGetURL();
		logger.info("Qstore Get URL : "+qstoreGetURL);
		List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
		Iterator <INetworkNodeInfo> I = networkTopNodesList.iterator();
		jsonstring = "[";
		while(I.hasNext()){
			INetworkNodeInfo networkNodeInfo = I.next();
			logger.info("Node id "+networkNodeInfo.getId());
			logger.info("Node statement type "+networkNodeInfo.getStatementType());
			jsonstring=jsonstring+networkManager.generateJsontoJQuery(networkNodeInfo.getId(), networkNodeInfo.getStatementType());
		}
		if(jsonstring.charAt(jsonstring.length()-1) == ','){
			jsonstring = jsonstring.substring(0, jsonstring.length()-1);
		}
		jsonstring = jsonstring+"]";
		logger.debug(jsonstring);
		
		//model.addAttribute("json", "[{\"adjacencies\": [\"1\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [\"2\",{\"nodeTo\": \"5\",\"nodeFrom\": \"3\",\"data\": {\"$color\": \"#557EAA\"}},],\"data\": {\"$color\": \"#83548B\",\"$type\": \"circle\",\"$dim\": 10},\"id\": \"3\",\"name\": \"3\"},{\"adjacencies\": [{\"nodeTo\": \"4\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}},{\"nodeTo\": \"3\",\"nodeFrom\": \"5\",\"data\": {\"$color\": \"#557EAA\"}}],\"data\": {\"$color\": \"#EBB056\",\"$type\": \"circle\",\"$dim\": 11},\"id\": \"5\",\"name\": \"5\"}, {\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"4\",\"name\": \"4\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"1\",\"name\": \"1\"},{\"adjacencies\": [],\"data\": {\"$color\": \"#83548B\",\"$type\": \"square\",\"$dim\": 11},\"id\": \"2\",\"name\": \"2\"}]");
		model.addAttribute("jsonstring",jsonstring);
		return "auth/networks/visualize";
	}

	
}
