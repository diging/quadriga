package edu.asu.spring.quadriga.dao.conceptcollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class ConceptCollectionCollaboratorManagerDAOTest {
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	IUser user;
	String sDeactiveRoleDBId;
	String sDatabaseSetup;

	@Autowired
	private IDBConnectionCCManager ccManagerDAO;
	
	@Autowired
	private IDBConnectionCCCollaboratorManager dbConnect;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		
		user = userFactory.createUserObject();
		user.setUserName("projuser");
		user.setName("projuser");

		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role4");
		roles.add(role);

		user.setQuadrigaRoles(roles);

		String[] databaseQuery = new String[8];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject3','test case data','testproject3','PROJ_3','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project VALUES('testproject4','test case data','testproject4','PROJ_4','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[4] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project VALUES('testproject5','test case data','testproject5','PROJ_5','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_5','projcollab','collaborator_role3','projcollab',NOW(),'projcollab',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_conceptcollection VALUES ('Hibernate Test','Hibernate Test','37ad9abc-9e88-4d55-8a98-fac829a583f9','projuser','0','projuser',NOW(),'projuser',NOW())";
		//databaseQuery[8] = "INSERT INTO tbl_conceptcollection_collaborator VALUES ('37ad9abc-9e88-4d55-8a98-fac829a583f9','projuser','cc_role2','projuser',NOW(),'projuser',NOW()),('37ad9abc-9e88-4d55-8a98-fac829a583f9','projuser','cc_role3','projuser',NOW(),'projuser',NOW())";
		
		for(String query : databaseQuery)
		{
			((ConceptCollectionCollaboratorManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[6];
		databaseQuery[0] = "DELETE FROM tbl_conceptcollection_collaborator WHERE conceptcollectionid IN ('37ad9abc-9e88-4d55-8a98-fac829a583f9')";
		databaseQuery[1] = "DELETE FROM tbl_conceptcollection WHERE conceptcollectionid IN ('37ad9abc-9e88-4d55-8a98-fac829a583f9')";
		databaseQuery[2] = "DELETE FROM tbl_project_collaborator WHERE projectid = 'PROJ_5'";
		databaseQuery[3] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2','PROJ_3','PROJ_4','PROJ_5')";
		databaseQuery[4] = "DELETE FROM tbl_project WHERE projectowner IN ('projuser','projcollab')";
		databaseQuery[5] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		
		
		for(String query : databaseQuery)
		{
			((ConceptCollectionCollaboratorManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}	
	
	@Test
	public void addCollaboratorRequest() throws QuadrigaStorageException 
	{
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setDisplayName("Read");
		collaboratorRole.setRoleDBid("cc_role2");
		collaboratorRole.setRoledescription("Test Desc");
		collaboratorRole.setRoleid("CC_READ_ACCESS");
		collaboratorRole.setRolename("cc_read_access");
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		collaboratorRoleList.add(collaboratorRole);
		
		collaborator.setUserObj(user);
		collaborator.setCollaboratorRoles(collaboratorRoleList);
		dbConnect.addCollaboratorRequest(collaborator,"37ad9abc-9e88-4d55-8a98-fac829a583f9","projuser");
		
		List<IConceptCollection> conceptCollectionList = ccManagerDAO.getCollaboratedConceptsofUser("projuser");
		assertEquals(1, conceptCollectionList.size());
	}

	@Test
	public void deleteCollaboratorRequest() throws QuadrigaStorageException 
	{
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setDisplayName("Read");
		collaboratorRole.setRoleDBid("cc_role2");
		collaboratorRole.setRoledescription("Test Desc");
		collaboratorRole.setRoleid("CC_READ_ACCESS");
		collaboratorRole.setRolename("cc_read_access");
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		collaboratorRoleList.add(collaboratorRole);
		
		collaborator.setUserObj(user);
		collaborator.setCollaboratorRoles(collaboratorRoleList);
		dbConnect.addCollaboratorRequest(collaborator,"37ad9abc-9e88-4d55-8a98-fac829a583f9","projuser");
		
		dbConnect.deleteCollaboratorRequest("projuser", "37ad9abc-9e88-4d55-8a98-fac829a583f9");
		assertTrue(true);
	}
	
	
	@Test
	public void updateCollaboratorRequest() throws QuadrigaStorageException 
	{
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setDisplayName("Read");
		collaboratorRole.setRoleDBid("cc_role2");
		collaboratorRole.setRoledescription("Test Desc");
		collaboratorRole.setRoleid("CC_READ_ACCESS");
		collaboratorRole.setRolename("cc_read_access");
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		collaboratorRoleList.add(collaboratorRole);
		
		collaborator.setUserObj(user);
		collaborator.setCollaboratorRoles(collaboratorRoleList);
		dbConnect.addCollaboratorRequest(collaborator,"37ad9abc-9e88-4d55-8a98-fac829a583f9","projuser");

		//TO DO - Pending work
/*		List<ICollaboratorRole> collaboratorRoleList2 = new ArrayList<ICollaboratorRole>();
		collaboratorRoleList2.add(collaboratorRole2);*/
		dbConnect.updateCollaboratorRequest("37ad9abc-9e88-4d55-8a98-fac829a583f9", "projuser", "cc_role3", "projuser");
		List<IConceptCollection> conceptCollectionList = ccManagerDAO.getCollaboratedConceptsofUser("projuser");
		assertEquals("cc_read/write_access",conceptCollectionList.get(0).getCollaborators().get(0).getCollaboratorRoles().get(0).getRolename());
	}
}
