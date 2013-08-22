package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;

/**
 * This class tests {@link ProxyCommunityManager}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml","file:src/test/resources/rest-service.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyCommunityManagerTest {

	private ICommunityManager proxyCommunityManager;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Resource(name = "dspaceStrings")
	private Properties dspaceProperties;

	private DspaceKeys dspaceKeys;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		proxyCommunityManager = new ProxyCommunityManager();
		dspaceKeys = new DspaceKeys();
		dspaceKeys.setPublicKey("b459689e");
		dspaceKeys.setPrivateKey("12cabcca2128e67e");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllCommunities() throws NoSuchAlgorithmException {
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));

		proxyCommunityManager.clearCompleteCache();
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, null, "test"));

		proxyCommunityManager.clearCompleteCache();
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", null));
	}

	@Test
	public void testGetAllCollections() throws NoSuchAlgorithmException {
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));

		//Get the collection list for the community
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, "test", "test", "12"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("10")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("10"));

		//Handle null case for community id
		assertNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, "test", "test", null));
	}

	@Test
	public void testGetAllItems() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, "test", "test", "12"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("10")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("10"));
		
		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
		//Wait for items to load
		while(collection.getLoadStatus() == false);
		
		//Valid collection id
		assertNotNull(proxyCommunityManager.getAllItems("10"));

		//Invalid collection id
		assertNull(proxyCommunityManager.getAllItems("1111111111111"));

		//Handle null case for collection id
		assertNull(proxyCommunityManager.getAllItems(null));
	}

	@Test
	public void testGetCollection() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, "test", "test", "12"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("10")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("10"));

		//Test case to load collection from cache
		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
		assertNotNull(collection.getName());

		//Test case to handle reload of collection
		collection = proxyCommunityManager.getCollection("10",false,restTemplate,dspaceProperties,dspaceKeys,"test","test","12");
		//A new REST Call would be made and hence thread takes time to load the collection name.
		assertNull(collection.getName());
	}

	@Test
	public void testGetCommunityName() throws NoSuchAlgorithmException {
		//Load the community details
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));
		
		assertNotNull(proxyCommunityManager.getCommunityName("12"));
		assertNull(proxyCommunityManager.getCommunityName("11111111111111"));
		assertNull(proxyCommunityManager.getCommunityName(null));
	}

	@Test
	public void testGetCollectionName() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);

		assertNotNull(proxyCommunityManager.getCollectionName("55"));
		assertNull(proxyCommunityManager.getCollectionName(null));
	}

	@Test
	public void testGetCommunityId() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);

		assertEquals("18",proxyCommunityManager.getCommunityId("55"));
		assertNull(proxyCommunityManager.getCommunityId(null));
	}

	@Test
	public void testGetItemName() throws NoSuchAlgorithmException {
		//Load the community and collection
		proxyCommunityManager.clearCompleteCache();
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load the items
		while(proxyCommunityManager.getCollectionName("55")==null);
		ICollection collection = proxyCommunityManager.getCollection("55", true, null, null, null, null, null, "18");
		while(collection.getItems() == null);
		while(collection.getItems().size() != Integer.parseInt(collection.getCountItems()));

		assertNull(proxyCommunityManager.getItemName("55", null));
		assertNotNull(proxyCommunityManager.getItemName("55", "9595"));
	}

	@Test
	public void testGetBitStream() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		ICollection collection = proxyCommunityManager.getCollection("55", true, null, null, null, null, null, "18");
		while(collection.getItems() == null);
		while(collection.getItems().size() != Integer.parseInt(collection.getCountItems()));

		//Load the bitstreams in the item
		assertNotNull(proxyCommunityManager.getAllBitStreams("55", "9595"));

		//Check null case
		assertNull(proxyCommunityManager.getBitStream("55", "9595", null));
		assertNull(proxyCommunityManager.getBitStream("55", null, "19490"));
		assertNull(proxyCommunityManager.getBitStream(null, "9595", "19490"));
		assertNull(proxyCommunityManager.getBitStream(null, null, null));

		assertNotNull(proxyCommunityManager.getBitStream("55", "9595", "19490"));
	}

	@Test
	public void testGetAllBitStreams() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		ICollection collection = proxyCommunityManager.getCollection("55", true, null, null, null, null, null, "18");
		while(collection.getItems() == null);
		while(collection.getItems().size() != Integer.parseInt(collection.getCountItems()));

		//Load the bitstreams in the item
		assertNotNull(proxyCommunityManager.getAllBitStreams("55", "9595"));

		//Check null case
		assertNull(proxyCommunityManager.getAllBitStreams("55", null));
	}

	@Test
	public void testGetCommunity() throws NoSuchAlgorithmException {
		//Load the community
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));

		//Load from cache
		assertNotNull(proxyCommunityManager.getCommunity("18", true, null, null, null, null, null));

		//Reload value
		ICommunity community = proxyCommunityManager.getCommunity("18", false, restTemplate, dspaceProperties, null, "test","test");
		assertNotNull(community);
	}

	@Test
	public void testGetItem() throws NoSuchAlgorithmException {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		ICollection collection = proxyCommunityManager.getCollection("55", true, null, null, null, null, null, "18");
		while(collection.getItems() == null);
		while(collection.getItems().size() != Integer.parseInt(collection.getCountItems()));

		assertNotNull(proxyCommunityManager.getItem("55", "9595"));
		assertNull(proxyCommunityManager.getItem("55", null));		
	}

}
