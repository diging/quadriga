package edu.asu.spring.quadriga.conceptpower.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;

public class ConceptpowerConnectorTest {

    @Mock
    private RestTemplate mockedRestTemplate = Mockito.mock(RestTemplate.class);

    private String conceptURL = "conceptURL";

    private String searchURL = "searchURL";

    private String idURL = "idUrl";

    @InjectMocks
    private IConceptpowerConnector connectorUnderTest;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(connectorUnderTest, "conceptURL",
                conceptURL);
        ReflectionTestUtils
                .setField(connectorUnderTest, "searchURL", searchURL);
        ReflectionTestUtils.setField(connectorUnderTest, "idUrl", idURL);

        List<ConceptpowerReply.ConceptEntry> conceptEntries = new ArrayList<ConceptpowerReply.ConceptEntry>();
        ConceptpowerReply.ConceptEntry entry = new ConceptpowerReply.ConceptEntry();
        entry.setLemma("dog");
        entry.setPos("noun");
        conceptEntries.add(entry);
        ConceptpowerReply rep = new ConceptpowerReply();
        rep.setConceptEntry(conceptEntries);

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("name", "dog");
        vars.put("pos", "noun");

        Mockito.when(
                mockedRestTemplate.getForObject(
                        Matchers.eq(conceptURL + searchURL + "{name}/{pos}"),
                        Matchers.eq(ConceptpowerReply.class),
                        Mockito.argThat(new MapMatcher(vars)))).thenReturn(rep);

        List<ConceptpowerReply.ConceptEntry> emptyList = new ArrayList<ConceptpowerReply.ConceptEntry>();
        ConceptpowerReply noResultsReply = new ConceptpowerReply();
        noResultsReply.setConceptEntry(emptyList);

        Map<String, String> vars2 = new HashMap<String, String>();
        vars2.put("name", "cat");
        vars2.put("pos", "noun");

        Mockito.when(
                mockedRestTemplate.getForObject(
                        Matchers.eq(conceptURL + searchURL + "{name}/{pos}"),
                        Matchers.eq(ConceptpowerReply.class),
                        Mockito.argThat(new MapMatcher(vars2)))).thenReturn(
                noResultsReply);

        Mockito.when(
                mockedRestTemplate.getForObject(Matchers.eq(conceptURL + idURL
                        + "id"), Matchers.eq(ConceptpowerReply.class), Mockito
                        .argThat(new MapMatcher(new HashMap<String, String>()))))
                .thenReturn(rep);

        Mockito.when(
                mockedRestTemplate.getForObject(Matchers.eq(conceptURL + idURL
                        + "id2"), Matchers.eq(ConceptpowerReply.class), Mockito
                        .argThat(new MapMatcher(new HashMap<String, String>()))))
                .thenReturn(noResultsReply);
    }

    @Test
    public void testSearch() {
        ConceptpowerReply reply = connectorUnderTest.search("dog", "noun");
        assertNotNull(reply);
        assertEquals(1, reply.getConceptEntry().size());
        assertEquals("dog", reply.getConceptEntry().get(0).getLemma());
        assertEquals("noun", reply.getConceptEntry().get(0).getPos());
    }

    @Test
    public void testSearchNoResults() {
        ConceptpowerReply reply = connectorUnderTest.search("cat", "noun");
        assertNotNull(reply);
        assertEquals(0, reply.getConceptEntry().size());
    }

    @Test
    public void testGetById() {
        ConceptpowerReply reply = connectorUnderTest.getById("id");
        assertNotNull(reply);
        assertEquals(1, reply.getConceptEntry().size());
        assertEquals("dog", reply.getConceptEntry().get(0).getLemma());
        assertEquals("noun", reply.getConceptEntry().get(0).getPos());
    }

    @Test
    public void testGetByIdNoResult() {
        ConceptpowerReply reply = connectorUnderTest.getById("id2");
        assertNotNull(reply);
        assertEquals(0, reply.getConceptEntry().size());
    }

    class MapMatcher extends ArgumentMatcher<Map> {

        Map<String, String> mapToMatch;

        public MapMatcher(Map<String, String> mapToMatch) {
            this.mapToMatch = mapToMatch;
        }

        @Override
        public boolean matches(Object argument) {
            if (!(argument instanceof Map)) {
                return false;
            }

            if (((Map) argument).size() != mapToMatch.size()) {
                return false;
            }

            for (String key : mapToMatch.keySet()) {
                if (!mapToMatch.get(key).equals(((Map) argument).get(key))) {
                    return false;
                }
            }
            return true;
        }

    }
}
