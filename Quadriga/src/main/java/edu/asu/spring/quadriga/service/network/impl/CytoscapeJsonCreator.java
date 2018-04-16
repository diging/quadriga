package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.TransformationRequestStatus;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeLinkDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeLinkObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeNodeDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeNodeObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeSearchObject;


@Service
public class CytoscapeJsonCreator implements IJsonCreator {

    private final Logger logger = LoggerFactory.getLogger(CytoscapeJsonCreator.class);

    @Override
    public String getJson(Map<String, Node> nodes, List<Link> links) {
        StringBuffer sb = new StringBuffer();

        sb.append("[");
        List<Node> nodeList = new ArrayList<Node>(nodes.values());
        for (int i = 0; i < nodeList.size(); i++) {
            sb.append("{ ");
            sb.append("\"data\": ");
            sb.append("{ ");
            sb.append("\"id\": \"" + nodeList.get(i).getId() + "\", ");
            sb.append("\"conceptName\": \"" + nodeList.get(i).getLabel() + "\", ");
            sb.append("\"conceptUri\": \"" + nodeList.get(i).getConceptId() + "\", ");
            sb.append("\"conceptId\": \"" + nodeList.get(i).getConceptIdShort() + "\", ");
            sb.append("\"group\": ");
            if (nodeList.get(i) instanceof PredicateNode) {
                sb.append("0");
            } else {
                sb.append("1");
            }
            sb.append(", ");
            sb.append("\"sourceReference\": \"" + nodeList.get(i).getSourceReference() + "\", ");
            sb.append("\"statementIds\": [");
            for (int j = 0; j < nodeList.get(i).getStatementIds().size(); j++) {
                sb.append("\"");
                sb.append(nodeList.get(i).getStatementIds().get(j));
                sb.append("\"");
                if (j < nodeList.get(i).getStatementIds().size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("] ");
            sb.append(" }");
            if (i == nodeList.size() - 1) {
                sb.append(" }\n");
            } else {
                sb.append(" },\n");
            }
        }

        for (int i = 0; i < links.size(); i++) {
            if (i == 0) {
                sb.append(",");
            }
            sb.append("{ ");
            sb.append("\"data\": ");
            sb.append("{ ");
            sb.append("\"id\": \"" + i + "\", ");
            sb.append("\"source\": ");
            sb.append("\"" + links.get(i).getSubject().getId() + "\", ");
            sb.append("\"target\": ");
            sb.append("\"" + links.get(i).getObject().getId() + "\", ");
            sb.append("\"label\": \"" + links.get(i).getLabel() + "\", ");
            sb.append("\"sourceReference\": \"" + links.get(i).getSourceReference() + "\", ");
            sb.append("\"statementIds\": [\"" + links.get(i).getStatementId() + "\"] ");

            sb.append(" }");

            if (i == links.size() - 1) {
                sb.append(" }\n");
            } else {
                sb.append(" },\n");
            }
        }
        sb.append("]");

        return sb.toString();
    }
    
    /**
     * This method constructs a cytoscape nodes using network nodes.
     * @param  nodes
     * @return cytoscape nodes
     * @author Chiraag Subramanian
     */
    @Override
    public List<CytoscapeNodeObject> getNodes(List<Node> nodes){
        
        List<CytoscapeNodeObject> cytoscapeNodeList = new ArrayList<CytoscapeNodeObject>();
        for(Node node : nodes){
            cytoscapeNodeList.add(createCytoscapeNodeObject(node));
        }
        return cytoscapeNodeList;
    }
    
    /**
     * This method constructs a cytoscape links using network nodes.
     * @param  links
     * @return cytoscape links
     * @author Chiraag Subramanian
     */
    @Override
    public List<CytoscapeLinkObject> getLinks(List<Link> links){

        List<CytoscapeLinkObject> cytoscapeLinkList = new ArrayList<CytoscapeLinkObject>();
        int i = 0;
        for(Link link : links){
            cytoscapeLinkList.add(createCytoscapeLinkObject(link, i));
            i++;
        }
        return cytoscapeLinkList;
    }
    
    private CytoscapeNodeObject createCytoscapeNodeObject(Node node){
        
        CytoscapeNodeDataObject dataObj= new CytoscapeNodeDataObject();
        dataObj.setId(node.getId());
        dataObj.setConceptName(node.getLabel());
        dataObj.setConceptUri(node.getConceptId());
        dataObj.setConceptId(node.getConceptIdShort());
        if(node instanceof PredicateNode){
            dataObj.setGroup("0");
        }
        else{
            dataObj.setGroup("1");
        }
        dataObj.setSourceReference(node.getSourceReference());
        dataObj.setStatementIds(node.getStatementIds());
        
        CytoscapeNodeObject obj = new CytoscapeNodeObject();
        obj.setData(dataObj);
        return obj;
    }
    
    private CytoscapeLinkObject createCytoscapeLinkObject(Link link, int id){
        
        CytoscapeLinkDataObject dataObj = new CytoscapeLinkDataObject();
        dataObj.setId(""+id);
        dataObj.setSource(link.getSubject().getId());
        dataObj.setTarget(link.getObject().getId());
        dataObj.setLabel(link.getLabel());
        dataObj.setSourceReference(link.getSourceReference());
        dataObj.setStatementIds(new ArrayList<String>());
        dataObj.getStatementIds().add(link.getStatementId());

        CytoscapeLinkObject obj = new CytoscapeLinkObject();
        obj.setData(dataObj);
        return obj;
    }
    
    /**
     * This method constructs the response object consisting of the transformed network and the status of the async network transformation.
     * @param  transformedNetwork
     * @param  transformationRequestStatus
     * @return CytoscapeSearchObject 
     * @author Chiraag Subramanian
     */
    @Override
    public CytoscapeSearchObject getCytoscapeSearchObject(ITransformedNetwork transformedNetwork, TransformationRequestStatus transformationRequestStatus) {
        CytoscapeSearchObject cytoscapeSearchObject = new CytoscapeSearchObject();
        if (transformedNetwork != null && transformedNetwork.getNodes().size() > 0) {
            cytoscapeSearchObject.setNodes(getNodes(new ArrayList<Node>(transformedNetwork.getNodes().values())));
            cytoscapeSearchObject.setLinks(getLinks(transformedNetwork.getLinks()));
        } else{
            cytoscapeSearchObject.setNetworkEmpty(true);
        }
        cytoscapeSearchObject.setStatus(transformationRequestStatus.getStatusCode());
        
        return cytoscapeSearchObject;
    }
}