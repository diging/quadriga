package edu.asu.spring.quadriga.web.workspace;

import static org.junit.Assert.*;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

/**
 * This class tests {@link ListWSController}
 * It makes REST service calls to dspace.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
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
	MockHttpServletRequest mock;

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
		
		mock = new MockHttpServletRequest();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
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
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.addFilesDspaceAuthentication("w1", mock, null, null));
	}

	@Test
	public void testSyncFilesDspaceAuthentication() {
		assertEquals("redirect:/auth/workbench/workspace/w1/updatebitstreams", listWSController.syncFilesDspaceAuthentication("w1", mock, null, null));
	}


	@Test
	public void testChangeDspaceAuthentication() {
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityListRequest() {
		//Setup username and password
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));

		//Load the list of communities
		assertEquals("auth/workbench/workspace/communities",listWSController.workspaceCommunityListRequest(null, model, principal));

		//Check if the list of communities are not null
		List<ICommunity> communities = (List<ICommunity>) model.get("communityList");
		assertNotNull(communities);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityRequest() {

		//Setup username and password
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);

		//Check if redirected to community page
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));
		assertNotNull(model.get("communityName"));
		List<ICollection> collections = (List<ICollection>)model.get("collectionList");
		assertNotNull(collections.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceItemListRequest() {
		//Setup username and password
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("55").equals("Loading..."));

		//Check if redirected to community page
		assertEquals("auth/workbench/workspace/community/collection", listWSController.workspaceItemListRequest("w1", "55", model, principal));

		List<IItem> items = (List<IItem>) model.get("itemList");
		assertNotNull(items.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceBitStreamListRequest() {
		//Setup username and password
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("55").equals("Loading..."));

		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest(null, "9595", "55", model, principal));

		//Wait for the bitstream to load
		//while(listWSController.getBitStreamStatus("19490", "9595", "55").equals("Loading..."));

		List<IBitStream> bitstreams = (List<IBitStream>)model.get("bitList");
		assertNotNull(bitstreams.size());
	}

	@Test
	public void testGetCollectionStatus() {
		//Setup username and password
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("55").equals("Loading..."));
		
		assertNotSame("Loading...",listWSController.getCollectionStatus("55"));
	}

	@Test
	public void testGetBitStreamStatus() {
		//Setup username and password
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("55").equals("Loading..."));

		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest(null, "9595", "55", model, principal));

		//Wait for the bitstream to load
		while(listWSController.getBitStreamStatus("19490", "9595", "55").equals("Loading..."));
		
		assertNotSame("Loading...", listWSController.getBitStreamStatus("19490", "9595", "55"));
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
