package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.json.CytoscapeLinkDataObject;
import edu.asu.spring.quadriga.domain.network.json.CytoscapeLinkObject;
import edu.asu.spring.quadriga.domain.network.json.CytoscapeNodeDataObject;
import edu.asu.spring.quadriga.domain.network.json.CytoscapeNodeObject;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class CytoscapeJsonCreator implements IJsonCreator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.service.network.impl.JsonCreator#getJson(java.
     * util.Map, java.util.List)
     */
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
    
    @Override
    public List<CytoscapeNodeObject> getNodes(Map<String, Node> nodes){
        List<CytoscapeNodeObject> cytoscapeNodeList = new ArrayList<CytoscapeNodeObject>();
        List<Node> nodeList = new ArrayList<Node>(nodes.values());
        for (int i = 0; i < nodeList.size(); i++) {
            
            CytoscapeNodeDataObject dataObj= new CytoscapeNodeDataObject();
            dataObj.setId(nodeList.get(i).getId());    
            dataObj.setConceptName(nodeList.get(i).getLabel());
            dataObj.setConceptUri(nodeList.get(i).getConceptId());
            dataObj.setConceptId(nodeList.get(i).getConceptIdShort());
            if (nodeList.get(i) instanceof PredicateNode) {
                dataObj.setGroup("0");
            } else {
                dataObj.setGroup("1");
            }
            dataObj.setSourceReference(nodeList.get(i).getSourceReference());
            dataObj.setStatementIds(new ArrayList<String>());
            for (int j = 0; j < nodeList.get(i).getStatementIds().size(); j++) {
                dataObj.getStatementIds().add(nodeList.get(i).getStatementIds().get(j)); 
            }
            
            CytoscapeNodeObject obj = new CytoscapeNodeObject();
            obj.setData(dataObj);
            cytoscapeNodeList.add(obj);
        } 
        
        return cytoscapeNodeList;
    }
    
    @Override
    public List<CytoscapeLinkObject> getLinks(List<Link> links){
        List<CytoscapeLinkObject> cytoscapeLinkList = new ArrayList<CytoscapeLinkObject>();
        for (int i = 0; i < links.size(); i++) {
            CytoscapeLinkDataObject dataObj = new CytoscapeLinkDataObject();
            dataObj.setId(""+i);
            dataObj.setSource(links.get(i).getSubject().getId());
            dataObj.setTarget(links.get(i).getObject().getId()); 
            dataObj.setLabel(links.get(i).getLabel());
            dataObj.setSourceReference(links.get(i).getSourceReference());
            dataObj.setStatementIds(new ArrayList<String>());
            dataObj.getStatementIds().add(links.get(i).getStatementId()) ;
            CytoscapeLinkObject obj = new CytoscapeLinkObject();
            obj.setData(dataObj);
            cytoscapeLinkList.add(obj);
        }
        return cytoscapeLinkList;
    }
}
