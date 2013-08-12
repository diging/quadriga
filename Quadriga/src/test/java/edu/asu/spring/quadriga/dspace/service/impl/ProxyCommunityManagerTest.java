package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		proxyCommunityManager = new ProxyCommunityManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllCommunities() {
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));

		proxyCommunityManager.clearCompleteCache();
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, null, "test"));

		proxyCommunityManager.clearCompleteCache();
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", null));
	}

	@Test
	public void testGetAllCollections() {
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));

		//Get the collection list for the community
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("55"));

		//Handle null case for community id
		assertNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", null));
	}

	@Test
	public void testGetAllItems() {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("55"));

		//Handle null case for collection id
		assertNull(proxyCommunityManager.getAllItems(null));

		assertNotNull(proxyCommunityManager.getAllItems("55"));		

	}

	@Test
	public void testGetCollection() {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);
		assertNotNull(proxyCommunityManager.getCollectionName("55"));

		//Test case to load collection from cache
		ICollection collection = proxyCommunityManager.getCollection("55", true, null, null, null, null, null, "18");
		assertNotNull(collection.getName());

		//Test case to handle reload of collection
		collection = proxyCommunityManager.getCollection("55",false,restTemplate,dspaceProperties,null,"test","test","18");
		//A new REST Call would be made and hence thread takes time to load the collection name.
		assertNull(collection.getName());
	}

	@Test
	public void testGetCommunityName() {
		//Load the community details
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getCommunityName("18"));

		assertNull(proxyCommunityManager.getCommunityName(null));
	}

	@Test
	public void testGetCollectionName() {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);

		assertNotNull(proxyCommunityManager.getCollectionName("55"));
		assertNull(proxyCommunityManager.getCollectionName(null));
	}

	@Test
	public void testGetCommunityId() {
		//Load the community and collection
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, null, "test", "test", "18"));

		//Wait for the collection to load
		while(proxyCommunityManager.getCollectionName("55")==null);

		assertEquals("18",proxyCommunityManager.getCommunityId("55"));
		assertNull(proxyCommunityManager.getCommunityId(null));
	}

	@Test
	public void testGetItemName() {
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
	public void testGetBitStream() {
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
	public void testGetAllBitStreams() {
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
	public void testGetCommunity() {
		//Load the community
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", "test"));
		
		//Load from cache
		assertNotNull(proxyCommunityManager.getCommunity("18", true, null, null, null, null, null));

		//Reload value
		ICommunity community = proxyCommunityManager.getCommunity("18", false, restTemplate, dspaceProperties, null, "test","test");
		assertNotNull(community);
	}

	@Test
	public void testGetItem() {
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
