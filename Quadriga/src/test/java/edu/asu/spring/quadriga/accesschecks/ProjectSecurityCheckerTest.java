package edu.asu.spring.quadriga.accesschecks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.accesschecks.impl.ProjectSecurityChecker;
import edu.asu.spring.quadriga.dao.workbench.IProjectAccessDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.impl.ProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;

public class ProjectSecurityCheckerTest {

    @Mock
    private IProjectAccessDAO mockedManager;

    @InjectMocks
    private ProjectSecurityChecker securityChecker;

    @Mock
    private IProjectCollaboratorManager mockedProjectManager;

    @Before
    public void init() throws QuadrigaStorageException {
        mockedManager = Mockito.mock(IProjectAccessDAO.class);
        mockedProjectManager = Mockito.mock(IProjectCollaboratorManager.class);
        MockitoAnnotations.initMocks(this);

        Mockito.when(mockedManager.getProjectOwner("project1")).thenReturn("user1");
        Mockito.when(mockedManager.getNrOfOwnedProjects("user1")).thenReturn(3);
        Mockito.when(mockedManager.getNrOfOwnedProjects("user2")).thenReturn(0);
        Mockito.when(mockedManager.getNrOfOwnedProjects("user3")).thenReturn(1);
        Mockito.when(mockedManager.getNrOfProjectsCollaboratingOn("user1", "role1")).thenReturn(3);
        Mockito.when(mockedManager.getNrOfProjectsCollaboratingOn("user2", "role1")).thenReturn(0);
        Mockito.when(mockedManager.getNrOfProjectsCollaboratingOn("user3", "role1")).thenReturn(1);

        IProjectCollaborator pColl1 = new ProjectCollaborator();
        ICollaborator coll1 = new Collaborator();
        IUser user1 = new User();
        user1.setUserName("user1");
        coll1.setUserObj(user1);

        IQuadrigaRole role = new QuadrigaRole();
        role.setId("role1");
        List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
        roles.add(role);
        coll1.setCollaboratorRoles(roles);

        pColl1.setCollaborator(coll1);
        List<IProjectCollaborator> collabs = new ArrayList<IProjectCollaborator>();
        collabs.add(pColl1);

        Mockito.when(mockedProjectManager.getProjectCollaborators("project1")).thenReturn(collabs);
    }

    @Test
    public void testUserIsProjectOwner() throws QuadrigaStorageException {
        assertTrue(securityChecker.isProjectOwner("user1", "project1"));
    }

    @Test
    public void testUserIsNotProjectOwner() throws QuadrigaStorageException {
        assertFalse(securityChecker.isProjectOwner("user2", "project1"));
    }

    @Test
    public void testIsOwnerOfSeveralProjects() throws QuadrigaStorageException {
        assertTrue(securityChecker.ownsAtLeastOneProject("user1"));
    }

    @Test
    public void testIsNoOwner() throws QuadrigaStorageException {
        assertFalse(securityChecker.ownsAtLeastOneProject("user2"));
    }

    @Test
    public void testIsOwnerOfOneProject() throws QuadrigaStorageException {
        assertTrue(securityChecker.ownsAtLeastOneProject("user3"));
    }

    /**
     * Tests
     * {@link edu.asu.spring.quadriga.accesschecks.impl.ProjectSecurityChecker.collaboratesOnAtLeastOneProject(String,
     * String)}
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void testIsCollaboratingOnSeveralProjects() throws QuadrigaStorageException {
        assertTrue(securityChecker.collaboratesOnAtLeastOneProject("user1", "role1"));
    }

    /**
     * Tests
     * {@link edu.asu.spring.quadriga.accesschecks.impl.ProjectSecurityChecker.collaboratesOnAtLeastOneProject(String,
     * String)}
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void testIsNoCollaborator() throws QuadrigaStorageException {
        assertFalse(securityChecker.collaboratesOnAtLeastOneProject("user2", "role1"));
    }

    /**
     * Tests
     * {@link edu.asu.spring.quadriga.accesschecks.impl.ProjectSecurityChecker.collaboratesOnAtLeastOneProject(String,
     * String)}
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void testIsCollaboratingOnOneProject() throws QuadrigaStorageException {
        assertTrue(securityChecker.collaboratesOnAtLeastOneProject("user3", "role1"));
    }

    /**
     * Tests
     * {@link edu.asu.spring.quadriga.accesschecks.impl.ProjectSecurityChecker.isUserCollaboratorOnProject(String,
     * String, String)}
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void testUserHasProjectAccess() throws QuadrigaStorageException {
        assertTrue(securityChecker.isUserCollaboratorOnProject("user1", "project1", "role1"));
    }

}
