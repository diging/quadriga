package edu.asu.spring.quadriga.service.conceptcollection.mapper.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionConceptFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.Concept;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTOPK;
import edu.asu.spring.quadriga.dto.ConceptsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceCCShallowMapper;

public class ConceptCollectionDeepMapperTest {

	@Mock
	private IConceptCollectionDAO mockeddbConnect = Mockito.mock(IConceptCollectionDAO.class);

	@Mock
	private IConceptCollectionFactory mockedccFactory = Mockito.mock(IConceptCollectionFactory.class);

	@Mock
	private IUserDeepMapper mockedUserDeepMapper = Mockito.mock(IUserDeepMapper.class);

	@Mock
	private IProjectConceptCollectionShallowMapper mockedProjectCCShallowMapper = Mockito
			.mock(IProjectConceptCollectionShallowMapper.class);

	@Mock
	private IWorkspaceCCShallowMapper mockedWorkspaceCCShallowMapper = Mockito.mock(IWorkspaceCCShallowMapper.class);

	@Mock
	private IQuadrigaRoleManager mockedRoleManager = Mockito.mock(IQuadrigaRoleManager.class);

	@Mock
	private ICollaboratorFactory mockedCollaboratorFactory = Mockito.mock(ICollaboratorFactory.class);

	@Mock
	private IConceptCollectionCollaboratorFactory mockedccCollaboratorFactory = Mockito
			.mock(IConceptCollectionCollaboratorFactory.class);

	@Mock
	private IConceptFactory mockedConceptFactory = Mockito.mock(IConceptFactory.class);

	@Mock
	private IConceptCollectionConceptFactory mockedccConceptsFactory = Mockito
			.mock(IConceptCollectionConceptFactory.class);

	@InjectMocks
	ConceptCollectionDeepMapper conceptCollectionDeepMapperUnderTest;

	private ConceptCollectionDTO ccDTO;

	private User user;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		QuadrigaUserDTO dto = new QuadrigaUserDTO();
		dto.setUsername("test name");
		ccDTO = new ConceptCollectionDTO();
		ccDTO.setConceptCollectionid("conceptCollectionid");
		ccDTO.setCollectionname("collectionname");
		ccDTO.setCreatedby("createdby");
		ccDTO.setCollectionowner(dto);

