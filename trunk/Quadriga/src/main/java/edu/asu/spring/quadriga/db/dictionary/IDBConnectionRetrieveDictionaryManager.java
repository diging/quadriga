package edu.asu.spring.quadriga.db.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveDictionaryManager
{

	/**
	 * This method retrieves the dictionary details for the specified dictionaryid.
	 * @param dictionaryId
	 * @return IDictinary object
	 * @throws QuadrigaStorageException
	 */
	public abstract DictionaryDTO getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

	public abstract DictionaryDTO getDictionaryDTO(String dictionaryId)
			throws QuadrigaStorageException;
	
	/**
	 * Queries the database to get a list of dictionary items objects list
	 * 
	 * @return List containing IDictionaryItems objects of dictionary items for
	 *         a dictionary
	 * @throws QuadrigaStorageException
	 */
	public abstract List<DictionaryItemsDTO> getDictionaryItemsDetailsDTOs(
			String dictionaryid,String ownerName) throws QuadrigaStorageException;

	

}
