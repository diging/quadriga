package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllCommunities() {
		//Dspace return list of communities for empty username/password
		assertNotNull(dspaceManager.getAllCommunities(null,null, null).size());

		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
	}

	@Test
	public void testGetAllCollections() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Handle null case for community id
		assertNull(dspaceManager.getAllCollections(null,"test", "test", null));
	}

	@Test
	public void testGetCommunityName() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getCommunityName("18"));

		//Handle null case for community id
		assertNull(dspaceManager.getCommunityName(null));
	}

	@Test
	public void testGetCollectionName() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Handle null case for collection id
		assertNull(dspaceManager.getCollectionName(null));

		//Wait for the collection to load
		while(dspaceManager.getCollectionName("55")==null);
		assertNotNull(dspaceManager.getCollectionName("55"));
	}

	@Test
	public void testGetCollection() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Handle null case for collection id
		assertNull(dspaceManager.getCollection(null));

		//Wait for the collection to load
		while(dspaceManager.getCollection("55") == null);

		ICollection collection = dspaceManager.getCollection("55");
		assertNotNull(collection);
		assertEquals("55", collection.getId());
	}

	@Test
	public void testGetCommunityId() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Check null case
		assertNull(dspaceManager.getCommunityId(null));

		//Wait for the collection to load
		while(dspaceManager.getCollection("55") == null);

		assertEquals("18",dspaceManager.getCommunityId("55"));

	}

	@Test
	public void testGetAllItems() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Wait for the collection to load
		ICollection collection = dspaceManager.getCollection("55");
		while(dspaceManager.getCollectionName("55")==null);
		while(collection.getItems() == null);
		while(collection.getItems().size() != Integer.parseInt(collection.getCountItems()));

		assertNotNull(dspaceManager.getAllItems("55"));

		//Check null case
		assertNull(dspaceManager.getAllItems(null)); 
	}

	@Test
	public void testGetAllBitStreams() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Check null cases
		assertNull(dspaceManager.getAllBitStreams(null,"test", "test", "55", null));
		assertNull(dspaceManager.getAllBitStreams(null,"test", "test", null, "9595"));
		assertNull(dspaceManager.getAllBitStreams(null,"test", "test", null, null));

		//Wait for the collection to load
		while(dspaceManager.getCollection("55") == null);
		assertNotNull(dspaceManager.getAllItems("55"));

		//Bitstreams are loaded from dspace
		assertNotNull(dspaceManager.getAllBitStreams(null,"test", "test", "55", "9595"));
	}

	@Test
	public void testGetItemName() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Check null cases
		assertNull(dspaceManager.getItemName(null,null));
		assertNull(dspaceManager.getItemName("55",null));
		assertNull(dspaceManager.getItemName(null,"9595"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("55") == null);

		//Check null case after the collection is loaded
		assertNull(dspaceManager.getItemName("55",null));

		assertNotNull(dspaceManager.getItemName("55", "9595"));
	}

	@Test
	public void testGetBitStream() {
		//Load all communities before trying to access collection
		assertNotNull(dspaceManager.getAllCommunities(null,"test", "test"));
		assertNotNull(dspaceManager.getAllCollections(null,"test", "test", "18"));

		//Wait for the collection to load
		while(dspaceManager.getCollection("55") == null);
		while(dspaceManager.getAllItems("55") == null);

		//Bitstreams are loaded from dspace
		while(dspaceManager.getAllBitStreams(null,"test", "test", "55", "9595") == null);

		//Check null cases
		assertNull(dspaceManager.getBitStream("55", "9595", null));
		assertNull(dspaceManager.getBitStream("55", null, "19490"));
		assertNull(dspaceManager.getBitStream(null, "9595", "19490"));
		assertNull(dspaceManager.getBitStream(null, null, null));

		assertNotNull(dspaceManager.getBitStream("55", "9595", "19490"));

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

}
