package edu.asu.spring.quadriga.domain.factory.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;

public interface IDictionaryCollaboratorFactory 
{

	public abstract IDictionaryCollaborator createDictionaryCollaboratorObject();
	
	public abstract IDictionaryCollaborator cloneDictionaryCollaboratorObject(IDictionaryCollaborator dictionaryCollaborator);

}