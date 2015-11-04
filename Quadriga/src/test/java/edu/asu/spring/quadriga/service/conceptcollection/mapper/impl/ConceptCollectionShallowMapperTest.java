package edu.asu.spring.quadriga.service.conceptcollection.mapper.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

public class ConceptCollectionShallowMapperTest {

	@Mock
	private IConceptCollectionDAO mockeddbConnect = Mockito.mock(IConceptCollectionDAO.class);

	@Mock
	private IConceptCollectionManager mockedccManager = Mockito.mock(IConceptCollectionManager.class);

	@Mock
	private IUserDeepMapper mockedUserDeepMapper = Mockito.mock(IUserDeepMapper.class);

	@InjectMocks
	ConceptCollectionShallowMapper conceptCollectionShallowMapperUnderTest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getConceptCollectionListTest() throws QuadrigaStorageException {
		QuadrigaUserDTO userDTO = new QuadrigaUserDTO();
		userDTO.setUsername("username");
		ConceptCollectionDTO dto = new ConceptCollectionDTO();
		dto.setCollectionname("collectionname");
		dto.setOwner(userDTO);

		QuadrigaUserDTO userDTO2 = new QuadrigaUserDTO();
		userDTO2.setUsername("username2");
		ConceptCollectionDTO dto2 = new ConceptCollectionDTO();
		dto2.setCollectionname("collectionname2");
		dto2.setOwner(userDTO2);

		List<ConceptCollectionDTO> ccDTOList = new ArrayList<ConceptCollectionDTO>();
		ccDTOList.add(dto);
		ccDTOList.add(dto2);

		User user = new User();
		user.setUserName("username");

		User user2 = new User();
		user2.setUserName("username2");

		Mockito.when(mockeddbConnect.getConceptsOwnedbyUser(Matchers.anyString())).thenReturn(ccDTOList);
		Mockito.when(mockedUserDeepMapper.getUser("username")).thenReturn(user);
		Mockito.when(mockedUserDeepMapper.getUser("username2")).thenReturn(user2);

		List<IConceptCollection> ccProxyList = conceptCollectionShallowMapperUnderTest
				.getConceptCollectionList("username");

		assertEquals(2, ccProxyList.size());
		assertEquals("username", ccProxyList.get(0).getOwner().getUserName());
		assertEquals("collectionname", ccProxyList.get(0).getConceptCollectionName());

		assertEquals("username2", ccProxyList.get(1).getOwner().getUserName());
		assertEquals("collectionname2", ccProxyList.get(1).getConceptCollectionName());

	}
}
