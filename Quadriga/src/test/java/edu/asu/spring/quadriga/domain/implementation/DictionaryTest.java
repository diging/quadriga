//package edu.asu.spring.quadriga.domain.implementation;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import edu.asu.spring.quadriga.domain.ICollaborator;
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.domain.factory.impl.dictionary.DictionaryFactory;
//import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
//
///*
//* @Description 	: tests getters and setters for dictionary names, description, id, collaborators
//* 				  and owners
//* 
//* @author		: Rohit Pendbhaje
//* 
//*/
//@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
//"file:src/test/resources/root-context.xml" })
//
//@RunWith(SpringJUnit4ClassRunner.class)
//public class DictionaryTest {
//	
//	private Dictionary dictionary;
//	private List<ICollaborator> collaborators; 
//	private IUser owner;
//	@Autowired
//	DictionaryFactory dictionaryFactory;
//
//	
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
//		this.dictionary = (Dictionary) dictionaryFactory.createDictionaryObject();
//		this.owner = new User();
//		
//		this.collaborators = new ArrayList<ICollaborator>();
//		collaborators.add(new Collaborator());
//		collaborators.add(new Collaborator());
//		
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testGetName() {
//		
//		dictionary.setDictionaryName(null);
//		assertEquals(dictionary.getDictionaryName(), null);
//		
//		dictionary.setDictionaryName("jane");
//		assertEquals(dictionary.getDictionaryName(),"jane");
//		
//	}
//
//	
//	@Test
//	public void testGetDescription() {
//		
//		dictionary.setDescription(null);
//		assertEquals(dictionary.getDescription(), null);
//		
//		dictionary.setDescription("jane");
//		assertEquals(dictionary.getDescription(),"jane");
//		
//	}
//
//	
//	@Test
//	public void testGetId() {
//		
//		dictionary.setDictionaryId(null);
//		assertEquals(dictionary.getDictionaryId(), null);
//		
//		dictionary.setDictionaryId("2058");
//		assertEquals(dictionary.getDictionaryId(), "2058");
//		
//	}
//
//	
//	@Test
//	public void testGetOwner() {
//		
//		dictionary.setOwner(null);
//		assertEquals(dictionary.getOwner(), null);
//		
//		dictionary.setOwner(owner);
//		assertEquals(dictionary.getOwner(), owner);
//		
//		
//	}
//
//	
//	@Test
//	public void testGetCollaborators() {
//		
//		dictionary.setCollaborators(null);
//		assertEquals(dictionary.getCollaborators(), null);
//		
//		dictionary.setCollaborators(collaborators);
//		assertEquals(dictionary.getCollaborators(), collaborators);
//		
//	}
//
//	
//}
