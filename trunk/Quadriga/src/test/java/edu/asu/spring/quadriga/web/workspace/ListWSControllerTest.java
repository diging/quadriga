package edu.asu.spring.quadriga.web.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
	IDBConnectionManager dbConnection;
	String sDatabaseSetup;

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
		sDatabaseSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&";
		sDatabaseSetup += "delete from tbl_dspace_keys&delete from tbl_workspace_dspace";
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

		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(null, principal, model));
		//Check if the null keys were handled
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());

		dspaceKeys.setPrivateKey(null);
		dspaceKeys.setPublicKey(null);
		//Check if the null keys were handled
		assertEquals("redirect:/auth/workbench/keys", listWSController.addDspaceKeys(dspaceKeys, principal, model));
		dspaceKeys =  (DspaceKeys) dspaceManager.getDspaceKeys("test");
		assertEquals("b459689e",dspaceKeys.getPublicKey());
		assertEquals("12cabcca2128e67e", dspaceKeys.getPrivateKey());

		dspaceKeys.setPrivateKey("");
		dspaceKeys.setPublicKey("");
		//Check if the empty keys were handled
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
	public void testAddFilesDspaceAuthentication() {
		//Setup username and password
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.addFilesDspaceAuthentication("w1", mock, null, null));

		//Use public access
		mock.addParameter("dspacePublicAccess", "public");
		mock.removeParameter("username");
		mock.removeParameter("password");
		assertEquals("redirect:/auth/workbench/workspace/w1/communities", listWSController.addFilesDspaceAuthentication("w1", mock, null, null));

		//Handle empty username and password
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addFilesDspaceAuthentication("w1", mock, null, null));

		//Reset the values
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
	}


	@Test
	public void testAddDspaceAuthentication()
	{
		//Setup username and password
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addDspaceAuthentication("w1", mock, null, null));
		assertEquals("test", listWSController.getDspaceUsername());
		assertEquals("test", listWSController.getDspacePassword());

		//Use public access
		mock.addParameter("dspacePublicAccess", "public");
		mock.removeParameter("username");
		mock.removeParameter("password");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addDspaceAuthentication("w1", mock, null, null));
		assertEquals("", listWSController.getDspaceUsername());
		assertEquals("", listWSController.getDspacePassword());

		//Handle empty username and password
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.addDspaceAuthentication("w1", mock, null, null));
		//Values should not have changed
		assertEquals("", listWSController.getDspaceUsername());
		assertEquals("", listWSController.getDspacePassword());

		//Reset the values
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		listWSController.setDspaceUsername("test");
		listWSController.setDspacePassword("test");

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
		assertEquals("", listWSController.getDspaceUsername());
		assertEquals("", listWSController.getDspacePassword());

		//Handle empty username and password
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));
		//Values should not have changed
		assertEquals("", listWSController.getDspaceUsername());
		assertEquals("", listWSController.getDspacePassword());

		//Reset the values
		mock.removeAllParameters();
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		listWSController.setDspaceUsername("test");
		listWSController.setDspacePassword("test");
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityListRequest() throws QuadrigaException {
		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
		assertEquals("redirect:/auth/workbench/workspace/workspacedetails/w1", listWSController.changeDspaceAuthentication("w1", mock, null, null));

		//Load the list of communities
		assertEquals("auth/workbench/workspace/communities",listWSController.workspaceCommunityListRequest(null, model, principal));

		//Check if the list of communities are not null
		List<ICommunity> communities = (List<ICommunity>) model.get("communityList");
		assertNotNull(communities);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkspaceCommunityRequest() throws QuadrigaException {

		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
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
	public void testWorkspaceItemListRequest() throws QuadrigaException {
		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
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
	public void testWorkspaceBitStreamListRequest() throws QuadrigaException {
		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
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
	public void testGetCollectionStatus() throws QuadrigaException {
		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
		listWSController.changeDspaceAuthentication("w1", mock, model, principal);
		//Load the community list
		listWSController.workspaceCommunityListRequest(null, model, principal);
		assertEquals("auth/workbench/workspace/community", listWSController.workspaceCommunityRequest("w1", "18", model, principal));

		//Wait for the collection to load
		while(listWSController.getCollectionStatus("55").equals("Loading..."));

		assertNotSame("Loading...",listWSController.getCollectionStatus("55"));
	}

	@Test
	public void testGetBitStreamStatus() throws QuadrigaException {
		//Setup username and password
		mock.addParameter("username", "test");
		mock.addParameter("password", "test");
		mock.removeParameter("dspacePublicAccess");
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
