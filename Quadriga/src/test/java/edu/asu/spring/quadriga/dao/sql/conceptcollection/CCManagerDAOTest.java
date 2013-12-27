package edu.asu.spring.quadriga.dao.sql.conceptcollection;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.conceptcollection.CCManagerDAO;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class CCManagerDAOTest {
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
	@Qualifier("cCManagerDAO")
	IDBConnectionCCManager dbConnect;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[6];
		
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_conceptcollection VALUES('COLL_1','COLL_1_DESC','COLL_1','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[4] = "INSERT INTO tbl_project_conceptcollection VALUES('PROJ_2','COLL_1',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[5] = "INSERT INTO tbl_conceptcollection_items VALUES('COLL_1','LEMMA','ITEM_1','POS','DESC',NOW(),NOW())";
				
		for(String query : databaseQuery)
		{
			((CCManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		
		String[] databaseQuery = new String[5];
		databaseQuery[0] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		databaseQuery[1] = "DELETE FROM tbl_project_conceptcollection WHERE projectid IN ('PROJ_2')";
		databaseQuery[2] = "DELETE FROM tbl_conceptcollection_items WHERE conceptcollectionid IN ('COLL_1')";
		databaseQuery[3] = "DELETE FROM tbl_conceptcollection WHERE conceptcollectionid IN ('COLL_1')";
		databaseQuery[4] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2')";
		
		for(String query : databaseQuery)
		{
			((CCManagerDAO)dbConnect).setupTestEnvironment(query);
		}	
	}
	
	@Test
	@Transactional
	public void testGetConceptsOwnedbyUser() throws QuadrigaStorageException
	{
		List<IConceptCollection> conceptCollectionList = dbConnect.getConceptsOwnedbyUser("projuser");
		assertEquals(1, conceptCollectionList.size());
	}

}
