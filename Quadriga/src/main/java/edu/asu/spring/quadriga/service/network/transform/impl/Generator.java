package edu.asu.spring.quadriga.service.network.transform.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;

/**
 * This class generates the output for transformed networks using Apache
 * velocity.
 * 
 */
@Service
public class Generator {

	@Autowired
    private IConceptpowerCache connector;
	
	private VelocityEngine engine;
	
	@PostConstruct
	public void initVelocityEngine() throws QuadrigaGeneratorException {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath,file");

		engine.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
		
		engine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.Log4JLogChute"
                );
        engine.setProperty("runtime.log.logsystem.log4j.logger","velocity");
        
		try {
            engine.init();
        } catch (Exception e) {
           throw new QuadrigaGeneratorException(e);
        }		
	}

	public String generateText(List<List<TransformNode>> listOfNodeList,
			String chosenTemplate, boolean isMergeDuplicateNodes) throws QuadrigaGeneratorException {

	    Resource templateRes = new ClassPathResource("transformation/xgmml-time.vm");
        String templatePath;
        try {
            templatePath = templateRes.getFile().getAbsolutePath();
        } catch (IOException e1) {
           throw new QuadrigaGeneratorException(e1);
        }

		Template template = null;

		/*
		 * if User selects a different template, override default template with
		 * chosen template.
		 */
		if (chosenTemplate != null || ("").equalsIgnoreCase(chosenTemplate))
			templatePath = chosenTemplate;

		List<TransformNode> nodes = new ArrayList<TransformNode>();
		List<String> nodeIds = new ArrayList<String>();
		List<TransformLink> links = new ArrayList<TransformLink>();

		/*
		 * We need to create a list of nodes with unique concepts(merge nodes)
		 * and gather all links associated to all nodes
		 */
		if (listOfNodeList != null && !listOfNodeList.isEmpty())
			for (List<TransformNode> nodeList : listOfNodeList)
				for (TransformNode node : nodeList)
					createList(node, nodes, links, nodeIds, isMergeDuplicateNodes);

		/*
		 * We need to rearrange links to point to the concept they are related
		 * to, as redundant concept nodes are merged.
		 */
		if(isMergeDuplicateNodes) {
			realignLinks(nodes, links);
		}
		
		try {
				template = engine.getTemplate("transformation/xgmml-time.vm");
		} catch (ResourceNotFoundException e) {
			throw new QuadrigaGeneratorException(e);
		} catch (ParseErrorException e) {
		    throw new QuadrigaGeneratorException(e);
		} catch (Exception e) {
		    throw new QuadrigaGeneratorException(e);
		}
		
		VelocityContext context = new VelocityContext();
		context.put("nodes", nodes);
		context.put("links", links);
		StringWriter writer = new StringWriter();

		if (template != null) {
			try {
				template.merge(context, writer);
			} catch (ResourceNotFoundException e) {
			    throw new QuadrigaGeneratorException(e);
			} catch (ParseErrorException e) {
			    throw new QuadrigaGeneratorException(e);
			} catch (MethodInvocationException e) {
			    throw new QuadrigaGeneratorException(e);
			} catch (Exception e) {
			    throw new QuadrigaGeneratorException(e);
			}
		}
		return writer.toString();
	}

	/**
	 * This method realigns link to point to the
	 *         relevant concept, as redundant concepts are merged to one node
	 *         per concept .
	 *         
	 * @author Prajakta Samant 
	 * 
	 * @param nodes
	 * @param links
	 */
	private void realignLinks(List<TransformNode> nodes,
			List<TransformLink> links) {

		if (links != null) {
			for (TransformLink link : links) {

				if (link.getSubject() != null) {
					for (TransformNode node : nodes) {
						if (link.getSubject().getConcept() != null && link.getSubject().getConcept()
								.equals(node.getConcept())) {
							link.setSubject(node);
							break;
						}
					}
				}

				if (link.getObject() != null) {
					for (TransformNode node : nodes) {
						if (link.getObject().getConcept() != null && link.getObject().getConcept()
								.equals(node.getConcept())) {
							link.setObject(node);
							break;
						}
					}
				}

			}
		}

	}

	/**
	 * @author Prajakta Samant Create a list of nodes of unique concepts and
	 *         list of all links.
	 * @param node
	 * @param nodeList
	 * @param links
	 * @param existingIds
	 */
	protected void createList(TransformNode node, List<TransformNode> nodeList,
			List<TransformLink> links, List<String> existingIds, boolean mergeConcepts) {

		if (node != null) {
			/*
			 * Add node to the list only if it has a new concept. We are
			 * maintaining nodelist of unique concepts
			 */
			String id = node.getId();
			if (mergeConcepts)
				id = node.getConcept();
			
			if (!existingIds.contains(id)) {
				nodeList.add(node);
				existingIds.add(id);
			}

			/*
			 * Add all links to the TransformLink list, even those of repeated
			 * nodes.
			 */
			if (node.getLinks() != null && connector != null) {
				for (TransformLink link : node.getLinks()) {
					
					if (links.contains(link))
						continue;
					
					if (link.getOccurTime() != null) {
						IConcept concept = connector.getConceptByUri(link
								.getOccurTime());
						if(concept!=null)
							link.setOccurTime(concept.getWord());
					}
					
					if (link.getStartTime() != null) {
					    IConcept concept = connector.getConceptByUri(link
								.getStartTime());
						if(concept!=null) {
						    link.setStartTime(concept.getWord());
						}
					}
					
					if (link.getEndTime() != null) {
					    IConcept concept = connector.getConceptByUri(link
								.getEndTime());
						if(concept!=null) {
						    link.setEndTime(concept.getWord());
						}
					}
					
					if (link.getPlace() != null) {
					    IConcept concept = connector.getConceptByUri(link
								.getPlace());
						if(concept!=null) {
						    link.setPlace(concept.getWord());
						}
					}
					
					links.add(link);
					createList(link.getObject(), nodeList, links, existingIds, mergeConcepts);
				}
			}
		}
	}

}
