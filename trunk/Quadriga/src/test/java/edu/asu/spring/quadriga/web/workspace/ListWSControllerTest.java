package edu.asu.spring.quadriga.web.workspace;

import static org.junit.Assert.*;

import java.security.Principal;
import java.util.List;

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
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.domain.ICommunity;
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
	
	private Principal principal;
	private BindingAwareModelMap model;
	
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
		
		model =  new BindingAwareModelMap();		
		principal = new Principal() {			
			@Override
			public String getName() {
				return "test";
			}
		};
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

	@Test
	public void testSyncFilesDspaceAuthentication() {
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", null, null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", "test", null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", null, "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", "", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", "test", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.syncFilesDspaceAuthentication("w1", "", "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/w1/updatebitstreams", listWSController.syncFilesDspaceAuthentication("w1", "test", "pass123", null, null));
	}

	
	@Test
	public void testChangeDspaceAuthentication() {
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", null, null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "test", null, null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", null, "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "test", "", null, null));
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "", "pass123", null, null));
		
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "test", "pass123", null, null));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityListRequest() {
		//Setup username and password
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", "test", "pass123", null, null));
		
		//Load the list of communities
		assertEquals("auth/workbench/workspace/communities",listWSController.workspaceCommunityListRequest(null, model, principal));
		
		//Check if the list of communities are not null
		List<ICommunity> communities = (List<ICommunity>) model.get("communityList");
		assertNotNull(communities);
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
