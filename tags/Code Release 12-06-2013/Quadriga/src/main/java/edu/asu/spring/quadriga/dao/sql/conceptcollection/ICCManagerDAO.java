package edu.asu.spring.quadriga.dao.sql.conceptcollection;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ICCManagerDAO {

	List<IConceptCollection> getConceptsOwnedbyUser(String userName) throws QuadrigaStorageException;

	List<IConceptCollection> getCollaboratedConceptsofUser(String userName)
			throws QuadrigaStorageException;

	void addCollection(IConceptCollection conceptCollection)
			throws QuadrigaStorageException;

	void getCollectionDetails(IConceptCollection collection, String username)
			throws QuadrigaStorageException, QuadrigaAccessException;
}
