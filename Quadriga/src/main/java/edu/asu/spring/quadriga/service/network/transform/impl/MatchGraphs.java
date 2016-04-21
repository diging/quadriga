package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.conceptpower.impl.ConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;

/**
 * This class matches multiple original file and
 * the mapping file to find matching graph amongst them.
 *         
 * @author Prajakta Sudhir Samant 
 */
/**
 * @author Kanchan
 * 
 */
public class MatchGraphs {

    @Autowired
	private PatternFinder finder;
    
    @Autowired
    private ConceptpowerConnector cpConnector;
	
	
	/**
	 * This method takes the pair of original and mapping file and finds the
	 * matching graph between them.
	 * 
	 * @param fileMapping
	 * @return turtle which is the matched graph result
	 */
	public String matchGraphs(List<ITransformation> fileMapping,
			List<CreationEvent> events) {

		List<List<TransformNode>>nodesList = new ArrayList<List<TransformNode>>();

		if (fileMapping != null && events != null) {
			for (ITransformation m : fileMapping) {
				GraphMapper mapper = new GraphMapper();
				EventGraphMapper eventMapper = new EventGraphMapper(cpConnector);
				List<TransformNode> nodes = new ArrayList<TransformNode>();
				List<Node> foundNodes = new ArrayList<Node>();
				String patternFilePath = m.getPatternFilePath();
				mapper.createGraph(patternFilePath);
				
				/*
				 * Get the Path provided by user, to retrieve Concepts, from the
				 * select Page
				 */
				eventMapper.buildGraphs(events);
				totalNodes = eventMapper.getStartNodes().size();
				foundNodes = finder.findPattern(eventMapper.getStartNodes(),
						mapper.getStartNode());
				String mappingFilePath = ResourceProvider.getWorkspacePath()
						+ m.getMappingFile()
								.replace(
										("." + FileConstants.GRAPHML_FILEEXTENSION),
										"_"
												+ (m.getMappingKey().substring(
														m.getMappingKey()
																.length() - 4)
														+ "." + FileConstants.GRAPHML_FILEEXTENSION));
				mapper.createMapping(mappingFilePath);

				nodes = transformer.transform(foundNodes,
						mapper.getStartNodeOfMapping());
				
				if (nodes != null && !nodes.isEmpty())
					nodesList.add(nodes);

			}

			
			/*
			 * Since we have our nodes ready, whichever fields are null just set
			 * them as empty strings so that it shows empty in the output
			 */
			for (int i = 0; i < nodesList.size(); i++) {
				List<TransformNode> nodes = nodesList.get(i);
				for (int j = 0; j < nodes.size(); j++) {
					TransformNode node = nodes.get(j);

					if (node.getConcept() == null) {
						node.setConcept("");
					}

					if (node.getCorrespondingId() == null) {
						node.setCorrespondingId("");
					}

					if (node.getType() == null) {
						node.setType("");
					}

					List<Term> terms = node.getTerms();
					if (terms != null) {
						for (int k = 0; k < terms.size(); k++) {
							Term term = terms.get(k);
							if (term.getSourceUri() == null) {
								term.setSourceUri("");
							}

							List<TermPart> termparts = term.getTermParts();
							if (termparts != null) {
								for (int l = 0; l < termparts.size(); l++) {
									TermPart termpart = termparts.get(l);
									if (termpart.getExpression() == null) {
										termpart.setExpression("");
									}
								}
							}
						}
					}
				}
			}
			/*
			 * To transform we pass the Transform nodes and the selected Concept
			 * URL path and the template to which result is to map.
			 */
			turtle = transformer.getXGMML(nodesList,
					selectPage.getConceptPath(), selectPage.getTemplatePath(),
					isMergeDuplicateNodes);

		}
		return turtle;
	}
	

	/**
	 * The result of match are nodes in graph found common.
	 * 
	 * @return size of matching nodes found
	 */
	public int getFoundNodes() {
		int size = 0;
		if (nodesList != null) {
			for (List<TransformNode> nodes : nodesList) {
				size = size + nodes.size();
			}
		}
		return size;
	}

	/**
	 * @return total number of nodes to match pattern with
	 */
	public int getTotalNodes() {

		return totalNodes;
	}

}
