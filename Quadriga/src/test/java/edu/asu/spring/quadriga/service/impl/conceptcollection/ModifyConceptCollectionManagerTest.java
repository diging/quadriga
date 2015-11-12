package edu.asu.spring.quadriga.service.impl.conceptcollection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class ModifyConceptCollectionManagerTest {

	@Mock
	private IConceptCollectionDAO mockedccDao = Mockito.mock(IConceptCollectionDAO.class);

	@InjectMocks
	ModifyConceptCollectionManager modifyConceptCollectionManagerUnderTest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void updateCollectionDetailsTest() throws QuadrigaStorageException {
		ConceptCollectionDTO conceptCollection = new ConceptCollectionDTO();

		ConceptCollection collection = new ConceptCollection();
		collection.setConceptCollectionName("conceptCollectionName");

		Mockito.when(mockedccDao.getDTO(Matchers.anyString())).thenReturn(conceptCollection);
		modifyConceptCollectionManagerUnderTest.updateCollectionDetails(collection, "username");

		assertEquals("conceptCollectionName", collection.getConceptCollectionName());

	}

}
