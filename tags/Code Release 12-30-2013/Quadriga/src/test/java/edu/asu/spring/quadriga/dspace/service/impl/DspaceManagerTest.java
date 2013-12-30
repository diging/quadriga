package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.implementation.BitStream;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * This class tests {@link DspaceManager}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml","file:src/test/resources/rest-service.xml","file:src/test/resources/hibernate.cfg.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration
public class DspaceManagerTest {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	private IDspaceManager dspaceManager;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	//Handle to the proxy community manager class
	@Autowired
	private ICommunityManager proxyCommunityManager;

	@Autowired
	private IDBConnectionDspaceManager dbconnectionManager;

	@Resource(name = "dspaceStrings")
	private Properties dspaceProperties;

	@Resource(name = "uiMessages")
	private Properties dspaceMessages;

	private DspaceKeys dspaceKeys;

	@Autowired
	private IDBConnectionManager dbConnection;
	private String sDatabaseSetup;

	@Autowired
	private IBitStreamFactory bitstreamFactory;

	@Autowired
	private	IListWSManager wsManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dspaceManager = new DspaceManager();
		dspaceManager.setRestTemplate(restTemplate);
		dspaceManager.setDbconnectionManager(dbconnectionManager);
		dspaceManager.setDspaceProperties(dspaceProperties);
		dspaceManager.setProxyCommunityManager(proxyCommunityManager);
		dspaceManager.setBitstreamFactory(bitstreamFactory);
		dspaceManager.setDspaceMessages(dspaceMessages);

		dspaceKeys = new DspaceKeys();
		dspaceKeys.setPublicKey("b459689e");
		dspaceKeys.setPrivateKey("12cabcca2128e67e");

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

