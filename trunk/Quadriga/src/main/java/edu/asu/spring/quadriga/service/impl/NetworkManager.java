package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.domain.implementation.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.implementation.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationEventType;

/**
 * This class acts as a Network manager which handles the networks object
 * 
 * @author : Lohith Dwaraka
 */
@Service
public class NetworkManager {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkManager.class);
	
	@Autowired
	private INetworkFactory networkFactory;
	
	@Autowired
	@Qualifier("DBConnectionNetworkManagerBean")
	private IDBConnectionNetworkManager dbConnect;
	
	public void receiveNetworkSubmitRequest(JAXBElement<ElementEventsType> response,IUser user){
		try{
			ElementEventsType e = response.getValue();
			List<CreationEvent> c =e.getRelationEventOrAppellationEvent();
			Iterator <CreationEvent> I= c.iterator();
			INetwork network = networkFactory.createNetworkObject(); 
			Date date = new Date();
			logger.info("current time : "+date.toGMTString());
			network.setCreationTime(date);
			List <String> appellationIds = new ArrayList<String>();
			List <String> relationIds = new ArrayList<String>();
			while(I.hasNext()){
				CreationEvent ce = I.next();
				if(ce instanceof AppellationEventType)
				{
					List<JAXBElement<?>> e2 = ce.getIdOrCreatorOrCreationDate();
					Iterator <JAXBElement<?>> I1 = e2.iterator();
					while(I1.hasNext()){
						JAXBElement<?> element = (JAXBElement<?>) I1.next();
						if(element.getName().toString().contains("id")){
							logger.info("Appellation Event ID : "+element.getValue().toString());
							appellationIds.add(element.getValue().toString());
						}
					}
				}
				if(ce instanceof RelationEventType){
					List<JAXBElement<?>> e2 = ce.getIdOrCreatorOrCreationDate();
					Iterator <JAXBElement<?>> I1 = e2.iterator();
					while(I1.hasNext()){
						JAXBElement<?> element = (JAXBElement<?>) I1.next();
						if(element.getName().toString().contains("id")){
							logger.info("Relation Event ID : "+element.getValue().toString());
							relationIds.add(element.getValue().toString());
						}
					}
				}
			}
			network.setAppellationIds(appellationIds);
			network.setRelationIds(relationIds);
			network.setCreator(user);
			dbConnect.submitUserNetworkRequest(network);
		}catch(Exception e){
			logger.error("",e);
		}
	}
	
}
