package edu.asu.spring.quadriga.dao;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dto.DspaceKeysDTO;
import edu.asu.spring.quadriga.dto.DspaceKeysDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DspaceDTOMapper;


public class DspaceManagerDAO extends DAOConnectionManager implements IDBConnectionDspaceManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DspaceDTOMapper dspaceDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(DspaceManagerDAO.class);

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

	/**
	 * Method to add bitstream to workspace
	 * @param  workspaceid,communityid, collectionid,itemid, bitstreamid and username
	 * @return integer representing the result of the save operation
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@Override
	public int addBitstreamToWorkspace(String workspaceid, String communityid,String collectionid, String itemid, String bitstreamid,
									   String username) throws QuadrigaStorageException 
	{
		int result = FAILURE;
		try
		{
			WorkspaceDspaceDTO workspaceDspaceDTO = new WorkspaceDspaceDTO();
			workspaceDspaceDTO.setWorkspaceDspaceDTOPK(new WorkspaceDspaceDTOPK(workspaceid, bitstreamid));
			workspaceDspaceDTO.setCommunityid(communityid);
			workspaceDspaceDTO.setCollectionid(collectionid);
			workspaceDspaceDTO.setItemid(itemid);
			workspaceDspaceDTO.setCreatedby(username);
			workspaceDspaceDTO.setCreateddate(new Date());
			sessionFactory.getCurrentSession().save(workspaceDspaceDTO);
			result = SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("addBitstreamToWorkspace method :",e);
			result = FAILURE;
        	throw new QuadrigaStorageException();
		}
		return result;
	}

	/**
	 * Method to delete bitstream from the workspace
	 * @param  workspaceid,bitstreamid and username
	 * @return void
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteBitstreamFromWorkspace(String workspaceid,String bitstreamids, String username)
			throws QuadrigaStorageException, QuadrigaAccessException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from WorkspaceDspaceDTO wsDspace where wsDspace.workspaceDspaceDTOPK.workspaceid =:workspaceid and wsDspace.workspaceDspaceDTOPK.bitstreamid =:bitstreamid");
			query.setParameter("workspaceid", workspaceid);
			query.setParameter("bitstreamid", bitstreamids);
			WorkspaceDspaceDTO workspaceDspaceDTO = (WorkspaceDspaceDTO) query.uniqueResult();
			if(workspaceDspaceDTO != null)
			{
				sessionFactory.getCurrentSession().delete(workspaceDspaceDTO);
			}
			else
			{
				throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
			}
		}
		catch(Exception e)
		{
			logger.error("deleteBitstreamFromWorkspace method :",e);
        	throw new QuadrigaStorageException();
		}
	}

	/**
	 * Method to add Dspace keys to the database
	 * @param  dspace keys and username
	 * @return Integer 0 or 1 representing the result
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@Override
	public int addDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException {
		int result = FAILURE;
		try
		{
			if(dspaceKeys == null || dspaceKeys.getPublicKey() == null || dspaceKeys.getPrivateKey() == null || username == null
					|| dspaceKeys.getPublicKey().equals("") || dspaceKeys.getPrivateKey().equals("") || username.equals(""))
			{
				result = FAILURE;
			}
			
			else
			{
				DspaceKeysDTO dspaceKeysDTO = new DspaceKeysDTO(new DspaceKeysDTOPK(username, dspaceKeys.getPublicKey(), dspaceKeys.getPrivateKey()));
				sessionFactory.getCurrentSession().save(dspaceKeysDTO);
				result = SUCCESS;
			}
			
		}
		catch(Exception e)
		{
			result = FAILURE;
			logger.error("addDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return result;
	}

	/**
	 * Method to update Dspace keys in the database corresponding to the username
	 * @param  dspace keys and username
	 * @return Integer 0 or 1 representing the result
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@Override
	public int updateDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException {
		int result = FAILURE;
		try
		{
			if(dspaceKeys == null || dspaceKeys.getPublicKey() == null || dspaceKeys.getPrivateKey() == null || username == null
					|| dspaceKeys.getPublicKey().equals("") || dspaceKeys.getPrivateKey().equals("") || username.equals(""))
			{
				result = FAILURE;
			}
			
			else
			{
				Query query = sessionFactory.getCurrentSession().createQuery("from DspaceKeysDTO dspaceKeys where dspaceKeys.dspaceKeysDTOPK.publickey =:publickey and dspaceKeys.dspaceKeysDTOPK.privatekey =:privatekey and dspaceKeys.dspaceKeysDTOPK.username =:username");
				query.setParameter("publickey", dspaceKeys.getPublicKey());
				query.setParameter("privatekey", dspaceKeys.getPrivateKey());
				query.setParameter("username", username);
				DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
				if(dspaceKeysDTO != null)
				{
					dspaceKeysDTO.setDspaceKeysDTOPK(new DspaceKeysDTOPK(username, dspaceKeys.getPublicKey(), dspaceKeys.getPrivateKey()));
					result = SUCCESS;
				}
				else
				{
					result = FAILURE;
				}
				sessionFactory.getCurrentSession().update(dspaceKeysDTO);
			}
			
		}
		catch(Exception e)
		{
			result = FAILURE;
			logger.error("updateDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return result;
	}
}
