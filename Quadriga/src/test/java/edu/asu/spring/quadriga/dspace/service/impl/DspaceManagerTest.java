package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.implementation.BitStream;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class tests {@link DspaceManager}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml","file:src/test/resources/rest-service.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DspaceManagerTest {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	private IDspaceManager dspaceManager;

	@Autowired
	@Qualifier("dspaceFilePath")
	private String filePath;

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

	private DspaceKeys dspaceKeys;

	@Autowired
	private IDBConnectionManager dbConnection;
	private String sDatabaseSetup;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dspaceManager = new DspaceManager();
		dspaceManager.setFilePath(filePath);
		dspaceManager.setRestTemplate(restTemplate);
		dspaceManager.setDbconnectionManager(dbconnectionManager);
		dspaceManager.setDspaceProperties(dspaceProperties);
		dspaceManager.setProxyCommunityManager(proxyCommunityManager);

		dspaceKeys = new DspaceKeys();
		dspaceKeys.setPublicKey("b459689e");
		dspaceKeys.setPrivateKey("12cabcca2128e67e");

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

	@Test
	public void testGetAllCommunities() throws QuadrigaException, QuadrigaAccessException {
		//Dspace return list of communities for empty username/password
		assertNotNull(dspaceManager.getAllCommunities(null,null, null).size());

		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
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
		while(dspaceManager.getCollection("10") == null);

		ICollection collection = dspaceManager.getCollection("10");
		assertNotNull(collection);
		assertEquals("10", collection.getId());

		//Invalid collection id
		assertNull(dspaceManager.getCollection("11111111111111111111"));

		//Handle null case for collection id
		assertNull(dspaceManager.getCollection(null));
	}

	@Test
	public void testGetCommunityId() throws QuadrigaException, QuadrigaAccessException {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(dspaceKeys,null,null));
		assertNotNull(dspaceManager.getAllCollections(dspaceKeys,null,null, "12"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("10") == null);
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
		ICollection collection = dspaceManager.getCollection("10");
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
		while(dspaceManager.getCollection("10") == null);
		//Wait for items to load
		while(dspaceManager.getCollection("10").getLoadStatus() == false);
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
		while(dspaceManager.getCollection("10") == null);
		while(dspaceManager.getCollection("10").getLoadStatus() == false);
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
		while(dspaceManager.getCollection("10") == null);
		while(dspaceManager.getCollection("10").getLoadStatus() == false);

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

	@Ignore
	@Test
	public void testAddBitStreamsToWorkspace() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testDeleteBitstreamFromWorkspace() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testUpdateDspaceMetadata() {
		fail("Not yet implemented");
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
		while(dspaceManager.getCollection("10") == null);
		while(dspaceManager.getCollection("10").getLoadStatus() == false);
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

		//Check empty dspace credentials
		List<IBitStream> checkedBitStreams = dspaceManager.checkDspaceBitstreamAccess(bitstreams, null, null, null);
		for(IBitStream checkedBitStream: checkedBitStreams)
		{
			assertEquals("Need Dspace Authentication", checkedBitStream.getCommunityName());
			assertEquals("Need Dspace Authentication", checkedBitStream.getCollectionName());
			assertEquals("Need Dspace Authentication", checkedBitStream.getItemName());
			assertEquals("Need Dspace Authentication", checkedBitStream.getName());
		}


		checkedBitStreams = dspaceManager.checkDspaceBitstreamAccess(bitstreams, dspaceKeys, null, null);
		for(IBitStream checkedBitStream: checkedBitStreams)
		{
			assertEquals("Leuckart Wall Charts", checkedBitStream.getCommunityName());
			assertEquals("Images", checkedBitStream.getCollectionName());
			assertEquals("Leuckart Chart, Series I, Chart 80: Echinodermata; Holothurioidea; Echinoidea, Ophiuroidea; Holothuria, Cucumaria, etc. Larval development", checkedBitStream.getItemName());
			assertEquals("050.tif", checkedBitStream.getName());
		}
	}
}
