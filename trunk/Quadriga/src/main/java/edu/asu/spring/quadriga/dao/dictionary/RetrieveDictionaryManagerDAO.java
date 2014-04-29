package edu.asu.spring.quadriga.dao.dictionary;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryDeepMapper;

@Repository
public class RetrieveDictionaryManagerDAO extends DAOConnectionManager implements IDBConnectionRetrieveDictionaryManager
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	@Autowired
	private IDictionaryDeepMapper dictionaryDeepMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RetrieveDictionaryManagerDAO.class);

	/**
	 * This method retrieves the dictionary details for the specified dictionaryid.
	 * @param dictionaryId
	 * @return IDictinary object
	 * @throws QuadrigaStorageException
	 */
	@Override
	public DictionaryDTO getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException 
	{
		IDictionary dictionary = null;
		DictionaryDTO dictionaryDTO = null;
		try
		{
			dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
			//dictionary = dictionaryDTOMapper.getDictionary(dictionaryDTO);
		} 
		catch (HibernateException e) 
		{
			logger.error("getDictionaryDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictionaryDTO;
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
	
	@Override
	public List<DictionaryItemsDTO> getDictionaryItemsDetailsDTOs(String dictionaryid,String ownerName)
	{
		Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryItemsDTO dictItems where dictItems.dictionaryDTO.dictionaryowner.username =:ownerName and dictItems.dictionaryDTO.dictionaryid =:dictionaryid ORDER BY dictItems.term");
		query.setParameter("ownerName", ownerName);
		query.setParameter("dictionaryid", dictionaryid);
		
		List<DictionaryItemsDTO> dictItemsDTOList = query.list();
		
		return dictItemsDTOList;
	}
	

	
	
}
