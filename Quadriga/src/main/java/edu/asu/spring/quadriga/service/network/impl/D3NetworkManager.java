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

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.ID3NetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.impl.NetworkJSon;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class D3NetworkManager implements ID3NetworkManager {

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	private IConceptCollectionManager conceptCollectionManager;

	@Autowired
	private EventParser parser;

	private static final Logger logger = LoggerFactory
			.getLogger(D3NetworkManager.class);


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public INetworkJSon parseNetworkForD3Jquery(List<INetworkNodeInfo> networkTopNodesList){
		Map<String, Node> nodes = new HashMap<String, Node>();
        List<Link> links = new ArrayList<Link>();
        
		if(networkTopNodesList!=null){

			if( networkTopNodesList.size()>0 ){
				Iterator <INetworkNodeInfo> topNodeIterator = networkTopNodesList.iterator();
				while(topNodeIterator.hasNext()){
					INetworkNodeInfo networkNodeInfo = topNodeIterator.next();
					if(networkNodeInfo.getStatementType().equals(INetworkManager.RELATIONEVENT)){					    
					    try {
                            parser.parseStatement(networkNodeInfo.getId(), nodes, links);
                        } catch (JAXBException e) {
                            logger.error("Issue while parsing the JAXB object",e);
                        } catch (QStoreStorageException e) {
                            logger.error("QStore retrieve error",e);
                        }
					}
				}
			}else{
				return null;
			}
		}else{
			return null;
		}

		INetworkJSon networkJson = new NetworkJSon(getD3JSON(nodes, links));

		return networkJson;

	}
	
	private String getD3JSON(Map<String, Node> nodes, List<Link> links) {
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
	        buffer.append("\"group\":");
	        if (node instanceof PredicateNode) {
	            buffer.append("0");
	        } else {
	            buffer.append("1");
	        }	       
	        buffer.append(",");
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
            buffer.append("\"");
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


