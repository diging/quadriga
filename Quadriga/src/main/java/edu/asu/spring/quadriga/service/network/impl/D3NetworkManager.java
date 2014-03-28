package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.d3.domain.ID3Constant;
import edu.asu.spring.quadriga.d3.domain.ID3Link;
import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.d3.domain.factory.ID3LinkFactory;
import edu.asu.spring.quadriga.d3.domain.factory.ID3NodeFactory;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.ID3NetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.impl.NetworkJSon;
import edu.asu.spring.quadriga.service.network.factory.INodeObjectWithStatementFactory;

@Service
public class D3NetworkManager implements ID3NetworkManager {

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	private INodeObjectWithStatementFactory nodeObjectWithStatementFactory;

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired
	ID3LinkFactory d3LinkFactory;

	@Autowired
	ID3NodeFactory d3NodeFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(D3NetworkManager.class);


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public INetworkJSon parseNetworkForD3Jquery(List<INetworkNodeInfo> networkTopNodesList){
		D3Map d3Map = null;
		if(networkTopNodesList!=null){
			if( networkTopNodesList.size()>0 ){
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

				d3Map =new D3Map();
				d3Map = prepareD3Map(d3Map, nodeObjectWithStatementList, relationEventPredicateMapping);
			}else{
				return null;
			}
		}else{
			return null;
		}

		// D3Map to String
		String d3Json =getD3JSonString(d3Map);

		INetworkJSon networkJson = new NetworkJSon(d3Json, d3Map.getD3NodeList());

		return networkJson;

	}


	/**
	 * This method would prepare {@link D3Map} which which has a {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * We maintain a cache of it until we parse the complete network.  
	 * @param d3Map												{@link D3Map} object to cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param nodeObjectWithStatementList						List of {@link INodeObjectWithStatement}
	 * @param relationEventPredicateMapping						{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 */
	private D3Map prepareD3Map(D3Map d3Map, List<INodeObjectWithStatement> nodeObjectWithStatementList, List<List<Object>> relationEventPredicateMapping) {

		Iterator<INodeObjectWithStatement> nodeObjectWithStatementIterator =  nodeObjectWithStatementList.iterator();

		while(nodeObjectWithStatementIterator.hasNext()){
			INodeObjectWithStatement nodeObjectWithStatement =  nodeObjectWithStatementIterator.next();
			d3Map = prepareD3JSonPerNode(nodeObjectWithStatement,d3Map,relationEventPredicateMapping);
		}
		return d3Map;
	}

	/**
	 * This method would help in generating the D# JQuery JSon using the {@link D3Map} object.
	 * We would form the JSon using the list of nodes and links of the networks. 
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @return													Returns JSon for D3 JQuery in form of {@link String}	
	 */
	public String getD3JSonString(D3Map d3Map){
		StringBuffer d3JsonString= new StringBuffer("");

		d3JsonString.append("{\n\"nodes\":[");
		d3JsonString.append(addNodesToD3JSonString(d3Map));
		d3JsonString.append("\"]");

		d3JsonString.append("}\n],\n\"links\":[\n");
		d3JsonString.append(addLinksToD3JSonString(d3Map));
		d3JsonString.append("}\n]\n}");

		return d3JsonString.toString();
	}

	/**
	 * This method would help in getting JSon having links of a network. 
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @return													Returns JSon related to links in form of {@link String}
	 */
	private String addLinksToD3JSonString(D3Map d3Map) {

		List<ID3Node> d3NodeList = d3Map.getD3NodeList();
		List<ID3Link> d3LinkList = d3Map.getD3LinkList();
		StringBuffer d3JsonString = new StringBuffer("");

		if(d3NodeList.size() > 0) {
			for(int i =0;i<d3LinkList.size()-2;i++){
				ID3Link d3Link =d3LinkList.get(i);
				d3JsonString.append("{\"source\":");
				d3JsonString.append(d3Link.getSource());
				d3JsonString.append(",");
				d3JsonString.append("\"target\":");
				d3JsonString.append(d3Link.getTarget());
				d3JsonString.append("},\n");
			}
			ID3Link d3Link =d3LinkList.get(d3LinkList.size()-1);
			d3JsonString.append("{\"source\":");
			d3JsonString.append(d3Link.getSource());
			d3JsonString.append(",");
			d3JsonString.append("\"target\":");
			d3JsonString.append(d3Link.getTarget());
		}
		return d3JsonString.toString();
	}

