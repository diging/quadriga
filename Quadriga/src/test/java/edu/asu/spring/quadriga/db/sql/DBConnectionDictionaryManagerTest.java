package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;


/**
 * This class tests the {@link DBConnectionDictionaryManager}. 
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_dictionary
 * 			  tbl_dictionary_items
 * 
 * @author      Lohith Dwaraka
 *
 * 
 *
 */

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml","file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionDictionaryManagerTest {

	private Connection connection;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryManager.class);
	
	@Autowired
	private IDictionaryFactory dictionaryFactory;

	@Autowired
	private IDictionaryItemsFactory dictionaryItemsFactory;

	@Test
	public void getDictionaryOfUserTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addDictionaryTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void getDictionaryItemsDetailsTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void getDictionaryNameTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addDictionaryItemsTest() {
		fail("Not yet implemented");
	}
}
