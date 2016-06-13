package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class CytoscapeJsonCreator implements JsonCreator {

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.network.impl.JsonCreator#getJson(java.util.Map, java.util.List)
     */
    @Override
    public String getJson(Map<String, Node> nodes, List<Link> links) {
        StringBuffer sb = new StringBuffer();
        
        sb.append("[\n");
        List<Node> nodeList = new ArrayList<Node>(nodes.values());
        for (Node node : nodeList) {
            sb.append("{ ");
            sb.append("data: ");
            sb.append("{ ");
            sb.append("id: '" + node.getId() + "', ");
            sb.append("conceptName: '" + node.getLabel() + "', ");
            sb.append("conceptUri: '" + node.getConceptId() + "', ");
            sb.append("conceptId: '" + node.getConceptIdShort() + "', ");
            sb.append("group: ");
            if (node instanceof PredicateNode) {
                sb.append("0");
            } else {
                sb.append("1");
            } 
            sb.append(", ");
            sb.append("sourceReference: '" + node.getSourceReference() + "', ");
            sb.append("statementIds: [");
            for (int j = 0; j < node.getStatementIds().size(); j++) {
                sb.append("'");
                sb.append(node.getStatementIds().get(j));
                sb.append("'");
                if(j < node.getStatementIds().size()-1){
                    sb.append(",");
                }
            }
            sb.append("], ");
            sb.append(" }");
            sb.append(" },\n");
        }
        
        for (Link link : links) {
            sb.append("{ ");
            sb.append("data: ");
            sb.append("{ ");
            sb.append("id: '" + links.indexOf(link) + "', ");
            sb.append("source: ");
            sb.append("'" + link.getSubject().getId() + "', ");
            sb.append("target: ");
            sb.append("'" + link.getObject().getId() + "', ");
            sb.append("label: '" + link.getLabel() + "', ");
            sb.append("sourceReference: '" + link.getSourceReference() + "', ");
            sb.append("statementIds: ['" + link.getStatementId() + "'] ");
            
            sb.append(" }");
            sb.append(" },\n");
        }
        sb.append("]");
        
        return sb.toString();
    }
}
