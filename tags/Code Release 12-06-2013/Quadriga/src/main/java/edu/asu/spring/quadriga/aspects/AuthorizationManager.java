package edu.asu.spring.quadriga.aspects;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;

@Service
public class AuthorizationManager implements IAuthorizationManager
{
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
	@Qualifier("dictionaryAuthorization")
	private IAuthorization dictionaryAuthorization;
	
	@Autowired
	@Qualifier("networkAuthorization")
	private IAuthorization networkAuthorization;
	
	private HashMap<CheckedElementType,IAuthorization> accessManager;
	
	@PostConstruct
	public void init() {
		
		accessManager = new  HashMap<CheckedElementType,IAuthorization>();
		
		//insert records into the HashMap
		accessManager.put(CheckedElementType.PROJECT, projectAuthorization);
		accessManager.put(CheckedElementType.WORKSPACE, workspaceAuthorization);
		accessManager.put(CheckedElementType.WORKSPACE_REST, workspaceRestAuthorization);
		accessManager.put(CheckedElementType.CONCEPTCOLLECTION, conceptCollectionAuthorization);
		accessManager.put(CheckedElementType.DICTIONARY, dictionaryAuthorization);
		accessManager.put(CheckedElementType.NETWORK,networkAuthorization);
		
	}
	
	@Override
	public IAuthorization getAuthorizationObject(CheckedElementType type)
	{
		return accessManager.get(type);
	}

}
