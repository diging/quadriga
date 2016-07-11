package edu.asu.spring.quadriga.service.conceptcollection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.impl.ModifyConceptCollectionManager;

public class ModifyConceptCollectionManagerTest {

    @Mock
    private IConceptCollectionDAO mockedccDao = Mockito.mock(IConceptCollectionDAO.class);

    @InjectMocks
    private ModifyConceptCollectionManager modifyConceptCollectionManagerUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateCollectionDetailsTest() throws QuadrigaStorageException {
        ConceptCollectionDTO conceptCollection = new ConceptCollectionDTO();

        ConceptCollection collection = new ConceptCollection();
        collection.setConceptCollectionName("conceptCollectionName");
        collection.setConceptCollectionId("id");

        Mockito.when(mockedccDao.getDTO("id")).thenReturn(conceptCollection);
        modifyConceptCollectionManagerUnderTest.updateCollectionDetails(collection, "username");

        assertEquals("conceptCollectionName", collection.getConceptCollectionName());

    }

}
