package edu.asu.spring.quadriga.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
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

	public void receiveNetworkSubmitRequest(JAXBElement<ElementEventsType> response,IUser user,String networkName){
		String networkId="";
		try{
			networkId=dbConnect.addNetworkRequest(networkName, user);
		}catch(QuadrigaStorageException e){
			logger.error("DB action error ",e);
		}
		try{
			ElementEventsType e = response.getValue();
			List<CreationEvent> c =e.getRelationEventOrAppellationEvent();
			Iterator <CreationEvent> I= c.iterator();
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
							dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "AE", "1", user);
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
							dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "RE", "1", user);
						}
					}
					RelationEventType re = (RelationEventType) (ce);
					getRelationEventElements(re,networkId,user);

				}
			}
		}catch(Exception e){
			logger.error("",e);
		}
	}

	public void getRelationEventElements(RelationEventType re,String networkId,IUser user) throws QuadrigaStorageException{
		List <?> ee = re.getRelationCreatorOrRelation();
		Iterator <?> Iee=ee.iterator();
		while(Iee.hasNext()){
			Object o = Iee.next();
			if(o instanceof RelationType){
				RelationType rt = (RelationType) o;
				List<JAXBElement<?>> e3 =rt.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> I2 = e3.iterator();
				while(I2.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) I2.next();

					if(element.getValue().toString().contains("SubjectObjectType")){
						//	Handles the subject part of the relation
						if(element.getName().toString().contains("subject")){
							SubjectObjectType  subject= (SubjectObjectType) element.getValue();
							
							//	Check for relation event inside subject and add if any
							RelationEventType re1 = subject.getRelationEvent();
							if(re1 == null){
								logger.debug("RE1 is null");
							}else{
								List<JAXBElement<?>> e2 = re1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Relation Event ID subject: "+element1.getValue().toString());
										dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
									}
								}
								getRelationEventElements(re1,networkId,user);
							}
							
							//	Check for Appellation event inside subject and add if any
							AppellationEventType ae1 = subject.getAppellationEvent();
							if(ae1 == null){
								logger.debug("AE1 is null");
							}else{
								logger.debug("AE1 found subject");
								List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Appellation Event ID : "+element1.getValue().toString());
										dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
									}
								}
							}
						}else 
							//	Handles the object part of the relation
							if(element.getName().toString().contains("object")){
								
								SubjectObjectType  object= (SubjectObjectType) element.getValue();
								
								//	Check for Relation event inside object and add if any
								RelationEventType re1 = object.getRelationEvent();
								if(re1 == null){
									logger.debug("RE1 is null");
								}else{
									List<JAXBElement<?>> e2 = re1.getIdOrCreatorOrCreationDate();
									Iterator <JAXBElement<?>> I1 = e2.iterator();
									while(I1.hasNext()){
										JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
										if(element1.getName().toString().contains("id")){
											logger.debug("Relation Event ID object: "+element1.getValue().toString());
											dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
										}
									}
									getRelationEventElements(re1,networkId,user);
								}
								//	Check for Appellation event inside object and add if any
								AppellationEventType ae1 = object.getAppellationEvent();
								if(ae1 == null){
									logger.debug("AE1 is null");
								}else{
									logger.debug("AE1 found object");
									List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
									Iterator <JAXBElement<?>> I1 = e2.iterator();
									while(I1.hasNext()){
										JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
										if(element1.getName().toString().contains("id")){
											logger.debug("Appellation Event ID : "+element1.getValue().toString());
											dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
										}
									}
								}
								
							}
					}else 
						//	Handles the predicate part of the relation
						
						if(element.getValue().toString().contains("PredicateType")){
							
							PredicateType  predicate= (PredicateType) element.getValue();
							
							//	Check for Appellation event inside predicate and add if any
							AppellationEventType ae1 = predicate.getAppellationEvent();
							if(ae1 == null){
								logger.debug("AE1 is null");
							}else{
								logger.debug("AE1 found object");
								List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Appellation Event ID : "+element1.getValue().toString());
										dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
									}
								}
							}
							
						}
				}
			}
		}
	}
}