	/**
	 * This method would help in getting JSon having Nodes of a network. 
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @return													Returns JSon related to nodes in form of {@link String}
	 */
	private String addNodesToD3JSonString(D3Map d3Map) {
		List<ID3Node> d3NodeList = d3Map.getD3NodeList();
		StringBuffer d3JsonString = new StringBuffer("");

		if(d3NodeList.size() > 0) {

			for(int i =0;i<d3NodeList.size()-1;i++){
				ID3Node d3Node =d3NodeList.get(i);
				d3JsonString.append("{\"name\":\"");
				d3JsonString.append(d3Node.getNodeName());
				d3JsonString.append("\",");
				d3JsonString.append("\"id\":\"");
				d3JsonString.append(d3Node.getNodeId());
				d3JsonString.append("\",");
				d3JsonString.append("\"group\":");
				d3JsonString.append(d3Node.getGroupId());
				d3JsonString.append(",");
				d3JsonString.append("\"statementid\":[\"");
				for (int j = 0; j < d3Node.getStatementIdList().size(); j++) {
					if(j == d3Node.getStatementIdList().size()-1){
						d3JsonString.append(d3Node.getStatementIdList().get(j));
					}else{
						d3JsonString.append(d3Node.getStatementIdList().get(j)+",");
					}
				}
				d3JsonString.append("\"]");
				d3JsonString.append("},\n");

			}
			ID3Node d3Node =d3NodeList.get(d3NodeList.size()-1);
			d3JsonString.append("{\"name\":\"");
			d3JsonString.append(d3Node.getNodeName());
			d3JsonString.append("\",");
			d3JsonString.append("\"id\":\"");
			d3JsonString.append(d3Node.getNodeId());
			d3JsonString.append("\",");
			d3JsonString.append("\"group\":");
			d3JsonString.append(d3Node.getGroupId());
			d3JsonString.append(",");
			d3JsonString.append("\"statementid\":[\"");
			for (int j = 0; j < d3Node.getStatementIdList().size(); j++) {
				if(j == d3Node.getStatementIdList().size()-1){
					d3JsonString.append(d3Node.getStatementIdList().get(j));
				}else{
					d3JsonString.append(d3Node.getStatementIdList().get(j)+",");
				}

			}
		}

		return d3JsonString.toString();
	}

	/**
	 * This method would prepare {@link D3Map} object for each node.
	 * Method to add nodes and links into List, which can be used to make a JSON object later.
	 * @param nodeObjectWithStatement							{@link INodeObjectWithStatement} object which holds {@link NodeObject}
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param relationEventPredicateMapping						{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.				
	 */
	public D3Map prepareD3JSonPerNode(INodeObjectWithStatement nodeObjectWithStatement,D3Map d3Map, List<List<Object>> relationEventPredicateMapping){


		NodeObject nodeObject = nodeObjectWithStatement.getNodeObject();
		String statementId = nodeObjectWithStatement.getStatementId();

		String predicateNameId = nodeObject.getPredicateId();
		String subjectNodeId=nodeObject.getSubjectId();
		String objectNodeId = nodeObject.getObjectId();

		String predicateName = nodeObject.getPredicate();
		String subjectName = nodeObject.getSubject();
		String objectName = nodeObject.getObject();

		// Check for reference to relation
		String temp=networkManager.getPredicateNameFromStackOfAE(nodeObject.getRelationEventId(),nodeObject.getPredicate(),relationEventPredicateMapping);

		if(!(temp.equals(""))){
			predicateNameId = temp;
		}
		predicateName = predicateName.substring(0, predicateName.lastIndexOf("_"));
		if(subjectName.lastIndexOf("_")!=-1){
			subjectName = subjectName.substring(0, subjectName.lastIndexOf("_"));   
			nodeObject.setSubject(subjectName);
		}
		if(objectName.lastIndexOf("_")!=-1){
			objectName = subjectName.substring(0, objectName.lastIndexOf("_"));    
			nodeObject.setObject(objectName);
		}

		// Adding Subject into node list
		d3Map = addSubjectToNodeList(d3Map, subjectNodeId, nodeObject,statementId);

		// Adding Object into node list
		d3Map = addObjectToNodeList(d3Map, objectNodeId, nodeObject,statementId);

		// Adding Object into node list
		d3Map = addPredicateToNodeList(d3Map, predicateNameId,predicateName, nodeObject,statementId);

		// Add Links for D3 JSon
		d3Map = addD3Links(d3Map, predicateNameId, subjectNodeId, objectNodeId);

		return d3Map;
	}

