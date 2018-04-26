package edu.asu.spring.quadriga.service.network.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.service.network.TransformationRequestStatus;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeLinkDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeLinkObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeNodeDataObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeNodeObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeSearchObject;

/**
 * This class consists of unit tests for CytoscapeJsonCreator.
 * 
 * @author Chiraag Subramanian
 *
 */
public class CytoscapeJsonCreatorTest {
    
    @Mock
    private ITransformedNetwork transformedNetwork;
    
    @InjectMocks
    private CytoscapeJsonCreator cytoscapeJsonCreator;
    
    private Map<String, Node> nodes = new HashMap<String, Node>();
    private List<Link> links = new ArrayList<Link>();
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);  
        
        List<String> statementIds = new ArrayList<String>();
        statementIds.add("REL_EVN-7470806594494772646");
        String sourceReference = "http://hdl.handle.net/10776/2326";
        Node node1 = new PredicateNode();
        node1.setId("eeca81e6-ab2a-468c-8910-f958db9e4071");
        node1.setLabel("be");
        node1.setConceptId("http://www.digitalhps.org/concepts/CON3fbc4870-6028-4255-9998-14acf028a316");
        node1.setConceptIdShort("CON3fbc4870-6028-4255-9998-14acf028a316");
        node1.setSourceReference(sourceReference);
        node1.setStatementIds(statementIds);
        
        Node node2 = new Node();
        node2.setId("APP_EVN6552216626031446394");
        node2.setLabel("Andrew Zachary Fire");
        node2.setConceptId("http://www.digitalhps.org/concepts/CON72aa3b79-2964-47a9-97c5-437ae60e6086");
        node2.setConceptIdShort("CON72aa3b79-2964-47a9-97c5-437ae60e6086");
        node2.setSourceReference(sourceReference);
        node2.setStatementIds(statementIds);
        
        Node node3 = new PredicateNode();
        node3.setId("c9f3109c-853a-466c-856d-21b6474e4674");
        node3.setLabel("have");
        node3.setConceptId("http://www.digitalhps.org/concepts/CON83f5110b-5034-4c95-82f8-8f80ff55a1b9");
        node3.setConceptIdShort("CON83f5110b-5034-4c95-82f8-8f80ff55a1b9");
        node3.setSourceReference(sourceReference);
        node3.setStatementIds(statementIds);
        
        Node node4 = new Node();
        node4.setId("APP_EVN-2776024847322037169");
        node4.setLabel("Sydney Brenner");
        node4.setConceptId("http://www.digitalhps.org/concepts/CON930c0c42-1f57-4dab-b060-334e77107377");
        node4.setConceptIdShort("CON930c0c42-1f57-4dab-b060-334e77107377");
        node4.setSourceReference(sourceReference);
        node4.setStatementIds(statementIds);
        
        Node node5 = new Node();
        node5.setId("APP_EVN-3698230411436013807");
        node5.setLabel("colleague");
        node5.setConceptId("http://www.digitalhps.org/concepts/CONfdacQrmKbERi");
        node5.setConceptIdShort("CONfdacQrmKbERi");
        node5.setSourceReference(sourceReference);
        node5.setStatementIds(statementIds);
        
        nodes.put(node1.getId(), node1);
        nodes.put(node2.getId(), node2);
        nodes.put(node3.getId(), node3);
        nodes.put(node4.getId(), node4);
        nodes.put(node5.getId(), node5);
        
        Link link1 = new Link();
        link1.setSubject(node1);
        link1.setObject(node5);
        link1.setLabel("has subject");
        link1.setSourceReference(sourceReference);
        link1.setStatementId(statementIds.get(0));
        
        Link link2 = new Link();
        link2.setSubject(node3);
        link2.setObject(node4);
        link2.setLabel("has subject");
        link2.setSourceReference(sourceReference);
        link2.setStatementId(statementIds.get(0));
        
        Link link3 = new Link();
        link3.setSubject(node1);
        link3.setObject(node2);
        link3.setLabel("has object");
        link3.setSourceReference(sourceReference);
        link3.setStatementId(statementIds.get(0));
        
        Link link4 = new Link();
        link4.setSubject(node3);
        link4.setObject(node1);
        link4.setLabel("has object");
        link4.setSourceReference(sourceReference);
        link4.setStatementId(statementIds.get(0));

        links.add(link1);
        links.add(link2);
        links.add(link3);
        links.add(link4);
    }    

    
    
    @Test
    public void test_getJson_success(){

        String json ="[{ \"data\": { \"id\": \"eeca81e6-ab2a-468c-8910-f958db9e4071\", \"conceptName\": \"be\", \"conceptUri\": \"http://www.digitalhps.org/concepts/CON3fbc4870-6028-4255-9998-14acf028a316\", \"conceptId\": \"CON3fbc4870-6028-4255-9998-14acf028a316\", \"group\": 0, \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"APP_EVN6552216626031446394\", \"conceptName\": \"Andrew Zachary Fire\", \"conceptUri\": \"http://www.digitalhps.org/concepts/CON72aa3b79-2964-47a9-97c5-437ae60e6086\", \"conceptId\": \"CON72aa3b79-2964-47a9-97c5-437ae60e6086\", \"group\": 1, \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"c9f3109c-853a-466c-856d-21b6474e4674\", \"conceptName\": \"have\", \"conceptUri\": \"http://www.digitalhps.org/concepts/CON83f5110b-5034-4c95-82f8-8f80ff55a1b9\", \"conceptId\": \"CON83f5110b-5034-4c95-82f8-8f80ff55a1b9\", \"group\": 0, \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"APP_EVN-2776024847322037169\", \"conceptName\": \"Sydney Brenner\", \"conceptUri\": \"http://www.digitalhps.org/concepts/CON930c0c42-1f57-4dab-b060-334e77107377\", \"conceptId\": \"CON930c0c42-1f57-4dab-b060-334e77107377\", \"group\": 1, \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"APP_EVN-3698230411436013807\", \"conceptName\": \"colleague\", \"conceptUri\": \"http://www.digitalhps.org/concepts/CONfdacQrmKbERi\", \"conceptId\": \"CONfdacQrmKbERi\", \"group\": 1, \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } }\n"
                      +",{ \"data\": { \"id\": \"0\", \"source\": \"eeca81e6-ab2a-468c-8910-f958db9e4071\", \"target\": \"APP_EVN-3698230411436013807\", \"label\": \"has subject\", \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"1\", \"source\": \"c9f3109c-853a-466c-856d-21b6474e4674\", \"target\": \"APP_EVN-2776024847322037169\", \"label\": \"has subject\", \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"2\", \"source\": \"eeca81e6-ab2a-468c-8910-f958db9e4071\", \"target\": \"APP_EVN6552216626031446394\", \"label\": \"has object\", \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } },\n"
                      +"{ \"data\": { \"id\": \"3\", \"source\": \"c9f3109c-853a-466c-856d-21b6474e4674\", \"target\": \"eeca81e6-ab2a-468c-8910-f958db9e4071\", \"label\": \"has object\", \"sourceReference\": \"http://hdl.handle.net/10776/2326\", \"statementIds\": [\"REL_EVN-7470806594494772646\"]  } }\n]";
       
        assertEquals(json, cytoscapeJsonCreator.getJson(nodes, links));
    }
    
    @Test
    public void test_getNodes_success(){

        List<CytoscapeNodeObject> cytoscapeNodeObjectsUnderTest = cytoscapeJsonCreator.getNodes(new ArrayList<Node>(nodes.values()));
        assertEquals(5, cytoscapeNodeObjectsUnderTest.size() );
        
        assertEquals("eeca81e6-ab2a-468c-8910-f958db9e4071", cytoscapeNodeObjectsUnderTest.get(0).getData().getId());
        assertEquals("be", cytoscapeNodeObjectsUnderTest.get(0).getData().getConceptName());
        assertEquals("http://www.digitalhps.org/concepts/CON3fbc4870-6028-4255-9998-14acf028a316", cytoscapeNodeObjectsUnderTest.get(0).getData().getConceptUri());
        assertEquals("CON3fbc4870-6028-4255-9998-14acf028a316", cytoscapeNodeObjectsUnderTest.get(0).getData().getConceptId());
        assertEquals("0", cytoscapeNodeObjectsUnderTest.get(0).getData().getGroup());
        assertEquals("http://hdl.handle.net/10776/2326", cytoscapeNodeObjectsUnderTest.get(0).getData().getSourceReference());

        assertEquals("APP_EVN6552216626031446394", cytoscapeNodeObjectsUnderTest.get(1).getData().getId());
        assertEquals("Andrew Zachary Fire", cytoscapeNodeObjectsUnderTest.get(1).getData().getConceptName());
        assertEquals("http://www.digitalhps.org/concepts/CON72aa3b79-2964-47a9-97c5-437ae60e6086", cytoscapeNodeObjectsUnderTest.get(1).getData().getConceptUri());
        assertEquals("CON72aa3b79-2964-47a9-97c5-437ae60e6086", cytoscapeNodeObjectsUnderTest.get(1).getData().getConceptId());
        assertEquals("1", cytoscapeNodeObjectsUnderTest.get(1).getData().getGroup());
        assertEquals("http://hdl.handle.net/10776/2326", cytoscapeNodeObjectsUnderTest.get(1).getData().getSourceReference());
    }
    

    @Test
    public void test_getLinks_success(){
        
        List<CytoscapeLinkObject> cytoscapeLinkObjectsUnderTest =  cytoscapeJsonCreator.getLinks(links);
        assertEquals(4, cytoscapeLinkObjectsUnderTest.size() );
        
        assertEquals("0", cytoscapeLinkObjectsUnderTest.get(0).getData().getId());
        assertEquals("has subject", cytoscapeLinkObjectsUnderTest.get(0).getData().getLabel());
        assertEquals("eeca81e6-ab2a-468c-8910-f958db9e4071", cytoscapeLinkObjectsUnderTest.get(0).getData().getSource());
        assertEquals("APP_EVN-3698230411436013807", cytoscapeLinkObjectsUnderTest.get(0).getData().getTarget());
        assertEquals("http://hdl.handle.net/10776/2326",cytoscapeLinkObjectsUnderTest.get(0).getData().getSourceReference());
        
        assertEquals("2", cytoscapeLinkObjectsUnderTest.get(2).getData().getId());
        assertEquals("has object", cytoscapeLinkObjectsUnderTest.get(2).getData().getLabel());
        assertEquals("eeca81e6-ab2a-468c-8910-f958db9e4071", cytoscapeLinkObjectsUnderTest.get(2).getData().getSource());
        assertEquals("APP_EVN6552216626031446394", cytoscapeLinkObjectsUnderTest.get(2).getData().getTarget());
        assertEquals("http://hdl.handle.net/10776/2326",cytoscapeLinkObjectsUnderTest.get(2).getData().getSourceReference());

    }
    
    @Test
    public void test_getCytoscapeSearchObject_success(){
        Mockito.when(transformedNetwork.getNodes()).thenReturn(nodes);
        Mockito.when(transformedNetwork.getLinks()).thenReturn(links);
        
        CytoscapeSearchObject cytoscapeSearchObject1 = cytoscapeJsonCreator.getCytoscapeSearchObject(transformedNetwork, TransformationRequestStatus.RUNNING);
        assertEquals(TransformationRequestStatus.RUNNING.getStatusCode(), cytoscapeSearchObject1.getStatus());
        assertEquals(false, cytoscapeSearchObject1.isNetworkEmpty());
        
        CytoscapeSearchObject cytoscapeSearchObject2 = cytoscapeJsonCreator.getCytoscapeSearchObject(null, TransformationRequestStatus.RUNNING);
        assertEquals(true, cytoscapeSearchObject2.isNetworkEmpty());
    }
    
}
