package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
}
