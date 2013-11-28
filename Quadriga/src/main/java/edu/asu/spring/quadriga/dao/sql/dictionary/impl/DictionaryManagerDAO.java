package edu.asu.spring.quadriga.dao.sql.dictionary.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.dictionary.IDictionaryManagerDAO;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class DictionaryManagerDAO extends DAOConnectionManager implements IDictionaryManagerDAO
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryManagerDAO.class);

	/**
	 * Gets the dictionary of the user matched with his user name
	 * @return List of Dictionary 
	 * @throws QuadrigaStorageException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IDictionary> getDictionaryOfUser(String userId) throws QuadrigaStorageException 
	{
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryDTO dictionary where dictionary.dictionaryowner.username =:username");
			query.setParameter("username", userId);
			List<DictionaryDTO> dictionaryDTOList = query.list();
			if(dictionaryDTOList != null && dictionaryDTOList.size() > 0)
			{
				dictionaryList = dictionaryDTOMapper.getDictionaryList(dictionaryDTOList);
			}
		} 
		catch (Exception e) 
		{
			logger.error("getDictionaryOfUser method :",e);
		}
		return dictionaryList;
	}
	
}
