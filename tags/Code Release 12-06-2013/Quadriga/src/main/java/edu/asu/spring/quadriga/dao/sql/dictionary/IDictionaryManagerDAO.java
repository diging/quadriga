package edu.asu.spring.quadriga.dao.sql.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryManagerDAO {

	List<IDictionary> getDictionaryOfUser(String userId) throws QuadrigaStorageException;


}