	/**
	 * This method would help in adding the links to D3Map for a particular relation.
	 * We would have predicate, Subject and Object to forms the links. Link would contain Predicate --> Subject and Predicate --> Object
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param predicateNameId									{@link PredicateObject} name or ID of type {@link String}
	 * @param subjectNodeId										{@link SubjectObject} name or ID of type {@link String}
	 * @param objectNodeId										{@link ObjectTypeObject} name or ID of type {@link String}
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.											
	 */
	private D3Map addD3Links(D3Map d3Map, String predicateNameId,
			String subjectNodeId, String objectNodeId) {

		List<ID3Link> d3LinkList = d3Map.getD3LinkList();
		Map<String,Integer> d3NodeIdMap = d3Map.getD3NodeIdMap();

		// Add Links for D3 JSon
		ID3Link d3LinkSubject = d3LinkFactory.createD3LinkObject();
		d3LinkSubject.setSource(d3NodeIdMap.get(predicateNameId));
		d3LinkSubject.setTarget(d3NodeIdMap.get(subjectNodeId));
		d3LinkList.add(d3LinkSubject);

		ID3Link d3LinkObject = d3LinkFactory.createD3LinkObject();
		d3LinkObject.setSource(d3NodeIdMap.get(predicateNameId));
		d3LinkObject.setTarget(d3NodeIdMap.get(objectNodeId));
		d3LinkList.add(d3LinkObject);

		d3Map.setD3NodeIdMap(d3NodeIdMap);
		d3Map.setD3LinkList(d3LinkList);		

		return d3Map;
	}


	/**
	 * This method would help in adding the node details of predicate to the {@link D3Map}.
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param predicateId										{@link PredicateObject} ID of type {@link String}		
	 * @param predicateName										{@link PredicateObject} name of type {@link String}
	 * @param nodeObject										{@link NodeObject} object which hold node details
	 * @param statementId										Statement ID of the relation to which this node belongs to
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.								
	 */
	private D3Map addPredicateToNodeList(D3Map d3Map, String predicateId, String predicateName,
			NodeObject nodeObject, String statementId) {

		Map<String,Integer> d3NodeIdMap = d3Map.getD3NodeIdMap();
		List<ID3Node> d3NodeList = d3Map.getD3NodeList();
		int nodeIndex = d3Map.getIndex();

		// Adding Predicate into node list
		if(!d3NodeIdMap.containsKey(predicateId)){
			ID3Node d3NodePredicate = d3NodeFactory.createD3NodeObject();
			List<String> stmtList = d3NodePredicate.getStatementIdList();
			stmtList.add(statementId);
			d3NodePredicate.setStatementIdList(stmtList);
			d3NodePredicate.setNodeName(predicateName);
			d3NodePredicate.setNodeId(predicateId);
			d3NodePredicate.setGroupId(ID3Constant.RELATION_EVENT_PREDICATE_TERM);
			d3NodeList.add(d3NodePredicate);
			d3NodeIdMap.put(predicateId,nodeIndex);
			nodeIndex++;
		}else{
			int index= d3NodeIdMap.get(predicateId);
			ID3Node d3NodePredicate = d3NodeList.get(index);
			List<String> stmtList = d3NodePredicate.getStatementIdList();
			boolean flag=false;
			for(String stmt: stmtList){
				if(stmt.equals(statementId)){
					flag=true;
				}
			}
			if(!flag){
				stmtList.add(statementId);
			}
		}

		d3Map.setD3NodeIdMap(d3NodeIdMap);
		d3Map.setD3NodeList(d3NodeList);
		d3Map.setIndex(nodeIndex);


		return d3Map;
	}