		user = new User();
		user.setName("name");
		user.setEmail("test@gmail.com");
		user.setUserName("username");
	}

	@Test
	public void getConceptCollectionDetailsTest() throws QuadrigaStorageException {

		ConceptsDTO conceptDTO = new ConceptsDTO();
		conceptDTO.setLemma("lemma");

		ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK = new ConceptCollectionItemsDTOPK();
		conceptCollectionItemsDTOPK.setConcept("concept");

		ConceptCollectionItemsDTO ccItemsDTO = new ConceptCollectionItemsDTO();
		ccItemsDTO.setConceptCollectionItemsDTOPK(conceptCollectionItemsDTOPK);

		ConceptCollectionItemsDTO collectionItemsDTO = new ConceptCollectionItemsDTO();
		collectionItemsDTO.setConceptcollectionsItemsDTOPK(conceptCollectionItemsDTOPK);
		collectionItemsDTO.setConceptDTO(conceptDTO);
		ccItemsDTO.setConceptcollectionsItemsDTOPK(conceptCollectionItemsDTOPK);

		List<ConceptCollectionItemsDTO> conceptCollectionItemsDTOList = new ArrayList<ConceptCollectionItemsDTO>();
		conceptCollectionItemsDTOList.add(collectionItemsDTO);

		ccDTO.setConceptCollectionItemsDTOList(conceptCollectionItemsDTOList);

		setMockConditionsCollectionDetailsTest(ccDTO);

		IConceptCollection col = conceptCollectionDeepMapperUnderTest.getConceptCollectionDetails("ccId");

		assertEquals("conceptCollectionid", col.getConceptCollectionId());
		assertEquals("collectionname", col.getConceptCollectionName());
		assertEquals("test@gmail.com", col.getOwner().getEmail());
		assertEquals("username", col.getOwner().getUserName());
		assertEquals("lemma", col.getConceptCollectionConcepts().get(0).getConcept().getLemma());
		assertEquals("concept", col.getConceptCollectionConcepts().get(0).getConcept().getConceptId());

	}

	@Test
	public void getConceptCollectionDetailsNullTest() throws QuadrigaStorageException {
		Mockito.when(mockeddbConnect.getDTO(Matchers.anyString())).thenReturn(null);
		IConceptCollection col = conceptCollectionDeepMapperUnderTest.getConceptCollectionDetails("ccId");
		assertNull(col);
	}

	private void setMockConditionsCollectionDetailsTest(ConceptCollectionDTO ccDTO) throws QuadrigaStorageException {

		Mockito.when(mockedccFactory.createConceptCollectionObject()).thenReturn(new ConceptCollection());
		Mockito.when(mockeddbConnect.getDTO(Matchers.anyString())).thenReturn(ccDTO);
		Mockito.when(mockedUserDeepMapper.getUser(Matchers.anyString())).thenReturn(user);
		Mockito.when(mockedProjectCCShallowMapper.getProjectConceptCollectionList(
				Matchers.any(ConceptCollectionDTO.class), Matchers.any(IConceptCollection.class)))
				.thenReturn(new ArrayList<IProjectConceptCollection>());
		Mockito.when(mockedWorkspaceCCShallowMapper.getWorkspaceConceptCollectionList(
				Matchers.any(IConceptCollection.class), Matchers.any(ConceptCollectionDTO.class)))
				.thenReturn(new ArrayList<IWorkspaceConceptCollection>());
		Mockito.when(mockedConceptFactory.createConceptObject()).thenReturn(new Concept());
		Mockito.when(mockedccConceptsFactory.createConceptCollectionConceptsObject())
				.thenReturn(new ConceptCollectionConcepts());
	}

	@Test
	public void getConceptCollectionCollaboratorListTest() throws QuadrigaStorageException {
		QuadrigaUserDTO quadrigaUserDTO = new QuadrigaUserDTO();
		quadrigaUserDTO.setUsername("username");

		ConceptCollectionCollaboratorDTOPK collabDTOPK = new ConceptCollectionCollaboratorDTOPK();
		collabDTOPK.setCollaboratorrole("role");

		QuadrigaUserDTO quadrigaUserDTO2 = new QuadrigaUserDTO();
		quadrigaUserDTO2.setUsername("username2");

		ConceptCollectionCollaboratorDTOPK collabDTOPK2 = new ConceptCollectionCollaboratorDTOPK();
		collabDTOPK2.setCollaboratorrole("role2");

		QuadrigaUserDTO quadrigaUserDTO3 = new QuadrigaUserDTO();
		quadrigaUserDTO3.setUsername("username");

		ConceptCollectionCollaboratorDTOPK collabDTOPK3 = new ConceptCollectionCollaboratorDTOPK();
		collabDTOPK3.setCollaboratorrole("role3");

		List<ConceptCollectionCollaboratorDTO> collabList = new ArrayList<ConceptCollectionCollaboratorDTO>();

		ConceptCollectionCollaboratorDTO collaboratorDTO = new ConceptCollectionCollaboratorDTO();
		collaboratorDTO.setQuadrigaUserDTO(quadrigaUserDTO);
		collaboratorDTO.setCollaboratorDTOPK(collabDTOPK);
		collaboratorDTO.setUpdateddate(new Date());

		ConceptCollectionCollaboratorDTO collaboratorDTO2 = new ConceptCollectionCollaboratorDTO();
		collaboratorDTO2.setQuadrigaUserDTO(quadrigaUserDTO2);
		collaboratorDTO2.setCollaboratorDTOPK(collabDTOPK2);
		collaboratorDTO2.setUpdateddate(new Date());

		ConceptCollectionCollaboratorDTO collaboratorDTO3 = new ConceptCollectionCollaboratorDTO();
		collaboratorDTO3.setQuadrigaUserDTO(quadrigaUserDTO3);
		collaboratorDTO3.setCollaboratorDTOPK(collabDTOPK3);
		collaboratorDTO3.setUpdateddate(new Date());

		collabList.add(collaboratorDTO);
		collabList.add(collaboratorDTO2);
		collabList.add(collaboratorDTO3);

		QuadrigaRole collaboratorRole = new QuadrigaRole();
		collaboratorRole.setId("id");
		setMockConditionsCollaboratorListTest(collaboratorRole);

		ccDTO.setConceptCollectionCollaboratorDTOList(collabList);
		ConceptCollection conceptCollection = new ConceptCollection();
		conceptCollection.setConceptCollectionId(ccDTO.getConceptCollectionid());
		conceptCollection.setConceptCollectionName(ccDTO.getCollectionname());
		conceptCollection.setDescription(ccDTO.getDescription());
		conceptCollection.setCreatedBy(ccDTO.getCreatedby());
		conceptCollection.setCreatedDate(ccDTO.getCreateddate());
		conceptCollection.setUpdatedBy(ccDTO.getUpdatedby());
		conceptCollection.setUpdatedDate(ccDTO.getUpdateddate());
		conceptCollection.setOwner(user);

		List<IConceptCollectionCollaborator> collectionCollaborators = conceptCollectionDeepMapperUnderTest
				.getConceptCollectionCollaboratorList(ccDTO, conceptCollection);

		// since we have 2 users - username and username2
		assertEquals(2, collectionCollaborators.size());

		assertEquals("id", collectionCollaborators.get(0).getCollaborator().getCollaboratorRoles().get(0).getId());
		assertEquals("createdby", collectionCollaborators.get(0).getConceptCollection().getCreatedBy());
		assertEquals("conceptCollectionid",
				collectionCollaborators.get(0).getConceptCollection().getConceptCollectionId());
		assertEquals("test@gmail.com", collectionCollaborators.get(0).getConceptCollection().getOwner().getEmail());

	}

	private void setMockConditionsCollaboratorListTest(QuadrigaRole collaboratorRole) throws QuadrigaStorageException {

		Mockito.when(mockedRoleManager.getQuadrigaRoleByDbId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(collaboratorRole);
		Mockito.doNothing().when(mockedRoleManager).fillQuadrigaRole(Matchers.anyString(),
				Matchers.any(IQuadrigaRole.class));
		Mockito.when(mockedCollaboratorFactory.createCollaborator()).thenReturn(new Collaborator());
		Mockito.when(mockedccCollaboratorFactory.createConceptCollectionCollaboratorObject())
				.thenReturn(new ConceptCollectionCollaborator());
	}

}
