package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.impl.NetworkDownloadService;

/**
 * This class matches multiple original file and
 * the mapping file to find matching graph amongst them.
 *         
 * @author Prajakta Sudhir Samant, Julia Damerow
 * 
 */
@Service
public class MatchGraphs {

    @Autowired
	private PatternFinder finder;
    
    @Autowired
    private IConceptpowerConnector cpConnector;
    
    @Autowired
    private INetworkManager networkManager;
    
    @Autowired
    private NetworkDownloadService networkDownloadService;

	
	/**
	 * This method takes the pair of original and mapping file and finds the
	 * matching graph between them.
	 * 
	 * @param fileMappings
	 * @return turtle which is the matched graph result
	 */
	public List<List<TransformNode>> matchGraphs(List<ITransformation> fileMappings,
			List<String> networkIds) {
	    
	    List<CreationEvent> events = new ArrayList<CreationEvent>();
	    for (String networkId : networkIds) {
	        List<INetworkNodeInfo> networkTopNodesList = null;

	        try {
	            networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
	        } catch (QuadrigaStorageException e) {
	            return null;
	        }
	        
	        List<ElementEventsType> elementEventsTypeList = networkDownloadService
	                .getElementEventTypes(networkTopNodesList);
	        
	        for (ElementEventsType event : elementEventsTypeList) {
	           events.addAll(event.getRelationEventOrAppellationEvent());
	        }

	    }
	    
	    return runTransformations(events, fileMappings);
		
	}
	
	private List<List<TransformNode>> runTransformations(List<CreationEvent> events, List<ITransformation> fileMappings) {
	    List<List<TransformNode>>nodesList = new ArrayList<List<TransformNode>>();
	    
	    Set<CreationEvent> allEvents = new HashSet<CreationEvent>();
	    for (CreationEvent event : events) {
	        addEvents(event, allEvents);
	    }

        
        if (fileMappings != null && allEvents != null) {
            for (ITransformation m : fileMappings) {
                GraphMapper mapper = new GraphMapper();
                EventGraphMapper eventMapper = new EventGraphMapper(cpConnector);

                List<TransformNode> nodes = new ArrayList<TransformNode>();
                
                String patternFilePath = m.getPatternFilePath();
                mapper.createGraph(patternFilePath);
                
                eventMapper.buildGraphs(allEvents);

                List<Node> foundNodes = finder.findPattern(eventMapper.getStartNodes(),
                        mapper.getStartNode());
                
                // Path that will be retrieved from db using DTO which is uploaded in quad-138
                String mappingFilePath = m.getTransformationFilePath();
                mapper.createMapping(mappingFilePath);

                Transformer transformer = new Transformer(cpConnector);
                nodes = transformer.transform(foundNodes,
                        mapper.getStartNodeOfMapping());
                
                if (nodes != null && !nodes.isEmpty())
                    nodesList.add(nodes);

            }

            
            /*
             * Since we have our nodes ready, whichever fields are null just set
             * them as empty strings so that it shows empty in the output
             */
            for (int i = 0; i < nodesList.size(); i++) {
                List<TransformNode> nodes = nodesList.get(i);
                for (int j = 0; j < nodes.size(); j++) {
                    TransformNode node = nodes.get(j);

                    if (node.getConcept() == null) {
                        node.setConcept("");
                    }

                    if (node.getCorrespondingId() == null) {
                        node.setCorrespondingId("");
                    }

                    if (node.getType() == null) {
                        node.setType("");
                    }

                    List<Term> terms = node.getTerms();
                    if (terms != null) {
                        for (int k = 0; k < terms.size(); k++) {
                            Term term = terms.get(k);
                            if (term.getSourceUri() == null) {
                                term.setSourceUri("");
                            }

                            List<TermPart> termparts = term.getTermParts();
                            if (termparts != null) {
                                for (int l = 0; l < termparts.size(); l++) {
                                    TermPart termpart = termparts.get(l);
                                    if (termpart.getExpression() == null) {
                                        termpart.setExpression("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return nodesList;
	}

	
	private void addEvents(CreationEvent eventToAdd, Set<CreationEvent> events) {
	    // if the given event has already been added, then return here
	    if (!events.add(eventToAdd)) {
	        return;
	    }
	    if (eventToAdd instanceof RelationEventType) {
	        // add subject
	        CreationEvent subject = ((RelationEventType) eventToAdd).getRelation().getSubjectType().getAppellationEvent();
	        if (subject == null) {
	            subject = ((RelationEventType) eventToAdd).getRelation().getSubjectType().getRelationEvent();
	        }
	        addEvents(subject, events);      
	        
	        // add predicate
	        addEvents(((RelationEventType) eventToAdd).getRelation().getPredicateType().getAppellationEvent(), events);
	        
	        // add object
	        CreationEvent object = ((RelationEventType) eventToAdd).getRelation().getObjectType().getAppellationEvent();
            if (object == null) {
                object = ((RelationEventType) eventToAdd).getRelation().getObjectType().getRelationEvent();
            }
            addEvents(object, events);  
	    }
	}
}
