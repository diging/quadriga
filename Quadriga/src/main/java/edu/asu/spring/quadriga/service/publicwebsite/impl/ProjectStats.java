package edu.asu.spring.quadriga.service.publicwebsite.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.impl.ConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.publicwebsite.IProjectStats;
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
    public List<IConceptStats> getTopConcepts(List<INetwork> networks)
            throws QuadrigaStorageException {
        HashMap<String, ConceptStats> mapStats = new HashMap<String, ConceptStats>();

        for (INetwork n : networks) {
            ITransformedNetwork transformedNetwork = transformationManager
                    .getTransformedNetwork(n.getNetworkId());
            if (transformedNetwork != null) {
                Map<String, Node> mapNodes = transformedNetwork.getNodes();
                for (Node node : mapNodes.values()) {
                    String url = node.getConceptId();
                    if (mapStats.containsKey(url)) {
                        mapStats.get(url).incCount();
                    } else {
                        ConceptStats conceptStats = new ConceptStats(url,
                                node.getDescription(), node.getLabel(), 1);
                        mapStats.put(url, conceptStats);
                    }
                }
            }
        }

        List<IConceptStats> valuesList = new ArrayList<IConceptStats>(
                mapStats.values());

        return getSortedList(valuesList);

    }

    public List<IConceptStats> getSortedList(List<IConceptStats> csList) {
        Collections.sort(csList, new Comparator<IConceptStats>() {
            public int compare(IConceptStats o1, IConceptStats o2) {
                if (o1.getCount() == o2.getCount())
                    return 0;
                return o1.getCount() < o2.getCount() ? 1 : -1;
            }
        });
        return csList;
    }
}
