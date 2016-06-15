package edu.asu.spring.quadriga.service.network.transform.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;


/**
 * This class reads graphml files that describe patterns and mappings.
 * It expects that createGraph() is called for the pattern file and 
 * createMapping() for the mapping file. It then holds a reference two both
 * graphs by referring to the start node of these mappings.
 * 
 * This class expects that there is only one graph with one start node that
 * does not have any incoming edges.
 * 
 * @author viraaj navalekar, Julia Damerow
 *
 */
public class GraphMapper {
	
	private Node startNode;
	private TransformNode startNodeOfMapping;

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	/**
	 * This method creates the pattern from an graphml file.
	 * @param file Absolute path to the pattern file.
	 */
	public void createGraph(String file) {
	
		Graph graph = getGraphFromFile(file);
	    
	    Iterable<Vertex> vertices = graph.getVertices();
	    Iterator<Vertex> verticesIterator = vertices.iterator();
	    
	    Map<Object, Node> nodeMap = new HashMap<Object, Node>();
	    Map<Object, Relation> relationMap = new HashMap<Object, Relation>();
	    
	    createNodeMap(verticesIterator, nodeMap);
	    
	    createRelationMap(vertices, nodeMap, relationMap);
	     
	    createNestedReations(vertices, nodeMap, relationMap);
	    
	}
	
	
	/**
	 * Creates the template mapping graph.
	 *
	 * @param templateFilePath the template graphml file path
	 * @param nodeInfo the node generator info
	 */
	public void createTemplateMappingGraph(String templateFilePath) {
		// TODO Auto-generated method stub
		Graph graph = getGraphFromFile(templateFilePath);
	    
	    Iterable<Vertex> vertices = graph.getVertices();
	    Iterator<Vertex> verticesIterator = vertices.iterator();
	    
	    Map<Object, Node> nodeMap = new HashMap<Object, Node>();
	    Map<Object, Relation> relationMap = new HashMap<Object, Relation>();
	    createNodeMap(verticesIterator, nodeMap);
	    
	    createRelationMap(vertices, nodeMap, relationMap);
	    
	    createNestedReations(vertices, nodeMap, relationMap);
	}

	/**
	 * Creates the node map.
	 *
	 * @param verticesIterator the vertices iterator
	 * @param nodeMap the node map
	 */
	private void createNodeMap(Iterator<Vertex> verticesIterator,
			Map<Object, Node> nodeMap) {
		while (verticesIterator.hasNext()) {
	    	Vertex vertex = verticesIterator.next();
	    	Object id = vertex.getId();
	    	if (nodeMap.get(id) != null)
	    		continue;
	    	
	    	Node newNode = new Node();
	    	String concept = (String) vertex.getProperty("Concept");
	    	String nodeId = (String) vertex.getProperty("NodeId");
	    	String type = (String) vertex.getProperty("Type");
	    	newNode.setConcept(concept);
	    	newNode.setId(nodeId);
	    	newNode.setType(type);
	    	nodeMap.put(id, newNode);
	    }
	}

