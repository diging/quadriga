package edu.asu.spring.quadriga.service.impl.conceptcollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.login.RoleNames;


public class CCCollaboratorManagerTest {
	
	@Mock
	private IConceptCollectionCollaboratorDAO mockedCcCollabDao;
	
	@Mock
	private IConceptCollectionDAO mockedCcDao;
	
	@InjectMocks
	private CCCollaboratorManager ccCollabManagerUnderTest;

	private ConceptCollectionDTO ccDto;
	
	@Before
    public void setUp() throws Exception {
		mockedCcCollabDao =  Mockito.mock(IConceptCollectionCollaboratorDAO.class);
		mockedCcDao = Mockito.mock(IConceptCollectionDAO.class);
        MockitoAnnotations.initMocks(this);
        
        ccDto = new ConceptCollectionDTO();
        ccDto.setConceptCollectionid("CC1");
        ccDto.setConceptCollectionCollaboratorDTOList(new ArrayList<ConceptCollectionCollaboratorDTO>());
        
        QuadrigaUserDTO userDto = new QuadrigaUserDTO();
        userDto.setUsername("User1");
        Mockito.when(mockedCcDao.getDTO("CC1")).thenReturn(ccDto);
        Mockito.when(mockedCcDao.getUserDTO("User1")).thenReturn(userDto);
    }

	@Test
	public void testCreateNewCollaboratorDTO() {
		ConceptCollectionCollaboratorDTO dto = ccCollabManagerUnderTest.createNewCollaboratorDTO();
		assertNotNull(dto);
	}
	
	@Test
	public void testCreateNewCollaboratorDTOPK() {
		ConceptCollectionCollaboratorDTOPK dtopk = ccCollabManagerUnderTest.createNewCollaboratorDTOPK("Id", "user", "role");
		assertNotNull(dtopk);
		assertEquals("Id", dtopk.getConceptcollectionid());
		assertEquals("user", dtopk.getCollaboratoruser());
		assertEquals("role", dtopk.getCollaboratorrole());
	}
	
	@Test
	public void testAddingFirstCollaborator() throws QuadrigaStorageException {
		ccCollabManagerUnderTest.updateCollaborators("CC1", "User1", RoleNames.ROLE_CC_COLLABORATOR_ADMIN, "admin");
		
		List<ConceptCollectionCollaboratorDTO> collaborators = ccDto.getCollaboratorList();
		assertEquals(1, collaborators.size());
		
		ConceptCollectionCollaboratorDTO collaborator = collaborators.get(0);
		assertEquals("User1", collaborator.getCollaboratorDTOPK().getCollaboratoruser());
		assertEquals(RoleNames.ROLE_CC_COLLABORATOR_ADMIN, collaborator.getCollaboratorDTOPK().getCollaboratorrole());
		assertEquals("admin", collaborator.getCreatedby());
	}
	
	@Test
	public void testAddingRoleToCollaborator() throws QuadrigaStorageException {
		ConceptCollectionCollaboratorDTO collabDto = new ConceptCollectionCollaboratorDTO();
		collabDto.setCollaboratorDTOPK(new ConceptCollectionCollaboratorDTOPK("CC1", "User1", RoleNames.ROLE_CC_COLLABORATOR_ADMIN));
		
		ccCollabManagerUnderTest.updateCollaborators("CC1", "User1", RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE + "," + RoleNames.ROLE_CC_COLLABORATOR_ADMIN, "admin");
		
		List<ConceptCollectionCollaboratorDTO> collaborators = ccDto.getCollaboratorList();
		assertEquals(2, collaborators.size());
		
		List<String> roles = new ArrayList<String>();
		for (ConceptCollectionCollaboratorDTO collab : collaborators) {
			assertEquals("User1", collab.getCollaboratorDTOPK().getCollaboratoruser());
			assertEquals("admin", collab.getCreatedby());
			roles.add(collab.getCollaboratorDTOPK().getCollaboratorrole());
		}
		
		assertTrue(roles.contains(RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE));
		assertTrue(roles.contains(RoleNames.ROLE_CC_COLLABORATOR_ADMIN));
	}
}
