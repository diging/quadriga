package edu.asu.spring.quadriga.accesschecks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.accesschecks.impl.WSSecurityChecker;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceAccessDAO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.login.RoleNames;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckWSSecurityTest {

    @Mock
    private IProjectSecurityChecker projectSecurity;
    
    @Mock
    private IWorkspaceAccessDAO dbConnect;
    
    @InjectMocks
    private WSSecurityChecker securityChecker;
    
    @Before
    public void init() throws QuadrigaStorageException {
        projectSecurity = Mockito.mock(IProjectSecurityChecker.class);
        dbConnect = Mockito.mock(IWorkspaceAccessDAO.class);
        MockitoAnnotations.initMocks(this);

        // set default return values
        Mockito.when(projectSecurity.isProjectOwner(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        Mockito.when(projectSecurity.isUserCollaboratorOnProject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        
        Mockito.when(projectSecurity.isProjectOwner("user1", "project1")).thenReturn(
                true);
        Mockito.when(projectSecurity.isUserCollaboratorOnProject("user1", "project2", RoleNames.ROLE_COLLABORATOR_OWNER)).thenReturn(
                true);
        Mockito.when(projectSecurity.isUserCollaboratorOnProject("user2", "project1", RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN)).thenReturn(
                true);
        Mockito.when(projectSecurity.isUserCollaboratorOnProject("user3", "project1", RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR)).thenReturn(
                true);
        Mockito.when(projectSecurity.isUserCollaboratorOnProject("user4", "project1", RoleNames.ROLE_QUADRIGA_ADMIN)).thenReturn(
                true);
        
        Mockito.when(dbConnect.chkWorkspaceOwner(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        Mockito.when(dbConnect.chkWorkspaceOwner("user2", "ws1")).thenReturn(true);
    }
    
    @Test
    public void testCanCreateWsUserIsProjectOwner() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasPermissionToCreateWS("user1", "project1"));
    }
    
    @Test
    public void testCanCreateWsUserIsProjAdmin() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasPermissionToCreateWS("user1", "project2"));
    }
    
    @Test
    public void testCanCreateWsUserIsCollabAdmin() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasPermissionToCreateWS("user2", "project1"));
    }
    
    @Test
    public void testCanCreateWsUserIsCollab() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasPermissionToCreateWS("user3", "project1"));
    }
    
    @Test
    public void testCanCreateWsUserIsAnythingElse() throws QuadrigaStorageException {
        assertFalse(securityChecker.hasPermissionToCreateWS("userX", "project1"));
    }
    
    @Test
    public void testHasWSAccessUserIsProjectOwner() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasAccessToWorkspace("user1", "project1", "ws1"));
    }
    
    @Test
    public void testHasWSAccessUserIsWSOwner() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasAccessToWorkspace("user2", "project1", "ws1"));
    }
    
    @Test
    public void testHasWSAccessUserIsQuadrigaAdmin() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasAccessToWorkspace("user4", "project1", "ws1"));
    }
    
    @Test
    public void testHasWSAccessUserIsCollaborator() throws QuadrigaStorageException {
        assertTrue(securityChecker.hasAccessToWorkspace("user2", "project1", "ws1"));
    }
    
    @Test
    public void testHasWSAccessUserIsSomethingElse() throws QuadrigaStorageException {
        assertFalse(securityChecker.hasAccessToWorkspace("userX", "project1", "ws1"));
    }
}
