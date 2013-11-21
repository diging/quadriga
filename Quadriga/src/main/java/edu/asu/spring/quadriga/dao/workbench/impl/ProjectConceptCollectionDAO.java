package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.IConceptCollection;

public class ProjectConceptCollectionDAO extends DAOConnectionManager implements
		IProjectConceptCollectionDAO 
{
	
	public void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId)
	{
		throw new NotImplementedException();
	}
	
	
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId)
	{
		throw new NotImplementedException();
	}
	
	public void deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId)
	{
		throw new NotImplementedException();
	}

}
