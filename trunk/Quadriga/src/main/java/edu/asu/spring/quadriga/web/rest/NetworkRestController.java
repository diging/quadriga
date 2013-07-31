package edu.asu.spring.quadriga.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.service.impl.DictionaryManager;

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
	@Qualifier("qStoreURL")
	private String qStoreURL;
	
	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;
	
	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;
	
	/**
	 * Gets the QStrore Add URL
	 * 
	 * @return String URL
	 */
	public String getQStoreAddURL() {
		return qStoreURL+""+qStoreURL_Add;
	}
	
	/**
	 * Rest interface for uploading XML for networks
	 * http://<<URL>:<PORT>>/quadriga/rest/uploadnetworks
	 * hhttp://localhost:8080/quadriga/rest/uploadnetworks
	 * 
	 * @author Lohith Dwaraka
	 * @param request
	 * @param response
	 * @param xml
	 * @param accept
	 * @return
	 * @throws QuadrigaException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/uploadnetworks", method = RequestMethod.POST)
	public String getXMLFromVogon(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			@RequestHeader("Accept") String accept) throws QuadrigaException, ParserConfigurationException, SAXException, IOException {

		if (xml.equals("")) {
			response.setStatus(500);
			return "Please provide XML in body of the post request.";

		} else {
			if (accept != null && accept.equals("application/xml")) {
			}
			String res=storeXMLQStore(xml);
			logger.info(" " + res);
			response.setStatus(200);
			response.setContentType(accept);
			return "";
		}
	}
	
	
	/**
	 * Stores XML from Vogon into Q-Store
	 * @author Lohith Dwaraka
	 * @param XML
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String storeXMLQStore(String XML) throws ParserConfigurationException, SAXException, IOException {
		String res="";
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		RestTemplate restTemplate = new RestTemplate();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		HttpEntity request = new HttpEntity(XML,headers);

		try{
			 res = restTemplate.postForObject(getQStoreAddURL(), request,String.class);
		}catch(Exception e){
			logger.error("",e);
		}
		return res;

	}
	
}
