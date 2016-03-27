//package edu.asu.spring.quadriga.dspace.service.impl;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.Properties;
//
//import javax.annotation.Resource;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.client.RestTemplate;
//
//import edu.asu.spring.quadriga.domain.dspace.ICollection;
//import edu.asu.spring.quadriga.domain.dspace.ICommunity;
//import edu.asu.spring.quadriga.domain.factories.ICollectionFactory;
//import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
//import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
//
///**
// * This class tests {@link ProxyCommunityManager}
// * 
// * @author Ram Kumar Kumaresan
// *
// */
//@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
//		"file:src/test/resources/root-context.xml","file:src/test/resources/rest-service.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ProxyCommunityManagerTest {
//
//	private ICommunityManager proxyCommunityManager;
//
//	@Autowired
//	@Qualifier("restTemplate")
//	private RestTemplate restTemplate;
//
//	@Resource(name = "dspaceStrings")
//	private Properties dspaceProperties;
//
//	private DspaceKeys dspaceKeys;
//	
//	@Autowired
//	private ICollectionFactory collectionFactory;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		proxyCommunityManager = new ProxyCommunityManager();
//		proxyCommunityManager.setCollectionFactory(collectionFactory);
//		
//		dspaceKeys = new DspaceKeys();
//		dspaceKeys.setPublicKey("b459689e");
//		dspaceKeys.setPrivateKey("12cabcca2128e67e");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testGetAllCommunities() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Test is an invalid user. DspaceKeys are used if available and the username/password are ignored.
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, "test", "test"));
//
//		proxyCommunityManager.clearCompleteCache();
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, null, "test"));
//
//		proxyCommunityManager.clearCompleteCache();
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, null, "test", null));
//	}
//
//	@Test
//	public void testGetAllCollections() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//
//		//Get the collection list for the community
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		assertNotNull(proxyCommunityManager.getCollectionName("10"));
//
//		//Invalid community id
//		assertNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "11111111111111111"));
//
//		//Handle null case for community id
//		assertNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, null));
//	}
//
//	@Test
//	public void testGetAllItems() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		assertNotNull(proxyCommunityManager.getCollectionName("10"));
//
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		//Wait for items to load
//		while(collection.getLoadStatus() == false);
//
//		//Valid collection id
//		assertNotNull(proxyCommunityManager.getAllItems("10"));
//
//		//Invalid collection id
//		assertNull(proxyCommunityManager.getAllItems("1111111111111"));
//
//		//Handle null case for collection id
//		assertNull(proxyCommunityManager.getAllItems(null));
//	}
//
//	@Test
//	public void testGetCollection() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		assertNotNull(proxyCommunityManager.getCollectionName("10"));
//
//		//Test case to load collection from cache
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		assertNotNull(collection.getNetworkName());
//		
//		//Invalid collection id
//		assertNull(proxyCommunityManager.getCollection("111111111111", true, null, null, null, null, null, "12"));
//		//Null collection id
//		assertNull(proxyCommunityManager.getCollection(null, true, null, null, null, null, null, "12"));
//
//		//Invalid community id
//		assertNotNull(proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "111111111111111"));
//		//Null community id
//		assertNotNull(proxyCommunityManager.getCollection("10", true, null, null, null, null, null, null));
//		
//		//Test case to handle reload of collection
//		collection = proxyCommunityManager.getCollection("10",false,restTemplate,dspaceProperties,dspaceKeys,null,null,"12");
//		//A new REST Call would be made and hence thread takes time to load the collection name.
//		assertNull(collection.getNetworkName());
//	}
//
//	@Test
//	public void testGetCommunityName() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community details
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//
//		assertNotNull(proxyCommunityManager.getCommunityName("12"));
//		assertNull(proxyCommunityManager.getCommunityName("11111111111111"));
//		assertNull(proxyCommunityManager.getCommunityName(null));
//	}
//
//	@Test
//	public void testGetCollectionName() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//
//		assertNotNull(proxyCommunityManager.getCollectionName("10"));
//		assertNull(proxyCommunityManager.getCollectionName(null));
//		assertNull(proxyCommunityManager.getCollectionName("11111111111111111"));
//
//	}
//
//	@Test
//	public void testGetCommunityId() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//
//		assertEquals("12",proxyCommunityManager.getCommunityId("10"));
//		assertNull(proxyCommunityManager.getCommunityId(null));
//	}
//
//	@Test
//	public void testGetItemName() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		proxyCommunityManager.clearCompleteCache();
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load the items
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		while(collection.getLoadStatus() == false);
//
//		assertNotNull(proxyCommunityManager.getItemName("10", "1217"));
//		assertNull(proxyCommunityManager.getItemName("10", null));
//		assertNull(proxyCommunityManager.getItemName("10", "11111111111111111"));
//		assertNull(proxyCommunityManager.getItemName("10", ""));
//		assertNull(proxyCommunityManager.getItemName(null, "1217"));
//		assertNull(proxyCommunityManager.getItemName("1111111111111111", "1217"));
//	}
//
//	@Test
//	public void testGetBitStream() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		while(collection.getLoadStatus() == false);
//
//		//Load the bitstreams in the item
//		assertNotNull(proxyCommunityManager.getAllBitStreams("10", "1217"));
//
//		//Check null case
//		assertNull(proxyCommunityManager.getBitStream("10", "1217", null));
//		assertNull(proxyCommunityManager.getBitStream("10", null, "3991"));
//		assertNull(proxyCommunityManager.getBitStream(null, "1217", "3991"));
//		assertNull(proxyCommunityManager.getBitStream(null, null, null));
//
//		assertNotNull(proxyCommunityManager.getBitStream("10", "1217", "3991"));
//	}
//
//	@Test
//	public void testGetAllBitStreams() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		while(collection.getLoadStatus() == false);
//
//		//Load the bitstreams in the item
//		assertNotNull(proxyCommunityManager.getAllBitStreams("10", "1217"));
//
//		//Invalid cases
//		assertNull(proxyCommunityManager.getAllBitStreams("", ""));
//		assertNull(proxyCommunityManager.getAllBitStreams("111111111111", "1217"));
//		assertNull(proxyCommunityManager.getAllBitStreams("10", "11111111111111111111"));
//
//		//Check null case
//		assertNull(proxyCommunityManager.getAllBitStreams("10", null));
//		assertNull(proxyCommunityManager.getAllBitStreams(null, "1217"));
//		assertNull(proxyCommunityManager.getAllBitStreams(null, null));
//
//	}
//
//	@Test
//	public void testGetCommunity() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//
//		//Load from cache
//		assertNotNull(proxyCommunityManager.getCommunity("12", true, null, null, null, null, null));
//		//Invalid id
//		assertNull(proxyCommunityManager.getCommunity("111111111111111111", true, null, null, null, null, null));
//		//Null case
//		assertNull(proxyCommunityManager.getCommunity(null, true, null, null, null, null, null));
//
//		//Reload value
//		ICommunity community = proxyCommunityManager.getCommunity("12", false, restTemplate, dspaceProperties, dspaceKeys, null, null);
//		assertNotNull(community);
//	}
//
//	@Test
//	public void testGetItem() throws NoSuchAlgorithmException, QuadrigaAccessException {
//		//Load the community and collection
//		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, null, null));
//		assertNotNull(proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, null, null, "12"));
//
//		//Wait for the collection to load
//		while(proxyCommunityManager.getCollectionName("10")==null);
//		ICollection collection = proxyCommunityManager.getCollection("10", true, null, null, null, null, null, "12");
//		while(collection.getLoadStatus() == false);
//
//		assertNotNull(proxyCommunityManager.getItem("10", "1217"));
//		assertNull(proxyCommunityManager.getItem("10", null));
//		assertNull(proxyCommunityManager.getItem(null, "1217"));
//		assertNull(proxyCommunityManager.getItem("", ""));
//		assertNull(proxyCommunityManager.getItem(null, null));
//	}
//
//}
