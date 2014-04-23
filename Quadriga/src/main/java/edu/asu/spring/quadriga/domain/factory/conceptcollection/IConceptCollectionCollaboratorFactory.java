package edu.asu.spring.quadriga.domain.factory.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;

public interface IConceptCollectionCollaboratorFactory 
{
	public abstract IConceptCollectionCollaborator createConceptCollectionCollaboratorObject();
	
	public abstract IConceptCollectionCollaborator cloneConceptCollectionCollaboratorObject(
			IConceptCollectionCollaborator conceptCollectionCollaborator);

}
