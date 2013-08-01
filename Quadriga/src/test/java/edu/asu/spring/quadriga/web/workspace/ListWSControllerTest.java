package edu.asu.spring.quadriga.web.workspace;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml","file:src/test/resources/rest-service.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ListWSControllerTest {

	ListWSController listWSController;
	
	@Autowired
	private	IListWSManager wsManager;
	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;
	@Autowired
	private IDspaceManager dspaceManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		listWSController = new ListWSController();
		listWSController.setWsManager(wsManager);
		listWSController.setWsCollabManager(wsCollabManager);
		listWSController.setDspaceManager(dspaceManager);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testGetWorkspaceDetails() {
		fail("Not yet implemented");
	}

	
	@Test
	public void testAddFilesDspaceAuthentication() {
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", null, null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", "test", null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", null, "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", "", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", "test", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", "", "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.addFilesDspaceAuthentication("w1", "test", "pass123", null, null));
	}

	@Ignore
	@Test
	public void testSyncFilesDspaceAuthentication() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testChangeDspaceAuthentication() {
		fail("Not yet implemented");
	}
	
	@Ignore
	@Test
	public void testWorkspaceCommunityListRequest() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testWorkspaceCommunityRequest() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testWorkspaceItemListRequest() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testWorkspaceBitStreamListRequest() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCollectionStatus() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetBitStreamStatus() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testAddBitStreamsToWorkspace() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testDeleteBitStreamsFromWorkspace() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testUpdateBitStreamsFromWorkspace() {
		fail("Not yet implemented");
	}

}
