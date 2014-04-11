package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.IJITNetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

@Service
public class JITNetworkManager implements IJITNetworkManager {

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	IConceptCollectionManager conceptCollectionManager;


	private static final Logger logger = LoggerFactory
			.getLogger(JITNetworkManager.class);



	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.IJITNetworkManager#parseNetworkForJITJquery(java.util.List)
	 */
	@Override
	public String parseNetworkForJITJquery(List<INetworkNodeInfo> networkTopNodesList){
		Iterator <INetworkNodeInfo> topNodeIterator = networkTopNodesList.iterator();
		List<List<Object>> relationEventPredicateMapping = new ArrayList<List<Object>>();
		List<INodeObjectWithStatement> nodeObjectWithStatementList = new ArrayList<INodeObjectWithStatement>();
		while(topNodeIterator.hasNext()){
			INetworkNodeInfo networkNodeInfo = topNodeIterator.next();
			if(networkNodeInfo.getStatementType().equals(INetworkManager.RELATIONEVENT)){
				try{
					String statementId = networkNodeInfo.getId();
					nodeObjectWithStatementList = networkManager.parseEachStatement(networkNodeInfo.getId(), networkNodeInfo.getStatementType(),statementId,relationEventPredicateMapping,nodeObjectWithStatementList);
				}catch(QStoreStorageException e){
					logger.error("QStore retrieve error",e);
				}catch(JAXBException e){
					logger.error("Issue while parsing the JAXB object",e);
				}
			}
		}

		
		// JIT Jquery JSon generation to String
		String jitJson =getJITJSonString(nodeObjectWithStatementList,relationEventPredicateMapping);
		
		return jitJson;
		
	}
	
	private String getJITJSonString(
			List<INodeObjectWithStatement> nodeObjectWithStatementList,
			List<List<Object>> relationEventPredicateMapping) {
		StringBuffer jsonString = new StringBuffer("");
		Iterator<INodeObjectWithStatement> nodeObjectWithStatementIterator =  nodeObjectWithStatementList.iterator();
		
		jsonString.append("[");
		
		while(nodeObjectWithStatementIterator.hasNext()){
			INodeObjectWithStatement nodeObjectWithStatement =  nodeObjectWithStatementIterator.next();
			jsonString.append(prepareJsonStringForNodeRelation(nodeObjectWithStatement.getNodeObject(), relationEventPredicateMapping));
		}
		
		String actualJSonString = jsonString.toString();
		if(actualJSonString.charAt(actualJSonString.length()-1) == ','){
			actualJSonString = actualJSonString.substring(0, actualJSonString.length()-1);
		}
		actualJSonString = actualJSonString+"]";
		
		return actualJSonString;
	}

	public String prepareJsonStringForNodeRelation(NodeObject nodeObject, List<List<Object>> relationEventPredicateMapping){
		
		StringBuffer jsonString = new StringBuffer();
		
		String predicateNameId = nodeObject.getPredicate();
		// Check for reference to relation
		String temp=networkManager.getPredicateNameFromStackOfAE(nodeObject.getRelationEventId(), nodeObject.getPredicate(), relationEventPredicateMapping);
		String predicateName = predicateNameId.substring(0,predicateNameId.lastIndexOf('_'));
		if(!(temp.equals(""))){
			predicateNameId = temp;
		}

		// Forming the JSON object
		jsonString.append("{\"adjacencies\":[");
		jsonString.append("{");
		jsonString.append("\"nodeTo\": \""+nodeObject.getSubject()+"\",");
		jsonString.append("\"nodeFrom\": \""+predicateName+"\",\"dim\" : \"15\", ");
		jsonString.append("\"data\": {\"$color\": \"#FFFFFF\",\"$labelid\": \"arrow1\",\"$labeltext\": \"Subject\"}");
		jsonString.append("},");

		jsonString.append("{");
		jsonString.append("\"nodeTo\": \""+nodeObject.getObject()+"\",");
		jsonString.append("\"nodeFrom\": \""+predicateName+"\",");
		jsonString.append("\"data\": {\"$color\": \"#FFFFFF\",\"$labelid\": \"arrow1\",\"dim\":\"8\",\"$labeltext\": \"Object\"}");
		jsonString.append("}],");

		jsonString.append("\"data\": {");
		jsonString.append("\"$color\": \"#CC0066\",");
		jsonString.append("\"$type\": \"circle\",");
		jsonString.append("\"nodetype\": \"Predicate\",");
		jsonString.append("\"$dim\": 13");
		jsonString.append("},");
		jsonString.append("\"id\": \""+predicateNameId+"\",");

		jsonString.append("\"name\": \""+predicateName+"\"");
		jsonString.append("},");
		
		return jsonString.toString();
	}

	
}