	/**
	 * This method would help in adding the node details of object to the {@link D3Map}.
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param objectNodeId										{@link ObjectTypeObject} ID of type {@link String}
	 * @param nodeObject										{@link NodeObject} object which hold node details
	 * @param statementID										Statement ID of the relation to which this node belongs to
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 */	
	private D3Map addObjectToNodeList(D3Map d3Map, String objectNodeId,
			NodeObject nodeObject, String statementID) {
		Map<String,Integer> d3NodeIdMap = d3Map.getD3NodeIdMap();
		List<ID3Node> d3NodeList = d3Map.getD3NodeList();
		int nodeIndex = d3Map.getIndex();

		// Adding Object into node list
		if(!d3NodeIdMap.containsKey(objectNodeId)){
			ID3Node d3NodeObject = d3NodeFactory.createD3NodeObject();
			//List<String> stmtList = d3NodeObject.getStatementIdList();
			//stmtList.add(stmtId);
			//d3NodeObject.setStatementIdList(stmtList);
			d3NodeObject.setNodeName(nodeObject.getObject());
			d3NodeObject.setNodeId(objectNodeId);
			d3NodeObject.setGroupId(ID3Constant.RELATION_EVENT_OBJECT_TERM);
			d3NodeList.add(d3NodeObject);
			d3NodeIdMap.put(objectNodeId,nodeIndex);
			nodeIndex++;
		}

		d3Map.setD3NodeIdMap(d3NodeIdMap);
		d3Map.setD3NodeList(d3NodeList);
		d3Map.setIndex(nodeIndex);


		return d3Map;
	}

	/**
	 * This method would help in adding the node details of subject to the {@link D3Map}.
	 * @param d3Map												{@link D3Map} object which has cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 * @param subjectNodeId										{@link SubjectObject} ID of type {@link String}
	 * @param nodeObject										{@link NodeObject} object which hold node details
	 * @param statementID										Statement ID of the relation to which this node belongs to
	 * @return													Returns the {@link D3Map} object which cache {@link List} of {@link ID3Node} and {@link List} of {@link ID3Link}.
	 */
	private D3Map addSubjectToNodeList(D3Map d3Map, String subjectNodeId,
			NodeObject nodeObject, String statementID) {

		Map<String,Integer> d3NodeIdMap = d3Map.getD3NodeIdMap();
		List<ID3Node> d3NodeList = d3Map.getD3NodeList();
		int nodeIndex = d3Map.getIndex();

		// Adding Subject into node list
		if(!d3NodeIdMap.containsKey(subjectNodeId)){
			ID3Node d3NodeSubject = d3NodeFactory.createD3NodeObject();
			//List<String> stmtList = d3NodeSubject.getStatementIdList();
			//stmtList.add(stmtId);
			//d3NodeSubject.setStatementIdList(stmtList);
			d3NodeSubject.setNodeName(nodeObject.getSubject());
			d3NodeSubject.setNodeId(subjectNodeId);
			d3NodeSubject.setGroupId(ID3Constant.RELATION_EVENT_SUBJECT_TERM);
			d3NodeList.add(d3NodeSubject);
			d3NodeIdMap.put(subjectNodeId,nodeIndex);
			nodeIndex++;
		}

		d3Map.setD3NodeIdMap(d3NodeIdMap);
		d3Map.setD3NodeList(d3NodeList);
		d3Map.setIndex(nodeIndex);


		return d3Map;
	}




	/**
	 * Inner class to store D3 related {@link ID3Node} and {@link ID3Link} connectivity
	 * @author Lohith Dwaraka
	 *
	 */
	class D3Map{

		private List<ID3Node> d3NodeList;

		private Map<String,Integer> d3NodeIdMap;

		private List<ID3Link> d3LinkList;

		int index;

		public D3Map() {
			this.d3NodeList = new ArrayList<ID3Node>();
			this.d3NodeIdMap = new HashMap<String, Integer>();
			this.d3LinkList  = new ArrayList<ID3Link>();
			this.index = 0; 
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public List<ID3Node> getD3NodeList() {
			return d3NodeList;
		}

		public void setD3NodeList(List<ID3Node> d3NodeList) {
			this.d3NodeList = d3NodeList;
		}

		public Map<String, Integer> getD3NodeIdMap() {
			return d3NodeIdMap;
		}

		public void setD3NodeIdMap(Map<String, Integer> d3NodeIdMap) {
			this.d3NodeIdMap = d3NodeIdMap;
		}

		public List<ID3Link> getD3LinkList() {
			return d3LinkList;
		}

		public void setD3LinkList(List<ID3Link> d3LinkList) {
			this.d3LinkList = d3LinkList;
		}
	}

}

