package edu.asu.spring.quadriga.aspects;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;

/**
 * Service class Implementation of {@link IAuthorizationManager} We can get
 * {@link IAuthorization} object for particular type like
 * {@link ProjectAuthorization}, {@link WorkspaceAuthorization} etc. So the
 * Authorization can be handled by the appropriate service class.
 * 
 * @author Kiran kumar
 *
 */
@Service
public class AuthorizationManager implements IAuthorizationManager {
    @Autowired
    private transient ApplicationContext context;

    @Autowired
    @Qualifier("projectAuthorization")
    private IAuthorization projectAuthorization;

    @Autowired
    @Qualifier("workspaceAuthorization")
    private IAuthorization workspaceAuthorization;

    @Autowired
    @Qualifier("workspaceRestAuthorization")
    private IAuthorization workspaceRestAuthorization;

    @Autowired
    @Qualifier("conceptCollectionAuthorization")
    private IAuthorization conceptCollectionAuthorization;

    @Autowired
    @Qualifier("conceptCollectionRestAuthorization")
    private IAuthorization conceptCollectionRestAuthorization;

    @Autowired
    @Qualifier("dictionaryAuthorization")
    private IAuthorization dictionaryAuthorization;

    @Autowired
    @Qualifier("networkAuthorization")
    private IAuthorization networkAuthorization;

    private HashMap<CheckedElementType, IAuthorization> accessManager;

    /**
     * Init class which runs after the class is loaded in the tomcat container
     * Adds the autowired {@link IAuthorization} type of objects in the
     * {@link HashMap}
     */
    @PostConstruct
    public void init() {

        accessManager = new HashMap<CheckedElementType, IAuthorization>();

        // insert records into the HashMap
        accessManager.put(CheckedElementType.PROJECT, projectAuthorization);
        accessManager.put(CheckedElementType.WORKSPACE, workspaceAuthorization);
        accessManager.put(CheckedElementType.WORKSPACE_REST, workspaceRestAuthorization);
        accessManager.put(CheckedElementType.CONCEPTCOLLECTION, conceptCollectionAuthorization);
        accessManager.put(CheckedElementType.CONCEPTCOLLECTION_REST, conceptCollectionRestAuthorization);
        accessManager.put(CheckedElementType.DICTIONARY, dictionaryAuthorization);
        accessManager.put(CheckedElementType.NETWORK, networkAuthorization);

    }

    @Override
    public IAuthorization getAuthorizationObject(CheckedElementType type) {
        return accessManager.get(type);
    }

}
