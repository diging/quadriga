package edu.asu.spring.quadriga.dao.dictionary;

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

import edu.asu.spring.quadriga.dao.conceptcollection.ConceptCollectionCollaboratorManagerDAO;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class DictionaryCollaboratorManagerDAOTest {

	
	@Autowired
	private IDBConnectionDictionaryCollaboratorManager dbConnect;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		String[] databaseQuery = new String[8];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject3','test case data','testproject3','PROJ_3','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project VALUES('testproject4','test case data','testproject4','PROJ_4','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[4] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project VALUES('testproject5','test case data','testproject5','PROJ_5','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_5','projcollab','collaborator_role3','projcollab',NOW(),'projcollab',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_conceptcollection VALUES ('Hibernate Test','Hibernate Test','37ad9abc-9e88-4d55-8a98-fac829a583f9','projuser','0','projuser',NOW(),'projuser',NOW())";
		
		for(String query : databaseQuery)
		{
			((ConceptCollectionCollaboratorManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}
	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void updateCollaboratorRoles() throws QuadrigaStorageException 
	{
		/*dbConnect.updateCollaboratorRoles(dictionaryId, collabUser, collaboratorRole, username);
		dictionaryManagerDAO.getDictionaryCollabOfUser(userId)*/
	}

}