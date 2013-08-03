package edu.asu.spring.quadriga.dspace.service.impl;

import static org.junit.Assert.*;

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

import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;

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
		assertNotNull(proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, "test", "test"));
	}

	@Ignore
	@Test
	public void testGetAllCollections() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetAllItems() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCollection() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCommunityName() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCollectionName() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCommunityId() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetItemName() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetBitStream() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetAllBitStreams() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCommunity() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetItem() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testClearCompleteCache() {
		fail("Not yet implemented");
	}

}
