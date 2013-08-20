package edu.asu.spring.quadriga.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.domain.implementation.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.implementation.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.implementation.networks.PredicateType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationType;
import edu.asu.spring.quadriga.domain.implementation.networks.SubjectObjectType;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;

/**
 * This class acts as a Network manager which handles the networks object
 * 
 * @author : Lohith Dwaraka
 */
@Service
public class EditorManager implements IEditorManager {

	private static final Logger logger = LoggerFactory
			.getLogger(EditorManager.class);

		
	@Autowired
	private INetworkFactory networkFactory;

	@Autowired
	@Qualifier("DBConnectionEditorManagerBean")
	private IDBConnectionEditorManager dbConnect;

	

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IEditorManager#getEditorNetworkList(edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();
		
		try{
			networkList=dbConnect.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}
	
	
}
