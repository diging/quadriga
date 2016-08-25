package edu.asu.spring.quadriga.aspects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.util.ReflectionUtils;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factory.impl.conceptcollection.ConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.impl.ConceptCollectionManager;

public class ConceptCollectionRestAuthorizationTest {

    @Mock
    private ConceptCollectionManager mockedManager;
    
    private IConceptCollectionFactory factory;
    
    @InjectMocks
    private ConceptCollectionRestAuthorization authorization;
    
    private List<IConceptCollectionCollaborator> collaborators;
    
    @Before
    public void init() throws QuadrigaStorageException, QuadrigaAccessException {
        mockedManager = Mockito.mock(ConceptCollectionManager.class);
        factory = new ConceptCollectionFactory();
        
        MockitoAnnotations.initMocks(this);
        
        Field fieldToSet = ReflectionUtils.findField(ConceptCollectionRestAuthorization.class, "collectionFactory");
        ReflectionUtils.makeAccessible(fieldToSet);
        ReflectionUtils.setField(fieldToSet, authorization, factory);
        
        IConceptCollection collection = new ConceptCollection();
        final IUser owner = new User();
        owner.setUserName("testuser");
        collection.setOwner(owner);
        collection.setConceptCollectionId("collectionid");
        
        // create colaborator
        IConceptCollectionCollaborator ccCollaborator = new ConceptCollectionCollaborator();
        ICollaborator collaborator = new Collaborator();
        IUser collabUser = new User();
        collabUser.setUserName("collabuser");
        collaborator.setUserObj(collabUser);
        
        // create collaborator roles 
        IQuadrigaRole role = new QuadrigaRole();
        role.setId("role1");
        List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
        roles.add(role);
        collaborator.setCollaboratorRoles(roles);
        
        ccCollaborator.setCollaborator(collaborator);
        collaborators = new ArrayList<IConceptCollectionCollaborator>();
        
        collaborators.add(ccCollaborator);
        
        Mockito.when(mockedManager.showCollaboratingUsers("collectionid")).thenReturn(collaborators);
        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                IConceptCollection collection = (IConceptCollection) args[0];
                collection.setOwner(owner);
                return null;
            }
        
        }).when(mockedManager).fillConceptCollection(Mockito.isA(IConceptCollection.class));
      
        Mockito.when(mockedManager.getConceptCollection("collectionid")).thenReturn(collection);
        
    }
    
    @Test
    public void testChkAuthorizationIsOwner() throws QuadrigaStorageException, QuadrigaAccessException {
        // this should succeed because testuser is the owner of the collection
        boolean authorized = authorization.chkAuthorization("testuser", "collectionid", new String[] { "" });
        assertTrue(authorized);
    }
    
    @Test
    public void testChkAuthorizationHasValidRole() throws QuadrigaStorageException, QuadrigaAccessException {
        // this should succeed because collabuser is a collaborator and
        // has a role that has access to the collection
        boolean authorized = authorization.chkAuthorization("collabuser", "collectionid", new String[] { "role1" });
        assertTrue(authorized);
    }
    
    @Test
    public void testChkAuthorizationHasNoValidRole() throws QuadrigaStorageException, QuadrigaAccessException {
        // this should succeed because collabuser is a collaborator but
        // does not have a role that has access to the collection
        boolean authorized = authorization.chkAuthorization("collabuser", "collectionid", new String[] { "role2" });
        assertFalse(authorized);
    }
    
    @Test
    public void testChkAuthorizationIsNotCollaborator() throws QuadrigaStorageException, QuadrigaAccessException {
        // this should fail because testuser2 is not a collaborator
        boolean authorized = authorization.chkAuthorization("testuser2", "collectionid", new String[] { "role1" });
        assertFalse(authorized);
    }
}
