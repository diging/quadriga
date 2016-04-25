package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;

public interface IProjectDictionaryFactory 
{
	public abstract IProjectDictionary  createProjectDictionaryObject();
	
	public abstract IProjectDictionary cloneProjectDictionaryObject(
			IProjectDictionary projectDictionary);
}
