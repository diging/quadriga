package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;

public interface IProjectConceptCollectionFactory 
{
	public abstract IProjectConceptCollection  createProjectConceptCollectionObject(); 
	
	
	public abstract IProjectConceptCollection cloneProjectConceptCollectionObject(
			IProjectConceptCollection projectConceptCollection);
	
	

}
