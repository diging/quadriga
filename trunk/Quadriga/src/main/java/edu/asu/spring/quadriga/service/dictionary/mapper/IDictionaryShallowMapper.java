package edu.asu.spring.quadriga.service.dictionary.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryShallowMapper {
	
	public abstract List<IDictionary> getDictionaryList(String userName) throws QuadrigaStorageException;

}