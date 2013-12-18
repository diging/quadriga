package edu.asu.spring.quadriga.dao.sql.dictionary;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
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

	@Override
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException 
	{
		IDictionary dictionary = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryDTO dictionary where dictionary.id =:id");
			query.setParameter("id", dictionaryId);
			
			DictionaryDTO dictionaryDTO = (DictionaryDTO) query.uniqueResult();
			if(dictionaryDTO != null)
			{
				dictionary = dictionaryDTOMapper.getDictionary(dictionaryDTO);
			}
		} 
		catch (Exception e) 
		{
			logger.error("getDictionaryDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictionary;
	}
	

}
