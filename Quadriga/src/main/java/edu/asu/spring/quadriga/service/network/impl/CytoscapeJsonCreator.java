package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;
import edu.asu.spring.quadriga.web.network.INetworkStatus;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeLinkDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeLinkObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeNodeDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeNodeObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.PublicSearchObject;

@Service
public class CytoscapeJsonCreator implements IJsonCreator {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Map<Integer, Future<PublicSearchObject>> searchResultMap = new HashMap<Integer, Future<PublicSearchObject>>();
    private static final Logger logger = LoggerFactory.getLogger(CytoscapeJsonCreator.class);
    
    @Autowired
    private INetworkTransformationManager transformationManager;
     
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
    public List<CytoscapeNodeObject> getNodes(List<Node> nodeList){
        
        List<CytoscapeNodeObject> cytoscapeNodeList = new ArrayList<CytoscapeNodeObject>();
        for(Node node : nodeList){
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
            cytoscapeNodeList.add(createCytoscapeNodeObject(dataObj));
        }
        return cytoscapeNodeList;
    }
    
    @Override
    public List<CytoscapeLinkObject> getLinks(List<Link> links){

        List<CytoscapeLinkObject> cytoscapeLinkList = new ArrayList<CytoscapeLinkObject>();
        int i = 0;
        for(Link link : links){
            CytoscapeLinkDataObject dataObj = new CytoscapeLinkDataObject();
            dataObj.setId(""+i);
            dataObj.setSource(link.getSubject().getId());
            dataObj.setTarget(link.getObject().getId());
            dataObj.setLabel(link.getLabel());
            dataObj.setSourceReference(link.getSourceReference());
            dataObj.setStatementIds(new ArrayList<String>());
            dataObj.getStatementIds().add(link.getStatementId());
            cytoscapeLinkList.add(createCytoscapeLinkObject(dataObj));
            i++;
        }
        return cytoscapeLinkList;
    }
    
    private CytoscapeNodeObject createCytoscapeNodeObject(CytoscapeNodeDataObject dataObj){
        CytoscapeNodeObject obj = new CytoscapeNodeObject();
        obj.setData(dataObj);
        return obj;
    }
    
    private CytoscapeLinkObject createCytoscapeLinkObject(CytoscapeLinkDataObject dataObj){
        CytoscapeLinkObject obj = new CytoscapeLinkObject();
        obj.setData(dataObj);
        return obj;
    }
    
    @Override
    public Integer submitTransformationRequest(String conceptId, IProject project){
        
        Callable<PublicSearchObject> callable = () -> {

            PublicSearchObject publicSearchObject = new PublicSearchObject();
            ITransformedNetwork transformedNetwork = transformationManager
                    .getSearchTransformedNetwork(project.getProjectId(), conceptId, INetworkStatus.APPROVED);
            if (transformedNetwork != null) {
                publicSearchObject.setNodes(getNodes(new ArrayList<Node>(transformedNetwork.getNodes().values())));
                publicSearchObject.setLinks(getLinks(transformedNetwork.getLinks()));
            }
            if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
                publicSearchObject.setNetworkEmpty(true);
            }
            return publicSearchObject;
        };
        
        Future<PublicSearchObject> future = executorService.submit(callable);
        Random randomTokenGenerator = new Random();
        Integer randomToken = randomTokenGenerator.nextInt(100);
        while (searchResultMap.containsKey(randomToken)) {
            randomToken = randomTokenGenerator.nextInt(100);
        }
        searchResultMap.put(randomToken, future);
        
        return randomToken;
    }
    
    @Override
    public PublicSearchObject getSearchTransformedNetwork(Integer tokenId){
        
        PublicSearchObject publicSearchObject = new PublicSearchObject();
        if (!searchResultMap.containsKey(tokenId)) {
            publicSearchObject.setStatus(2);
            return publicSearchObject;
        }

        Future<PublicSearchObject> futureResult = searchResultMap.get(tokenId);
        if (futureResult != null && futureResult.isDone()) {
            try {
                publicSearchObject = futureResult.get();
                publicSearchObject.setStatus(1);
                searchResultMap.remove(tokenId);
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Exception while retrieving the result", e);
                publicSearchObject.setStatus(2);
            }
        } else {
            publicSearchObject.setStatus(3);
        }
        
        return publicSearchObject;
    }
}
