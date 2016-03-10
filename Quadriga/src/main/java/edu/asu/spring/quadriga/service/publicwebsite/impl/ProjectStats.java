package edu.asu.spring.quadriga.service.publicwebsite.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.impl.ConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.publicwebsite.IProjectStats;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

/**
 * This class represents business logic for getting top concepts from the given
 * list of networks.
 *
 * @author ajaymodi
 *
 */

@Service
public class ProjectStats implements IProjectStats {

	@Autowired
	private INetworkTransformationManager transformationManager;

	@Override
	public List<IConceptStats> getTopConcepts(List<INetwork> Networks)
	        throws QuadrigaStorageException {
		HashMap<String, ConceptStats> cStats = new HashMap<String, ConceptStats>();
		ConceptStats cs = new ConceptStats();

		for (INetwork n : Networks) {
			ITransformedNetwork transformedNetwork = transformationManager
			        .getTransformedNetwork(n.getNetworkId());
			if (transformedNetwork != null) {
				List<Link> links = transformedNetwork.getLinks();
				for (Link l : links) {
					Node s = l.getSubject();
					String url = s.getConceptId();
					if (cStats.containsKey(url)) {
						cStats.get(url)
						        .setCount(cStats.get(url).getCount() + 1);
					} else {
						ConceptStats c = new ConceptStats(url,
						        s.getDescription(), s.getLabel(), 1);
						cStats.put(url, c);
					}
				}
			}
		}

		List<IConceptStats> values = new ArrayList<IConceptStats>(
		        cStats.values());

		return cs.getSortedList(values);

	}
}