	/**
	 * Gets the graph from the graphml file.
	 *
	 * @param file the graphml file path
	 * @return the graph from file
	 */
	private Graph getGraphFromFile(String file) {
		Graph graph = new TinkerGraph();
	    GraphMLReader reader = new GraphMLReader(graph);
	 
	    InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			reader.inputGraph(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}

	/**
	 * Creates the nested reations.
	 *
	 * @param vertices the vertices
	 * @param nodeMap the node map
	 * @param relationMap the relation map
	 * @param nodeGenerator the node generator info. Will create relation nodes if not null.
	 */
	private void createNestedReations(Iterable<Vertex> vertices,
			Map<Object, Node> nodeMap, Map<Object, Relation> relationMap) {
		Iterator<Vertex> verticesIterator;
		verticesIterator = vertices.iterator();
	    
	    while (verticesIterator.hasNext()) {
	    	Vertex vertex = verticesIterator.next();
	    	
	    	Node node = nodeMap.get(vertex.getId());
	    	
	        Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT, new String[0]);
	        Iterator<Edge> outEdgeIt = outEdges.iterator();
	        
	        Node potSubject = null;
	        Object subjVertId = null;
	        Node potObject = null;
	        Object objVertId  = null;
	        
	        // get outgoing edges (= 2)
	        while (outEdgeIt.hasNext()) {
	        	Edge outEdge = outEdgeIt.next();
	        	
	        	Vertex outVertex = outEdge.getVertex(Direction.IN);
	        	Node goingIn = nodeMap.get(outVertex.getId());
		        String nodeType = (String) outEdge.getProperty("relationship");
		    	if (nodeType!=null && nodeType.equals("subject")) {
		    		potSubject = goingIn;
		    		subjVertId = outVertex.getId();
		    	}
		    	else if (nodeType!=null && nodeType.equals("object")) {
		    		potObject = goingIn;
		    		objVertId = outVertex.getId();
		    	}
	        }
	   
	        // if relType is set and there are potSubj and potObj
	        // node is nested relationship
	       if (potObject != null && potSubject != null) {
	            Relation rel = relationMap.get(vertex.getId());
	        	
	            Node subject = relationMap.get(subjVertId);
	        	if (subject == null)
	        		subject = potSubject;
	        	
	        	Node object = relationMap.get(objVertId);
	        	if (object == null)
	        		object = potObject;
	        	
	        	rel.setPredicate(node);
	        	rel.setSubject(subject);
	        	rel.setObject(object);
	        	
	        }
	    }
	}

