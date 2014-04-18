package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.impl.UserFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })

@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryItemsTest {

	private Item dictionaryItems;
	private IUser owner;
	
	@Autowired
	UserFactory userFactory;
	
	@Autowired
	DictionaryItemsFactory dictionaryItemsFactory;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//this.dictionaryItems =  dictionaryItemsFactory.createDictionaryItemsObject();
		this.dictionaryItems = new Item();
		this.owner = userFactory.createUserObject();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetItems() {
		dictionaryItems.setTerm(null);
		assertEquals(dictionaryItems.getTerm(), null);
		
		dictionaryItems.setTerm("horse");
		assertEquals(dictionaryItems.getTerm(),"horse");		
	}

	@Test
	public void testSetItems() {
		dictionaryItems.setTerm(null);
		assertEquals(dictionaryItems.getTerm(), null);
		
		dictionaryItems.setTerm("horse");
		assertEquals(dictionaryItems.getTerm(),"horse");	
	}

	@Test
	public void testGetDictionaryId() {
//		dictionaryItems.setDictionaryId(null);
//		assertEquals(dictionaryItems.getDictionaryId(), null);
		
//		dictionaryItems.setDictionaryId("id1");
//		assertEquals(dictionaryItems.getDictionaryId(),"id1");	
	}

	@Test
	public void testSetDictionaryId() {
//		dictionaryItems.setDictionaryId(null);
//		assertEquals(dictionaryItems.getDictionaryId(), null);
//		
//		dictionaryItems.setDictionaryId("id1");
//		assertEquals(dictionaryItems.getDictionaryId(),"id1");	
	}

	@Test
	public void testGetId() {
		dictionaryItems.setDictionaryItemId(null);
		assertEquals(dictionaryItems.getDictionaryItemId(), null);
		
		dictionaryItems.setDictionaryItemId("id1");
		assertEquals(dictionaryItems.getDictionaryItemId(),"id1");	
	}

	@Test
	public void testSetId() {
		dictionaryItems.setDictionaryItemId(null);
		assertEquals(dictionaryItems.getDictionaryItemId(), null);
		
		dictionaryItems.setDictionaryItemId("id1");
		assertEquals(dictionaryItems.getDictionaryItemId(),"id1");	
	}

	@Test
	public void testGetPos() {
		dictionaryItems.setPos(null);
		assertEquals(dictionaryItems.getPos(), null);
		
		dictionaryItems.setPos("noun");
		assertEquals(dictionaryItems.getPos(),"noun");	
	}

	@Test
	public void testSetPos() {
		dictionaryItems.setPos(null);
		assertEquals(dictionaryItems.getPos(), null);
		
		dictionaryItems.setPos("noun");
		assertEquals(dictionaryItems.getPos(),"noun");
	}

	@Test
	public void testGetVocabulary() {
		dictionaryItems.setVocabulary(null);
		assertEquals(dictionaryItems.getVocabulary(), null);
		
		dictionaryItems.setVocabulary("wordnet");
		assertEquals(dictionaryItems.getVocabulary(),"wordnet");
	}

	@Test
	public void testSetVocabulary() {
		dictionaryItems.setVocabulary(null);
		assertEquals(dictionaryItems.getVocabulary(), null);
		
		dictionaryItems.setVocabulary("wordnet");
		assertEquals(dictionaryItems.getVocabulary(),"wordnet");
	}

	@Test
	public void testGetDescription() {
		dictionaryItems.setDescription(null);
		assertEquals(dictionaryItems.getDescription(), null);
		
		dictionaryItems.setDescription("word description");
		assertEquals(dictionaryItems.getDescription(),"word description");
	}

	@Test
	public void testSetDescription() {
		dictionaryItems.setDescription(null);
		assertEquals(dictionaryItems.getDescription(), null);
		
		dictionaryItems.setDescription("word description");
		assertEquals(dictionaryItems.getDescription(),"word description");
	}

}