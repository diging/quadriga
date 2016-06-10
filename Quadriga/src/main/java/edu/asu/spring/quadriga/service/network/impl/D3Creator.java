package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class D3Creator implements ID3Creator {

    @Override
    public String getD3JSON(Map<String, Node> nodes, List<Link> links) {
        StringBuffer buffer = new StringBuffer();
        
        // nodes
        buffer.append("{\n\"nodes\":[");
        List<Node> nodeList = new ArrayList<Node>(nodes.values());
        for (Node node : nodeList) {
            buffer.append("{\"name\":\"");
            buffer.append(node.getLabel());
            buffer.append("\",");
            buffer.append("\"id\":\"");
            buffer.append(node.getId());
            buffer.append("\",");
            buffer.append("\"conceptId\":\"");
            buffer.append(node.getConceptId());
            buffer.append("\",");
            buffer.append("\"conceptCpId\":\"");
            buffer.append(node.getConceptIdShort());
            buffer.append("\",");
            buffer.append("\"group\":");
            if (node instanceof PredicateNode) {
                buffer.append("0");
            } else {
                buffer.append("1");
            }          
            buffer.append(",");
            // add source reference
            buffer.append("\"sourceReference\": \"" + node.getSourceReference() + "\",");
            // source reference end
            buffer.append("\"statementid\":[");
            for (int j = 0; j < node.getStatementIds().size(); j++) {
                buffer.append("\"");
                buffer.append(node.getStatementIds().get(j));
                buffer.append("\"");
                if(j < node.getStatementIds().size()-1){
                    buffer.append(",");
                }
            }
            buffer.append("]\n");
            buffer.append("}");
            if (nodeList.indexOf(node) < nodeList.size() - 1) {
                buffer.append(",");
            }
            buffer.append("\n");
        }
        buffer.append("],");
        
        // links
        buffer.append("\n\"links\":[\n");
        for (Link link : links) {
            buffer.append("{\"source\":");
            buffer.append(nodeList.indexOf(link.getSubject()));
            buffer.append(",");
            buffer.append("\"target\":");
            buffer.append(nodeList.indexOf(link.getObject()));
            buffer.append(",");
            buffer.append("\"label\":");
            buffer.append("\"");
            buffer.append(link.getLabel());
            buffer.append("\",");
            // add source reference
            buffer.append("\"sourceReference\": \"" + link.getSourceReference() + "\",");
            // source reference end
            buffer.append("}");
            if (links.indexOf(link) < links.size() - 1) {
                buffer.append(",");
            }
            buffer.append("\n");
        }
        buffer.append("]\n}");
        
        return buffer.toString();
    }
}