	@Test
	public void testGetAllCommunities() throws QuadrigaException, QuadrigaAccessException {
		//Dspace return list of communities for empty username/password
		assertNotNull(dspaceManager.getAllCommunities(null,null, null).size());

		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null, null));
	}

	@Test
	public void testGetAllCollections() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null, null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null, null,"12"));

		//Invalid community id
		assertNull(dspaceManager.getAllCollections(dspaceKeys, null, null, "11111111111111"));

		//Handle null case for community id
		assertNull(dspaceManager.getAllCollections(dspaceKeys, null, null, null));
	}

	@Test
	public void testGetCommunityName() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getCommunityName("12"));

		//Handle invalid community id
		assertNull(dspaceManager.getCommunityName("1111111111111111"));


		//Handle null case for community id
		assertNull(dspaceManager.getCommunityName(null));
	}

	@Test
	public void testGetCollectionName() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollectionName("10")==null);
		assertNotNull(dspaceManager.getCollectionName("10"));

		//Handle invalid collection id
		assertNull(dspaceManager.getCollectionName("111111111111111"));

		//Handle null case for collection id
		assertNull(dspaceManager.getCollectionName(null));

	}

	@Test
	public void testGetCollection() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10" , "12") == null);

		ICollection collection = dspaceManager.getCollection("10" , "12");
		assertNotNull(collection);
		assertEquals("10", collection.getId());

		//Invalid collection id
		assertNull(dspaceManager.getCollection("11111111111111111111" , "12"));

		//Handle null case for collection id
		assertNull(dspaceManager.getCollection(null,"12"));
	}

	@Test
	public void testGetCommunityId() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		assertEquals("12",dspaceManager.getCommunityId("10"));

		//Invalid collection id
		assertNull(dspaceManager.getCommunityId("11111111111111"));

		//Check null case
		assertNull(dspaceManager.getCommunityId(null));

	}

	@Test
	public void testGetAllItems() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		ICollection collection = dspaceManager.getCollection("10","12");
		//Wait for the collection to load
		while(dspaceManager.getCollectionName("10")==null);
		//Wait for items to load
		while(collection.getLoadStatus() == false);

		//Invalid collection id
		assertNull(dspaceManager.getAllItems("111111111111111"));

		//Check null case
		assertNull(dspaceManager.getAllItems(null)); 
	}

	@Test
	public void testGetAllBitStreams() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		//Wait for items to load
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);
		assertNotNull(dspaceManager.getAllItems("10"));

		//Check invalid ids
		assertNull(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "11111111111111", null));
		assertNull(dspaceManager.getAllBitStreams(dspaceKeys,null,null, null, "11111111111111"));

		//Check null cases
		assertNull(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", null));
		assertNull(dspaceManager.getAllBitStreams(dspaceKeys,null,null, null, "1217"));
		assertNull(dspaceManager.getAllBitStreams(dspaceKeys,null,null, null, null));

		//Bitstreams are loaded from dspace
		assertNotNull(dspaceManager.getAllBitStreams(null,"test", "test", "10", "1217"));
	}

	@Test
	public void testGetItemName() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);
		assertNotNull(dspaceManager.getItemName("10", "1217"));

		//Check invalid ids
		assertNull(dspaceManager.getItemName("10","111111111111"));
		assertNull(dspaceManager.getItemName("11111111111","1217"));

		//Check null cases
		assertNull(dspaceManager.getItemName(null,null));
		assertNull(dspaceManager.getItemName("10",null));
		assertNull(dspaceManager.getItemName(null,"1217"));
	}

	@Test
	public void testGetBitStream() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);

		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);

		//Handle invalid ids
		assertNull(dspaceManager.getBitStream("10", "1217", "1111111111"));
		assertNull(dspaceManager.getBitStream("10", "11111111111", "3991"));
		assertNull(dspaceManager.getBitStream("1111111111", "1217", "3991"));
		assertNull(dspaceManager.getBitStream("", "", ""));

		//Check null cases
		assertNull(dspaceManager.getBitStream("10", "1217", null));
		assertNull(dspaceManager.getBitStream("10", null, "3991"));
		assertNull(dspaceManager.getBitStream(null, "1217", "3991"));
		assertNull(dspaceManager.getBitStream(null, null, null));

		assertNotNull(dspaceManager.getBitStream("10", "1217", "3991"));

	}

	@Test
	public void testClearCompleteCache() {
		dspaceManager.clearCompleteCache();
		assertNull(dspaceManager.getCommunityName("18"));
	}

	@Test
	public void testGetDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException {
		//Setup the database
		testSetupTestEnvironment();

		//No keys are in the database.
		IDspaceKeys dbkeys = dspaceManager.getDspaceKeys("test");
		assertNull(dbkeys);

		//Insert new keys into database and check retrieval
		dspaceManager.addDspaceKeys(dspaceKeys, "test");
		dbkeys = dspaceManager.getDspaceKeys("test");
		assertEquals(dspaceKeys.getPublicKey(), dbkeys.getPublicKey());
		assertEquals(dspaceKeys.getPrivateKey(), dbkeys.getPrivateKey());
	}

	@Test
	public void testGetMaskedDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException{
		//Setup the database
		testSetupTestEnvironment();

		//No keys are in the database.
		IDspaceKeys dbkeys = dspaceManager.getMaskedDspaceKeys("test");
		assertNull(dbkeys);

		//Insert new keys into database and check retrieval
		dspaceManager.addDspaceKeys(dspaceKeys, "test");

		dbkeys = dspaceManager.getMaskedDspaceKeys("test");
		assertEquals("xxxx689e", dbkeys.getPublicKey());
		assertEquals("xxxxxxxxxxxxe67e", dbkeys.getPrivateKey());

	}

	@Test
	public void testAddDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException{
		//Setup the database
		testSetupTestEnvironment();

		//Insert new keys into database and check retrieval
		assertEquals(SUCCESS, dspaceManager.addDspaceKeys(dspaceKeys, "test"));

		IDspaceKeys dbkeys = dspaceManager.getDspaceKeys("test");
		assertEquals(dspaceKeys.getPublicKey(), dbkeys.getPublicKey());
		assertEquals(dspaceKeys.getPrivateKey(), dbkeys.getPrivateKey());
	}

	@Test
	public void testDeleteDspaceKeys() throws QuadrigaStorageException, QuadrigaAccessException{
		//Setup the database
		testSetupTestEnvironment();

		//Insert new keys into database
		assertEquals(SUCCESS, dspaceManager.addDspaceKeys(dspaceKeys, "test"));

		//Delete keys in the database
		assertEquals(SUCCESS, dspaceManager.deleteDspaceKeys("test"));

		//Check for keys in the database
		IDspaceKeys dbkeys = dspaceManager.getDspaceKeys("test");
		assertNull(dbkeys);
	}

	@Test
	public void testCheckDspaceBitstreamAccess() throws QuadrigaException, QuadrigaAccessException{
		dspaceManager.clearCompleteCache();
		//Load the community, collection, item and bitstream
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));
		while(dspaceManager.getCollection("10","12") == null);
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);
		while(dspaceManager.getBitStream("10", "1217", "3991").getLoadStatus() == false);

		//Setup bitstreams
		IBitStream bitstream = new BitStream();
		bitstream.setCommunityid("12");
		bitstream.setCollectionid("10");
		bitstream.setItemid("1217");
		bitstream.setId("3991");
		List<IBitStream> bitstreams = new ArrayList<IBitStream>();
		bitstreams.add(bitstream);

		List<IBitStream> checkedBitStreams = dspaceManager.checkDspaceBitstreamAccess(bitstreams, dspaceKeys, null, null);
		for(IBitStream checkedBitStream: checkedBitStreams)
		{
			assertEquals("Leuckart Wall Charts", checkedBitStream.getCommunityName());
			assertEquals("Images", checkedBitStream.getCollectionName());
			assertEquals("Leuckart Chart, Series I, Chart 80: Echinodermata; Holothurioidea; Echinoidea, Ophiuroidea; Holothuria, Cucumaria, etc. Larval development", checkedBitStream.getItemName());
			assertEquals("050.tif", checkedBitStream.getName());
		}

		dspaceManager.clearCompleteCache();	
		//Check wrong dspace credentials
		checkedBitStreams = dspaceManager.checkDspaceBitstreamAccess(bitstreams, null, "test", "test");
		for(IBitStream checkedBitStream: checkedBitStreams)
		{
			assertEquals("Wrong Dspace Authentication", checkedBitStream.getCommunityName());
			assertEquals("Wrong Dspace Authentication", checkedBitStream.getCollectionName());
			assertEquals("Wrong Dspace Authentication", checkedBitStream.getItemName());
			assertEquals("Wrong Dspace Authentication", checkedBitStream.getName());
		}

	}

	@Test
	public void testAddBitStreamsToWorkspace() throws QuadrigaStorageException, QuadrigaException, QuadrigaAccessException {
		testSetupTestEnvironment();
		String[] bitstreamids = {"3991"};
		try
		{
			//Handle illegal access to system. Must throw an QuadrigaAccessException
			dspaceManager.addBitStreamsToWorkspace("WS_1", "12", "10", "1217",bitstreamids , "test");
			fail("Addition of bitstreams to worksoace does not handle empty bitstream loads");
		}
		catch(QuadrigaAccessException e)
		{

		}

		/**
		 * Valid test case. Add bitstream to workspace.
		 */
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));
		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);
		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);
		//Add workspace-file link in the database.
		dspaceManager.addBitStreamsToWorkspace("WS_1", "12", "10", "1217",bitstreamids , "test");

		//Check in the database if the file was added
		IWorkSpace workspace = wsManager.getWorkspaceDetails("WS_1", "test");
		IBitStream bitstream = workspace.getBitstreams().get(0);
		assertEquals("12",bitstream.getCommunityid());
		assertEquals("10", bitstream.getCollectionid());
		assertEquals("1217", bitstream.getItemid());
		assertEquals("3991", bitstream.getId());
	}

	@Test
	public void testDeleteBitstreamFromWorkspace() throws QuadrigaStorageException, QuadrigaException, QuadrigaAccessException {
		testSetupTestEnvironment();
		String[] bitstreamids = {"3991"};
		/**
		 * Add bitstream to workspace.
		 */
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));
		//Wait for the collection to load
		while(dspaceManager.getCollection("10","12") == null);
		while(dspaceManager.getCollection("10","12").getLoadStatus() == false);
		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(dspaceKeys,null,null, "10", "1217") == null);
		//Add workspace-file link in the database.
		dspaceManager.addBitStreamsToWorkspace("WS_1", "12", "10", "1217",bitstreamids , "test");

		try
		{
			//Must throw an exception
			dspaceManager.deleteBitstreamFromWorkspace(null, null, null, null);
			fail("Deletion of bitstreams from workspace does not null inputs");
		}
		catch(QuadrigaAccessException e)
		{

		}
		
		IBitStream bitstream = new BitStream();
		bitstream.setId("3991");
		bitstream.setName("valid bitstream");
		
		List<IBitStream> workspacebitstreams = new ArrayList<IBitStream>();
		workspacebitstreams.add(bitstream);
		dspaceManager.deleteBitstreamFromWorkspace("WS_1", bitstreamids, workspacebitstreams, "test");
		
		//Check in the database if the file was deleted
		IWorkSpace workspace = wsManager.getWorkspaceDetails("WS_1", "test");
		assertEquals(0, workspace.getBitstreams().size());
	}

	@Test
	public void testValidateDspaceCredentials(){
		//Public access
		assertTrue(dspaceManager.validateDspaceCredentials(null, null, null));
		
		//Invalid user
		assertFalse(dspaceManager.validateDspaceCredentials("test", "test", null));
		
		dspaceKeys.setPublicKey("11111");
		dspaceKeys.setPrivateKey("11111");
		//Invalid keys
		assertFalse(dspaceManager.validateDspaceCredentials(null, null, dspaceKeys));
		
		//Reset to correct keys
		dspaceKeys.setPublicKey("b459689e");
		dspaceKeys.setPrivateKey("12cabcca2128e67e");
		
		//Correct keys with wrong username. Keys must take precedence over username/password
		assertTrue(dspaceManager.validateDspaceCredentials("test", "test", dspaceKeys));
	}
}
