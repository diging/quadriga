package edu.asu.spring.quadriga.profile.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.conceptpower.POS;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.impl.ConceptPowerService;
import edu.asu.spring.quadriga.profile.impl.SearchResult;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.conceptcollection.impl.ConceptCollectionManager;

//@RunWith(MockitoJUnitRunner.class)
public class ConceptPowerServiceTest {

    @Mock
    private IConceptCollectionManager mockedCollectionManager;
    
    @InjectMocks
    private ConceptPowerService conceptPowerService;

    
    private String rhino = "rhino";
    private ConceptpowerReply reply;

    @Before
    public void setUp() throws Exception {
        mockedCollectionManager = Mockito.mock(ConceptCollectionManager.class);
        MockitoAnnotations.initMocks(this);
        
        ConceptpowerReply.ConceptEntry entry = new ConceptpowerReply.ConceptEntry();
        entry.setDescription("massive powerful herbivorous odd-toed ungulate of southeast Asia and"
                + "Africa having very thick skin and one or two horns on the snout");
        entry.setId("WID-02391994-N-02-rhino");
        entry.setLemma("rhino");

        List<ConceptpowerReply.ConceptEntry> entries = new ArrayList<ConceptpowerReply.ConceptEntry>();
        entries.add(entry);

        reply = new ConceptpowerReply();
        reply.setConceptEntry(entries);
        
        Mockito.when(mockedCollectionManager.search(rhino, POS.NOUN)).thenReturn(reply);
    }

    @Test
    public void testSearch() {
       // assertEquals("welt", conceptPowerService.test());

        List<ISearchResult> expectedSearchResultList = new ArrayList<ISearchResult>();
        ISearchResult searchResult = new SearchResult();
        searchResult
                .setDescription("massive powerful herbivorous odd-toed ungulate of southeast Asia and"
                        + "Africa having very thick skin and one or two horns on the snout");
        searchResult.setId("WID-02391994-N-02-rhino");
        searchResult.setName(rhino);
        expectedSearchResultList.add(searchResult);

        List<ISearchResult> searchResultList = conceptPowerService
                .search(rhino);

        assertEquals(expectedSearchResultList.size(), searchResultList.size());
    }

}
