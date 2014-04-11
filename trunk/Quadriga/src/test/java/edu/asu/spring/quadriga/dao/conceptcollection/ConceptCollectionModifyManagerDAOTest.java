package edu.asu.spring.quadriga.dao.conceptcollection;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class ConceptCollectionModifyManagerDAOTest {
	
	@Autowired
	private IDBConnectionModifyCCManager dbConnect;
	
	@Autowired
	private IDBConnectionCCManager ccManagerDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {

		String[] databaseQuery = new String[3];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_conceptcollection VALUES ('Hibernate Test','Hibernate Test','37ad9abc-9e88-4d55-8a98-fac829a583f9','projuser','0','projuser',NOW(),'projuser',NOW())";
		
		for(String query : databaseQuery)
		{
			((ConceptCollectionModifyManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[3];
		databaseQuery[0] = "DELETE FROM tbl_conceptcollection_collaborator WHERE conceptcollectionid IN ('37ad9abc-9e88-4d55-8a98-fac829a583f9')";
		databaseQuery[1] = "DELETE FROM tbl_conceptcollection WHERE conceptcollectionid IN ('37ad9abc-9e88-4d55-8a98-fac829a583f9')";
		databaseQuery[2] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		
		for(String query : databaseQuery)
		{
			((ConceptCollectionModifyManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}	
	
	@Test
	public void transferCollectionOwnerRequest() throws QuadrigaStorageException
	{
		dbConnect.transferCollectionOwnerRequest("37ad9abc-9e88-4d55-8a98-fac829a583f9", "projuser", "projcollab", "cc_role2");
		List<IConceptCollection> conceptCollList = ccManagerDAO.getConceptsOwnedbyUser("projcollab");
		assertEquals(1, conceptCollList.size());
	}
	
	@Test
	public void updateCollectionDetails() throws QuadrigaStorageException
	{
		IConceptCollection conceptCollection = null;
				
		conceptCollection = ccManagerDAO.getConceptsOwnedbyUser("projuser").get(0);
		conceptCollection.setDescription("UPDATED_DESC");
		conceptCollection.setConceptCollectionName("UPDATED_NAME");
		dbConnect.updateCollectionDetails(conceptCollection, "projuser");
		
		conceptCollection = ccManagerDAO.getConceptsOwnedbyUser("projuser").get(0);
		assertEquals("UPDATED_NAME",conceptCollection.getConceptCollectionName());
	}
}
