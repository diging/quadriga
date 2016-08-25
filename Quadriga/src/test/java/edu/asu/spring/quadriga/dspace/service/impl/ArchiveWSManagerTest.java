package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.impl.ArchiveWSManager;

public class ArchiveWSManagerTest {

    @Mock
    private IWorkspaceDAO workspaceDao;
    
    @InjectMocks
    private ArchiveWSManager managerToTest;

    private WorkspaceDTO wsDto1;
    private WorkspaceDTO wsDto2;
    
    @Before
    public void init() throws QuadrigaStorageException {
        workspaceDao = Mockito.mock(IWorkspaceDAO.class);
        MockitoAnnotations.initMocks(this);
        
        wsDto1 = new WorkspaceDTO();
        wsDto1.setCreatedby("user1");
        wsDto1.setCreateddate(new Date());
        wsDto1.setIsarchived(false);
        wsDto1.setDescription("test 1");
        wsDto1.setWorkspaceid("ws1");
        wsDto1.setIsdeactivated(false);
        
        wsDto2 = new WorkspaceDTO();
        wsDto2.setCreatedby("user2");
        wsDto2.setCreateddate(new Date());
        wsDto2.setIsarchived(false);
        wsDto2.setDescription("test 2");
        wsDto2.setWorkspaceid("ws2");
        wsDto2.setIsdeactivated(false);
        
        Mockito.when(workspaceDao.getDTO("ws1")).thenReturn(wsDto1);
        Mockito.when(workspaceDao.getDTO("ws2")).thenReturn(wsDto2);
    }
    
    @Test
    public void testArchiveWsWithOneWs() {
        managerToTest.archiveWorkspace("ws1", "user2");
        Assert.assertTrue(wsDto1.getIsarchived());
        Assert.assertEquals("user2", wsDto1.getUpdatedby());
    }
    
    @Test
    public void testArchiveWSWithTwoWs() {
        managerToTest.archiveWorkspace("ws1, ws2", "user2");
        Assert.assertTrue(wsDto1.getIsarchived());
        Assert.assertTrue(wsDto2.getIsarchived());
    }
    
    @Test
    public void testUnarchiveWsWithOneWs() {
        wsDto1.setIsarchived(true);
        managerToTest.unArchiveWorkspace("ws1", "user2");
        Assert.assertFalse(wsDto1.getIsarchived());
        Assert.assertEquals("user2", wsDto1.getUpdatedby());
    }
    
    @Test
    public void testUnarchiveWSWithTwoWs() {
        wsDto1.setIsarchived(true);
        wsDto2.setIsarchived(true);
        managerToTest.unArchiveWorkspace("ws1, ws2", "user2");
        Assert.assertFalse(wsDto1.getIsarchived());
        Assert.assertFalse(wsDto2.getIsarchived());
    }
    
    @Test
    public void testDeactivateOneWs() {
        managerToTest.deactivateWorkspace("ws1", "user2");
        Assert.assertTrue(wsDto1.getIsdeactivated());
        Assert.assertEquals("user2", wsDto1.getUpdatedby());
    }
    
    @Test
    public void testDeactivateTwoWs() {
        managerToTest.deactivateWorkspace("ws1, ws2", "user2");
        Assert.assertTrue(wsDto1.getIsdeactivated());
        Assert.assertTrue(wsDto2.getIsdeactivated());
    }
    
    @Test
    public void testActivateOneWs() throws QuadrigaStorageException {
        wsDto1.setIsdeactivated(true);
        managerToTest.activateWorkspace("ws1", "user2");
        Assert.assertFalse(wsDto1.getIsdeactivated());
        Assert.assertEquals("user2", wsDto1.getUpdatedby());
    }
}
