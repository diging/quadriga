package edu.asu.spring.quadriga.domain.factory.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;

@Service
public class ProjectConceptCollectionFactory implements
		IProjectConceptCollectionFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectConceptCollection createProjectConceptCollectionObject() {
		return new ProjectConceptCollection();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectConceptCollection cloneProjectConceptCollectionObject(
			IProjectConceptCollection projectConceptCollection) 
	{
        IProjectConceptCollection clone = new ProjectConceptCollection();
        clone.setProject(projectConceptCollection.getProject());
        clone.setConceptCollection(projectConceptCollection.getConceptCollection());
        clone.setCreatedBy(projectConceptCollection.getCreatedBy());
        clone.setCreatedDate(projectConceptCollection.getCreatedDate());
        clone.setUpdatedBy(projectConceptCollection.getUpdatedBy());
        clone.setUpdatedDate(projectConceptCollection.getUpdatedDate());
		return clone;
	}

}
