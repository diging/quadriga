package edu.asu.spring.quadriga.dao.sql.dictionary.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.dictionary.IDictionaryManagerDAO;
import edu.asu.spring.quadriga.mapper.DspaceDTOMapper;

@Repository
public class DictionaryManagerDAO extends DAOConnectionManager implements IDictionaryManagerDAO
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DspaceDTOMapper dspaceDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryManagerDAO.class);

	/**
	 * This method is to add dictionary
	 * 
	 * @param username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	/*@Override
	public String addDictionary(IDictionary dictionary)throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DspaceKeysDTO.findByUsername");
			query.setParameter("username", username);
			DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
			if(dspaceKeysDTO != null)
			{
				return dspaceDTOMapper.getIDspaceKeys(dspaceKeysDTO);
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
	}*/
	
	
}
