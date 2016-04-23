package edu.asu.spring.quadriga.service.publicwebsite.impl;

import static org.junit.Assert.*;

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

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.impl.ConceptStats;
import edu.asu.spring.quadriga.domain.impl.networks.Network;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Node;

public class ProjectStatsTest {

    @Mock
    private INetworkTransformationManager mockedTransformationManager = Mockito
            .mock(INetworkTransformationManager.class);

    @InjectMocks
    private ProjectStats projectStatsUnderTest;

    private List<IConceptStats> mockedConceptStatsList;

    private List<INetwork> mockedNetworkList;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);

        Network network1 = new Network();
        network1.setNetworkId("id");
        network1.setNetworkName("test network");

        Network network2 = new Network();
        network2.setNetworkId("id2");
        network2.setNetworkName("test network2");

        mockedNetworkList = new ArrayList<INetwork>();
        mockedNetworkList.add(network1);
        mockedNetworkList.add(network2);

        Map<String, Node> nodes = new HashMap<String, Node>();
        Node n1 = new Node();
        n1.setConceptId("url1");
        Node n2 = new Node();
        n2.setConceptId("url1");
        nodes.put("1", n1);
        nodes.put("2", n2);

        TransformedNetwork transformedNetwork1 = new TransformedNetwork(nodes,
                null);

        Map<String, Node> nodes2 = new HashMap<String, Node>();
        Node nd1 = new Node();
        nd1.setConceptId("url1");
        Node nd2 = new Node();
        nd2.setConceptId("url2");
        nodes.put("1", nd1);
        nodes.put("2", nd2);

        TransformedNetwork transformedNetwork2 = new TransformedNetwork(nodes2,
                null);

        ConceptStats cs1 = new ConceptStats("url1", null, null, 3);
        ConceptStats cs2 = new ConceptStats("url2", null, null, 1);

        mockedConceptStatsList = new ArrayList<IConceptStats>();
        mockedConceptStatsList.add(cs1);
        mockedConceptStatsList.add(cs2);

        Mockito.when(mockedTransformationManager.getTransformedNetwork("id"))
                .thenReturn(transformedNetwork1);
        Mockito.when(mockedTransformationManager.getTransformedNetwork("id2"))
                .thenReturn(transformedNetwork2);
    }

    @Test
    public void getConceptCountTest() throws QuadrigaStorageException {

        List<IConceptStats> conceptStatsList = projectStatsUnderTest
                .getConceptCount(mockedNetworkList);
        assertTrue(conceptStatsList.containsAll(mockedConceptStatsList)
                && mockedConceptStatsList.containsAll(conceptStatsList));

    }

    @Test
    public void getSortedConceptCountTest() throws QuadrigaStorageException {
        assertEquals(mockedConceptStatsList,
                projectStatsUnderTest.getConceptCount(mockedNetworkList));
    }
}
