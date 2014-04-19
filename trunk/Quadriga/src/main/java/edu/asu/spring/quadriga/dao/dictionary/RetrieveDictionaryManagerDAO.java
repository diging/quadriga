package edu.asu.spring.quadriga.dao.dictionary;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class RetrieveDictionaryManagerDAO extends DAOConnectionManager implements IDBConnectionRetrieveDictionaryManager
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RetrieveDictionaryManagerDAO.class);

	/**
	 * This method retrieves the dictionary details for the specified dictionaryid.
	 * @param dictionaryId
	 * @return IDictinary object
	 * @throws QuadrigaStorageException
	 */
	@Override
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException 
	{
		IDictionary dictionary = null;
		DictionaryDTO dictionaryDTO = null;
		try
		{
			dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
			dictionary = dictionaryDTOMapper.getDictionary(dictionaryDTO);
		} 
		catch (HibernateException e) 
		{
			logger.error("getDictionaryDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictionary;
	}
	

	@Override
	public DictionaryDTO getDictionaryDTO(String dictionaryId) throws QuadrigaStorageException 
	{
		DictionaryDTO dictionaryDTO = null;
		try
		{
			dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
		} 
		catch (HibernateException e) 
		{
			logger.error("getDictionaryDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictionaryDTO;
	}
	
	
}
