//package edu.asu.spring.quadriga.domain.implementation;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.domain.impl.networks.Network;
//
///*
//* @Description 	: tests getters and setters for appelationId, relationId, creator, creationTime and
//* 				  textUrl
//* 
//* @author		: Rohit Pendbhaje
//* 
//*/
//
//public class NetworkTest {
//	
//	private Network network;
//	private IUser creator;
//	private Date creationTime;
//	private List<String> appellationIds;
//	private List<String> relationIds;
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
//		
//		this.network = new Network();
//		this.creator = new User();
//		
//		this.appellationIds = new ArrayList<String>();
//		appellationIds.add("3333");
//		appellationIds.add("4444");
//		
//		this.relationIds = new ArrayList<String>();
//		relationIds.add("123");
//		relationIds.add("567");
//		
//		this.creationTime = new Date();
//		
//			
//	}
//	
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testGetAppellationIds() {
//		
//		network.setAppellationIds(null);
//		assertEquals(network.getAppellationIds(), null);
//		
//		network.setAppellationIds(appellationIds);
//		assertEquals(network.getAppellationIds(), appellationIds);
//		
//	}
//
//	
//	@Test
//	public void testGetRelationIds() {
//		
//		network.setRelationIds(null);
//		assertEquals(network.getRelationIds(), null);
//		
//		network.setRelationIds(relationIds);
//		assertEquals(network.getRelationIds(), relationIds);
//		
//	}
//
//	
//	@Test
//	public void testGetCreator() {
//		
//		network.setCreator(null);
//		assertEquals(network.getCreator(), null);
//		
//		network.setCreator(creator);
//		assertEquals(network.getCreator(), creator);
//	}
//
//	
//	@Test
//	public void testGetCreationTime() {
//		
//		network.setCreationTime(null);
//		assertEquals(network.getCreationTime(), null);
//		
//		network.setCreationTime(creationTime);
//		assertEquals(network.getCreationTime(), creationTime);			
//	}
//
//	
//	@Test
//	public void testGetTextUrl() {
//		
//		network.setTextUrl(null);
//		assertEquals(network.getTextUrl(), null);
//		
//		network.setTextUrl("http://www.lsa.com");
//		assertEquals(network.getTextUrl(),"http://www.lsa.com");
//		
//	}
//
//	
//}
