package edu.asu.spring.quadriga.dao.sql.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.IDspaceManagerDAO;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dto.DspaceKeysDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DspaceDTOMapper;

@Repository
public class DspaceManagerDAO extends DAOConnectionManager implements IDspaceManagerDAO
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DspaceDTOMapper dspaceDTOMapper;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DspaceManagerDAO.class);

	/**
	 * This method get the Dspace keys from the database for the user
	 * 
	 * @param username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DspaceKeysDTO.findByUsername");
			query.setParameter("username", username);
			DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
			return dspaceDTOMapper.getIDspaceKeys(dspaceKeysDTO);
			
		}
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public int saveOrUpdateDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException
	{
		int result = FAILURE;
		try
		{
			DspaceKeysDTO dspaceKeysDTO = new DspaceKeysDTO(username, dspaceKeys.getPublicKey(), dspaceKeys.getPrivateKey());
			sessionFactory.getCurrentSession().saveOrUpdate(dspaceKeysDTO);
			result = SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
			result = FAILURE;
        	throw new QuadrigaStorageException();
		}
		return result;
	}
	
	@Override
	public int deleteDspaceKeys(String username) throws QuadrigaStorageException
	{
		int result = FAILURE;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DspaceKeysDTO.findByUsername");
			query.setParameter("username", username);
			DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
			sessionFactory.getCurrentSession().delete(dspaceKeysDTO);
			result = SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("deleteDspaceKeys method :",e);
			result = FAILURE;
        	throw new QuadrigaStorageException();
		}
		return result;
	}
}
