package edu.asu.spring.quadriga.web.conceptcollection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.CollectionsValidator;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.web.dictionary.DictionaryListControllerTest;
import edu.asu.spring.quadriga.web.login.RoleNames;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConceptcollectionControllerTest {

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired
	IConceptCollectionFactory conceptCollectionFactory;

	private Connection connection;

	private IUser user;

	ConceptcollectionController ccController;

	@Autowired
	IUserManager userManager;

	@Autowired
	IUserFactory userFactory;

	@Autowired
	IConceptFactory conceptFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	IDBConnectionCCManager dbConnection;

	MockHttpServletRequest mock;

	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;
	HttpServletRequest req;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DictionaryListControllerTest.class);

	String sDatabaseSetup [];

	@Autowired
	private CollectionsValidator validator;

	@Before
	public void setUp() throws Exception {
		ccController = new ConceptcollectionController();
		ccController.setCollectionFactory(conceptCollectionFactory);
		ccController.setConceptControllerManager(conceptCollectionManager);
		ccController.setConceptFactory(conceptFactory);
		ccController.setUsermanager(userManager);
		ccController.setValidator(validator);

		model =  new BindingAwareModelMap();	
		mock = new MockHttpServletRequest();

		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		//Setup a user object to compare with the object produced from usermanager
		user = userFactory.createUserObject();
		user.setUserName("jdoe");
		user.setName("John Doe");

		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role4");
		roles.add(role);

		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		for(int i=0;i<roles.size();i++)
		{
			quadrigaRole = rolemanager.getQuadrigaRole(roles.get(i).getDBid());

			//If user account is deactivated remove other roles 
			if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
			{
				rolesList.clear();
			}
			rolesList.add(quadrigaRole);
		}
		user.setQuadrigaRoles(rolesList);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = new String[]{
				"delete from tbl_conceptcollection_collaborator",
				"delete from tbl_conceptcollection_items",
				"delete from tbl_conceptcollection",						
				"delete from tbl_quadriga_user",
				"delete from tbl_quadriga_user_requests",
				"delete from tbl_quadriga_user_denied",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",

		};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConceptCollectionHandler() throws QuadrigaStorageException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);

		assertEquals(ccController.conceptCollectionHandler(model,principal),"auth/conceptcollections");
		String userName = null;
		if(model.containsAttribute("username")){
			userName = (String) model.get("username");
		}
		assertEquals(userName, "jdoe");
		List<IConceptCollection> list= (List<IConceptCollection>) model.get("conceptlist");
		assertEquals(list.size(), 1);
	}

	@Test
	public void testConceptDetailsHandler() throws QuadrigaStorageException, QuadrigaAccessException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);
		String ccId = conceptCollectionManager.getConceptCollectionId("test");
		assertEquals(ccController.conceptDetailsHandler(ccId, model, principal),"auth/conceptcollections/details");
		String ccIdCheck = null;
		if(model.containsAttribute("collectionid")){
			ccIdCheck = (String) model.get("collectionid");
		}
		assertEquals(ccIdCheck, ccId);
		IConceptCollection concept = null;
		
		if(model.containsAttribute("concept")){
			concept = (IConceptCollection) model.get("concept");
		}
		
		assertEquals(concept.getName(), "test");
		
		List<ICollaborator> collaboratingUsers = null;
		
		if(model.containsAttribute("collaboratingUsers")){
			collaboratingUsers = (List<ICollaborator>) model.get("collaboratingUsers");
		}
		assertEquals(collaboratingUsers.size(), 0);
	}

	@Test
	public void testConceptSearchHandler() throws QuadrigaStorageException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);
		String ccId = conceptCollectionManager.getConceptCollectionId("test");
		mock.addParameter("name", "dog");
		mock.addParameter("pos", "noun");
		
		assertEquals(ccController.conceptSearchHandler(ccId, mock, model), "auth/searchitems");
		List<ConceptpowerReply.ConceptEntry> result = null;
		if(model.containsAttribute("result")){
			result = (List<ConceptpowerReply.ConceptEntry>) model.get("result");
		}
		assertEquals(result.size() > 1, true);
		
		String ccIdCheck = null;
		if(model.containsAttribute("collectionid")){
			ccIdCheck = (String) model.get("collectionid");
		}
		assertEquals(ccIdCheck,ccId);
		
	}

	@Test
	public void testSaveItemsHandler() throws QuadrigaStorageException, QuadrigaAccessException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);
		assertEquals(ccController.conceptCollectionHandler(model,principal),"auth/conceptcollections");
		String ccId = conceptCollectionManager.getConceptCollectionId("test");
		assertEquals(ccController.conceptDetailsHandler(ccId, model, principal),"auth/conceptcollections/details");
		mock.addParameter("name", "dog");
		mock.addParameter("pos", "noun");
		
		assertEquals(ccController.conceptSearchHandler(ccId, mock, model), "auth/searchitems");
		ConceptpowerReply c1 = ccController.getC();
		String id = c1.getConceptEntry().get(1).getId();
		mock.setParameter("selected", id);
		
		assertEquals(ccController.saveItemsHandler(ccId, mock, model, principal), "redirect:/auth/conceptcollections/"+ccId);
		
	}

	@Test
	public void testAddCollectionsForm() {
		ModelAndView modelAndView = ccController.addCollectionsForm();
		assertEquals(modelAndView.getViewName(), "auth/conceptcollections/addCollectionsForm");
	}

	@Test
	public void testAddConceptCollection() throws QuadrigaStorageException {
		IConceptCollection temp = conceptCollectionFactory.createConceptCollectionObject();
		ConceptCollection c = (ConceptCollection) temp;
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		BindingResult result = mock(BindingResult.class);
		ModelAndView modelAndView = ccController.addConceptCollection(c, result, model, principal);
		assertEquals(modelAndView.getViewName(),"redirect:/auth/conceptcollections");
	}

	@Test
	public void testDeleteItems() throws QuadrigaStorageException, QuadrigaAccessException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);
		assertEquals(ccController.conceptCollectionHandler(model,principal),"auth/conceptcollections");
		String ccId = conceptCollectionManager.getConceptCollectionId("test");
		assertEquals(ccController.conceptDetailsHandler(ccId, model, principal),"auth/conceptcollections/details");
		mock.addParameter("name", "dog");
		mock.addParameter("pos", "noun");
		
		assertEquals(ccController.conceptSearchHandler(ccId, mock, model), "auth/searchitems");
		ConceptpowerReply c1 = ccController.getC();
		ConceptEntry temp = new ConceptEntry();
		mock.setParameter("selected", c1.getConceptEntry().get(1).getId());
		assertEquals(ccController.saveItemsHandler(ccId, mock, model, principal), "redirect:/auth/conceptcollections/"+ccId);
		
		IConceptCollection cTest = ccController.getCollection();
		IConcept concept = conceptFactory.createConceptObject();
		ConceptpowerReply.ConceptEntry conceptEntry = c1.getConceptEntry().get(1);
		concept.setDescription(conceptEntry.getDescription());
		concept.setId(conceptEntry.getId());
		concept.setLemma(conceptEntry.getLemma());
		concept.setPos(conceptEntry.getPos());
		cTest.getItems().add(concept);
		ccController.setCollection(cTest);
		assertEquals(ccController.deleteItems(mock, model, principal), "redirect:/auth/conceptcollections/"+ccId);
	}

	@Test
	public void testConceptUpdateHandler() throws QuadrigaStorageException, QuadrigaAccessException {
		IConceptCollection c = conceptCollectionFactory.createConceptCollectionObject();
		c.setName("test");
		c.setDescription("Description ");
		c.setOwner(user);
		conceptCollectionManager.addConceptCollection(c);
		assertEquals(ccController.conceptCollectionHandler(model,principal),"auth/conceptcollections");
		String ccId = conceptCollectionManager.getConceptCollectionId("test");
		assertEquals(ccController.conceptDetailsHandler(ccId, model, principal),"auth/conceptcollections/details");
		mock.addParameter("name", "dog");
		mock.addParameter("pos", "noun");
		
		assertEquals(ccController.conceptSearchHandler(ccId, mock, model), "auth/searchitems");
		ConceptpowerReply c1 = ccController.getC();
		ConceptEntry temp = new ConceptEntry();
		mock.setParameter("selected", c1.getConceptEntry().get(1).getId());
		IConceptCollection cTest = ccController.getCollection();
		IConcept concept = conceptFactory.createConceptObject();
		ConceptpowerReply.ConceptEntry conceptEntry = c1.getConceptEntry().get(1);
		concept.setDescription(conceptEntry.getDescription());
		concept.setId(conceptEntry.getId());
		concept.setLemma(conceptEntry.getLemma());
		concept.setPos(conceptEntry.getPos());
		cTest.getItems().add(concept);
		ccController.setCollection(cTest);
		assertEquals(ccController.conceptUpdateHandler(mock, model, principal), "redirect:/auth/conceptcollections/"+ccId);
	}

}

