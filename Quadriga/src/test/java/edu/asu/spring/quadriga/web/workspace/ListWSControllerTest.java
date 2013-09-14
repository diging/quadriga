package edu.asu.spring.quadriga.web.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

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
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
	@Autowired
	private IDspaceKeysFactory dspaceKeysFactory;

	private Principal principal;
	private BindingAwareModelMap model;
	private MockHttpServletRequest mock;
	private DspaceKeys dspaceKeys;

	@Autowired
	private IDBConnectionManager dbConnection;
	private String sDatabaseSetup;

	@Resource(name = "uiMessages")
	private Properties dspaceMessages;

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
		listWSController.setDspaceKeysFactory(dspaceKeysFactory);
		listWSController.setDspaceMessages(dspaceMessages);

		dspaceKeys = new DspaceKeys();
		dspaceKeys.setPublicKey("b459689e");
		dspaceKeys.setPrivateKey("12cabcca2128e67e");
		listWSController.setDspaceKeys(dspaceKeys);

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

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
		sDatabaseSetup += "&delete from tbl_dspace_keys&delete from tbl_workspace_dspace";
		sDatabaseSetup += "&delete from tbl_project;&insert into tbl_project values('projtrial','This is a trial project','trial','PROJ_1','test','ACCESSIBLE','test',CURDATE(),'test',CURDATE())";
		sDatabaseSetup += "&delete from tbl_workspace;&insert into tbl_workspace values('workspacetrial','This is a trial workspace','WS_1','test',0,0,'test',CURDATE(),'test',CURDATE());";
		sDatabaseSetup += "&delete from tbl_workspace_dspace";
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Load the required data into the dependent tables
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException
	{
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}

	@Ignore
	@Test
	public void testGetWorkspaceDetails() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDspaceKeysRequestForm() throws QuadrigaStorageException
	{
		testSetupTestEnvironment();

		ModelAndView model = listWSController.addDspaceKeysRequestForm(principal);
		assertNotNull(model);
		assertEquals("/auth/workbench/keys", model.getViewName());
	}

	@Test
	public void testAddDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException
	{
		testSetupTestEnvironment();
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(dspaceKeys, principal, model));

		//Check if the actual key values were inserted into the database
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());

		//Check if the null keys were handled
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(null, principal, model));
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());

		//Check if the null keys were handled
		dspaceKeys.setPrivateKey(null);
		dspaceKeys.setPublicKey(null);
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(dspaceKeys, principal, model));
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());

		//Check if the empty keys were handled
		dspaceKeys.setPrivateKey("");
		dspaceKeys.setPublicKey("");
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(dspaceKeys, principal, model));
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());
	}

	@Test
	public void testDeleteDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException
	{
		//Setup the database with keys for deletion
		testSetupTestEnvironment();
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(dspaceKeys, principal, model));

		assertEquals("redirect:/auth/workbench/keys", listWSController.deleteDspaceKeys(principal, model));
		assertNull(listWSController.getDspaceKeys());

		//Reload the keys into the controller
		listWSController.setDspaceKeys(dspaceKeys);
	}


	@Test
	public void testChangeDspaceAuthentication() {
		//Setup username and password
		mock.removeAllParameters();
		mock.addParameter("username", "changedUserName");
		mock.addParameter("password", "changedPassword");
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));
		assertEquals("changedUserName", listWSController.getDspaceUsername());
		assertEquals("changedPassword", listWSController.getDspacePassword());

		//Use public access
		mock.addParameter("dspacePublicAccess", "public");
		mock.removeParameter("username");
		mock.removeParameter("password");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));
		assertEquals(null, listWSController.getDspaceUsername());
		assertEquals(null, listWSController.getDspacePassword());

		//Handle empty username and password
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));
		//Values should not have changed
		assertEquals(null, listWSController.getDspaceUsername());
		assertEquals(null, listWSController.getDspacePassword());

		//Reset the values
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		listWSController.setDspaceUsername("test");
		listWSController.setDspacePassword("test");
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityListRequest() throws QuadrigaException, QuadrigaAccessException {
		//Handle null case for username, password and keys
		listWSController.setDspaceUsername(null);
		listWSController.setDspacePassword(null);
		listWSController.setDspaceKeys(null);

		//Handling null case for empty authentication
		assertEquals("auth/workbench/workspace/communities",listWSController.workspaceCommunityListRequest("w1", model, null));

		//Setup proper dspace keys
		dspaceManager.clearCompleteCache();
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the list of communities
		assertEquals("auth/workbench/workspace/communities",listWSController.workspaceCommunityListRequest("w1", model, null));

		//Check if the list of communities are not null
		List<ICommunity> communities = (List<ICommunity>) model.get("communityList");
		assertNotNull(communities);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityRequest() throws QuadrigaException, QuadrigaAccessException {
		//Handle null case for username, password and keys
		listWSController.setDspaceUsername(null);
		listWSController.setDspacePassword(null);
		listWSController.setDspaceKeys(null);
		//Direct request for community without getting the list first
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);

		//Check if redirected to community page
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));
		assertNotNull(model.get("communityName"));
		List<ICollection> collections = (List<ICollection>)model.get("collectionList");
		assertNotNull(collections.size());

		//Invalid community id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceCommunityRequest("w1", "1111111", model, principal));

		//Null community id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceCommunityRequest("w1", null, model, principal));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceItemListRequest() throws QuadrigaException, QuadrigaAccessException {
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));

		//Handle null case for username, password and keys
		listWSController.setDspaceUsername(null);
		listWSController.setDspacePassword(null);
		listWSController.setDspaceKeys(null);
		assertEquals("auth/workbench/workspace/community/collection", listWSController.workspaceItemListRequest("w1", "10", model, principal));
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Handle invalid collection id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceItemListRequest("w1", "1111111", model, principal));

		//Handle null collection id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceItemListRequest("w1", null, model, principal));

		//Check if redirected to community page - Valid collection id
		assertEquals("auth/workbench/workspace/community/collection", listWSController.workspaceItemListRequest("w1", "10", model, principal));

		List<IItem> items = (List<IItem>) model.get("itemList");
		assertNotNull(items.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceBitStreamListRequest() throws QuadrigaException, QuadrigaAccessException {
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));

		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest("w1", "1217", "10", model, principal));

		//Wait for the bitstream to load
		while(listWSController.getBitStreamStatus("3991", "1217", "10").equals("Loading..."));

		List<IBitStream> bitstreams = (List<IBitStream>)model.get("bitList");
		assertNotNull(bitstreams.size());

		//Handle null case for username, password and keys
		listWSController.setDspaceUsername(null);
		listWSController.setDspacePassword(null);
		listWSController.setDspaceKeys(null);
		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest("w1", "1217", "10", model, principal));
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Invalid collection id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceBitStreamListRequest("w1", "1217", "111111111", model, principal));
		//Null collection id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceBitStreamListRequest("w1", "1217", null, model, principal));

		//Invalid item id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceBitStreamListRequest("w1", "111111111", "10", model, principal));
		//Null item id
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.workspaceBitStreamListRequest("w1", null, "10", model, principal));
	}

	@Test
	public void testGetCollectionStatus() throws QuadrigaException, QuadrigaAccessException {
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));

		//Valid Collection id
		assertNotSame("Loading...",listWSController.getCollectionStatus("10"));

		//Invalid Collection id
		assertEquals("Restricted Collection", listWSController.getCollectionStatus("11111111111"));

		//Null Collection id
		assertEquals("Restricted Collection", listWSController.getCollectionStatus(null));
	}

	@Test
	public void testGetItemStatus() throws QuadrigaException, QuadrigaAccessException{
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));

		//Valid collection and item id
		assertNotSame("Loading...", listWSController.getItemStatus("10", "1217"));
		assertNotSame("Restricted Item", listWSController.getItemStatus("10", "1217"));

		//Invalid collection and valid item id
		assertEquals("Restricted Item", listWSController.getItemStatus("1111111111111", "1217"));

		//Invalid item and valid collection id
		assertEquals("Restricted Item", listWSController.getItemStatus("10", "111111111111"));

		//Invalid item, collection id
		assertEquals("Restricted Item", listWSController.getItemStatus("1111111111111111", "11111111111111"));

		//Null item id
		assertEquals("Restricted Item", listWSController.getItemStatus("10", null));

		//Null collection id
		assertEquals("Restricted Item", listWSController.getItemStatus(null, "1217"));

		//Null collection and item id
		assertEquals("Restricted Item", listWSController.getItemStatus(null, null));
	}

	@Test
	public void testGetBitStreamAccessStatus() throws QuadrigaException, QuadrigaAccessException{
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));
		//Make bitstream request
		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest(null, "1217", "10", model, principal));
		//Wait for the bitstream to load
		while(listWSController.getBitStreamStatus("3991", "1217", "10").equals("Loading..."));

		//Valid bitstream, item and collection id
		assertNotSame("Loading...", listWSController.getBitStreamAccessStatus("3991", "1217", "10"));
		assertNotSame("No Access to File", listWSController.getBitStreamAccessStatus("3991", "1217", "10"));

		//Invalid bitstream, Valid item and collection id
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus("11111111111111", "1217", "10"));
		//Invalid item id, Valid bitstream and collection id
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus("3991", "1111111111111111111", "10"));
		//Invalid collection id, Valid bitstream and item id
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus("3991", "1217", "111111111111111111"));

		//Handle null bitstream id
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus(null, "1217", "10"));
		//Handle null item id
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus("3991", null, "10"));
		//Handle null bitstream
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus("3991", "1217", null));
		//Handle null for all ids
		assertEquals("No Access to File", listWSController.getBitStreamAccessStatus(null, null, null));
	}

	@Test
	public void testGetBitStreamStatus() throws QuadrigaException, QuadrigaAccessException {
		//Setup Dspace access keys
		listWSController.setDspaceKeys(dspaceKeys);

		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "12", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("10").equals("Loading..."));

		//Make bitstream request
		assertEquals("auth/workbench/workspace/community/collection/item", listWSController.workspaceBitStreamListRequest(null, "1217", "10", model, principal));

		//Wait for the bitstream to load
		while(listWSController.getBitStreamStatus("3991", "1217", "10").equals("Loading..."));

		//Valid bitstream, collection and item id
		assertNotSame("Loading...", listWSController.getBitStreamStatus("3991", "1217", "10"));

	}

	@Test
	public void testAddBitStreamsToWorkspace() throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException {
		testSetupTestEnvironment();
		String[] bitstreamids = {"3991"};
		/**
		 * Valid test case. Add bitstream to workspace.
		 */
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));
		//Wait for the collection to load
		while(dspaceManager.getCollection("10") == null);
		while(dspaceManager.getCollection("10").getLoadStatus() == false);
		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);
		//Add workspace-file link in the database.
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/WS_1", listWSController.addBitStreamsToWorkspace("WS_1", "12", "10", "1217", bitstreamids, model, principal));

		//Check in the database if the file was added
		IWorkSpace workspace = wsManager.getWorkspaceDetails("WS_1", "test");
		IBitStream bitstream = workspace.getBitstreams().get(0);
		assertEquals("12",bitstream.getCommunityid());
		assertEquals("10", bitstream.getCollectionid());
		assertEquals("1217", bitstream.getItemid());
		assertEquals("3991", bitstream.getId());
	}


	@Test
	public void testDeleteBitStreamsFromWorkspace() throws QuadrigaStorageException, QuadrigaException, QuadrigaAccessException {
		testSetupTestEnvironment();
		String[] bitstreamids = {"3991"};
		/**
		 * Add bitstream to workspace.
		 */
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));
		//Wait for the collection to load
		while(dspaceManager.getCollection("10") == null);
		while(dspaceManager.getCollection("10").getLoadStatus() == false);
		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);
		//Add workspace-file link in the database.
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/WS_1", listWSController.addBitStreamsToWorkspace("WS_1", "12", "10", "1217", bitstreamids, model, principal));

		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/WS_1", listWSController.deleteBitStreamsFromWorkspace("WS_1", bitstreamids, model, principal));
		//Check in the database if the file was deleted
		IWorkSpace workspace = wsManager.getWorkspaceDetails("WS_1", "test");
		assertEquals(0, workspace.getBitstreams().size());

	}

}
