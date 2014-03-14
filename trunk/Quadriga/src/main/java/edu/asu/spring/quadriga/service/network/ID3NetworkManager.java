package edu.asu.spring.quadriga.service.network;

import java.util.List;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.PredicateType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.SubjectObjectType;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.AppellationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.RelationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

public interface ID3NetworkManager {

	public abstract INetworkJSon parseNetworkForD3Jquery(
			List<INetworkNodeInfo> networkTopNodesList);

	public abstract ElementEventsType getElementEventTypeFromRelationEvent(
			String relationEventId) throws JAXBException,
			QStoreStorageException;

	public abstract ElementEventsType unMarshalXmlToElementEventsType(String xml)
			throws JAXBException;

	public abstract RelationEventObject parseThroughRelationEvent(
			RelationEventType relationEventType,
			RelationEventObject relationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	public abstract String stackRelationEventPredicateAppellationObject(
			String relationEventId, String predicateName,
			AppellationEventObject appellationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	public abstract PredicateObject parseThroughPredicate(
			RelationEventType relationEventType, PredicateType predicateType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * Get Appellation associated to relation event
	 * @param relationEventId
	 * @return
	 */
	public abstract AppellationEventObject checkRelationEventInStack(
			String relationEventId,
			List<List<Object>> relationEventPredicateMapping);

	public abstract SubjectObject parseThroughSubject(
			RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	public abstract ObjectTypeObject parseThroughObject(
			RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * prepare Predicate Object content for 
	 * @param predicateObject
	 * @param nodeObject
	 * @return
	 */
	public abstract NodeObject getPredicateNodeObjectContent(
			PredicateObject predicateObject, NodeObject nodeObject);

	/**
	 * Check for repeats in the XML to avoid any repeated reference
	 * @param relationEventId
	 * @param predicateName
	 * @return
	 */

	public abstract String checkRelationEventRepeatation(
			String relationEventId, String predicateName,
			List<List<Object>> relationEventPredicateMapping);

	public abstract List<INodeObjectWithStatement> prepareNodeObjectContent(
			RelationEventObject relationEventObject,
			List<INodeObjectWithStatement> nodeObjectWithStatementList,
			String statementId);

	public abstract List<INodeObjectWithStatement> parseEachStatement(String relationEventId,
			String statementType, String statementId,
			List<List<Object>> relationEventPredicateMapping,
			List<INodeObjectWithStatement> nodeObjectWithStatementList)
			throws JAXBException, QStoreStorageException;

}