	/**
	 * Creates the relation map.
	 *
	 * @param vertices the vertices
	 * @param nodeMap the node map
	 * @param relationMap the relation map
	 * @param startNode the start node
	 * @return the relation
	 */
	private void createRelationMap(Iterable<Vertex> vertices,
			Map<Object, Node> nodeMap, Map<Object, Relation> relationMap) {
		Iterator<Vertex> verticesIterator = null;
		verticesIterator = vertices.iterator();
	    while (verticesIterator.hasNext()) {
	    	Vertex vertex = verticesIterator.next();
	    	
	        Iterable<Edge> edges = vertex.getEdges(Direction.IN, new String[0]);
	        Iterator<Edge> edgesIterator = edges.iterator();
	        
	        Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT, new String[0]);
	        Iterator<Edge> outEdgeIt = outEdges.iterator();
	   
	        String relType = null;
	        
	        // get incoming edges (max 1)
	        while (edgesIterator.hasNext()) {
	   
	          Edge edge = edgesIterator.next();
	          
	          relType = (String) edge.getProperty("relationship");
	        }
	        
	        Node potSubject = null;
	        Node potObject = null;
	        
	        // get outgoing edges (max 2)
	        while (outEdgeIt.hasNext()) {
	        	Edge outEdge = outEdgeIt.next();
	        	
	        	Vertex outVertex = outEdge.getVertex(Direction.IN);
	        	Node goingIn = nodeMap.get(outVertex.getId());
		        String nodeType = (String) outEdge.getProperty("relationship");
		    	if (nodeType!=null && nodeType.equals("subject"))
		    		potSubject = goingIn;
		    	else if (nodeType!=null && nodeType.equals("object"))
		    		potObject = goingIn;
	        }
	        
	        if (potObject != null && potSubject != null) {
	        	Relation rel = relationMap.get(vertex.getId());
        		if (rel == null) {
        			rel = new Relation();
        			rel.setId(nodeMap.get(vertex.getId()).getId());
        			relationMap.put(vertex.getId(), rel);
        		}
	        }
	        
	        // if relType is null, no edge is going in and node can only be predicate
	        // (or single node)
	        if (relType == null) {
	        	Relation rel = relationMap.get(vertex.getId());
        		if (rel == null) {
        			rel = new Relation();
        			rel.setId(nodeMap.get(vertex.getId()).getId());
        			relationMap.put(vertex.getId(), rel);
        		}
        		this.setStartNode(rel);
	        }
	        
	    }
	}
	
	/**
	 * This method creates the mapping file.
	 * @param file Absolute path to the mapping file.
	 */
	public void createMapping(String file) {
		Graph graph = getGraphFromFile(file);
	    
	    Iterable<Vertex> vertices = graph.getVertices();
	    Iterator<Vertex> verticesIterator = vertices.iterator();
	    
	    Map<Object, TransformNode> nodeMap = new HashMap<Object, TransformNode>();
	    
	    while (verticesIterator.hasNext()) {
	    	Vertex vertex = verticesIterator.next();
	    	Object id = vertex.getId();
	    	if (nodeMap.get(id) != null)
	    		continue;
	    	
	    	TransformNode newNode = new TransformNode();
	    	String concept = (String) vertex.getProperty("Concept");
	    	String nodeId = (String) vertex.getProperty("CorrespondingNodeId");
	    	String type = (String) vertex.getProperty("Type");
	    	newNode.setConcept(concept);
	    	newNode.setCorrespondingId(nodeId);
	    	newNode.setType(type);
	    	nodeMap.put(id, newNode);
	    }
	    
	    verticesIterator = vertices.iterator();
	    
	    while (verticesIterator.hasNext()) {
	    	Vertex vertex = verticesIterator.next();
	    	TransformNode node = nodeMap.get(vertex.getId());
	    	
	    	Iterable<Edge> inEdges = vertex.getEdges(Direction.IN, new String[0]);
		    Iterator<Edge> inEdgeIt = inEdges.iterator();
		    int nrOfincomingEdges = 0;
		    while (inEdgeIt.hasNext()) {
		    	inEdgeIt.next();
		    	nrOfincomingEdges++;
		    }
		    
		    if (nrOfincomingEdges == 0) {
		    	setStartNodeOfMapping(node);
		    }
	    	
	    	Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT, new String[0]);
		    Iterator<Edge> outEdgeIt = outEdges.iterator();
		    
		    while (outEdgeIt.hasNext()) {
	        	Edge outEdge = outEdgeIt.next();
	        	
	        	Vertex outVertex = outEdge.getVertex(Direction.IN);
	        	TransformNode goingIn = nodeMap.get(outVertex.getId());
		      
	        	String id = (String) outEdge.getProperty("CorrespondingNodeId");
		    	TransformLink link = new TransformLink();
		    	link.setCorrespondingId(id);
		    	
		    	String type = (String) outEdge.getProperty("Type");
		    	link.setType(type);
		    	
		    	String concept = (String) outEdge.getProperty("Concept");
		    	link.setConcept(concept);
		    	
		    	String startTime = (String) outEdge.getProperty("NodeIdStartTime");
		    	link.setStartTime(startTime);
		    	
		    	String endTime = (String) outEdge.getProperty("NodeIdEndTime");
		    	link.setEndTime(endTime);
		    	
		    	String occurTime = (String) outEdge.getProperty("NodeIdOccurTime");
		    	link.setOccurTime(occurTime);
		    	
		    	String place = (String) outEdge.getProperty("NodeIdPlace");
		    	link.setPlace(place);
		    	
		    	String statement = (String) outEdge.getProperty("RepresentedStatement");
		    	link.setRepresentedStatement(statement);
		    	
		    	link.setObject(goingIn);
		    	
		    	if (node.getLinks() == null)
		    		node.setLinks( new ArrayList<TransformLink>());
		    	node.getLinks().add(link);
	        }
	    }
	}

	public TransformNode getStartNodeOfMapping() {
		return startNodeOfMapping;
	}

	public void setStartNodeOfMapping(TransformNode startNodeOfMapping) {
		this.startNodeOfMapping = startNodeOfMapping;
	}

	
}
