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
import edu.asu.spring.quadriga.service.network.INetworkTransformer;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class NetworkTransformer implements INetworkTransformer {

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	private IConceptCollectionManager conceptCollectionManager;

	@Autowired
	private EventParser parser;

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkTransformer.class);


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ITransformedNetwork transformNetwork(List<INetworkNodeInfo> networkTopNodesList){
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

		ITransformedNetwork networkJson = new TransformedNetwork(nodes, links);

		return networkJson;

	}
}


