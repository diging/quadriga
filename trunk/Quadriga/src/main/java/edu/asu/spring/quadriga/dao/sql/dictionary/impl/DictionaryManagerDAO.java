package edu.asu.spring.quadriga.dao.sql.dictionary.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class DictionaryManagerDAO extends DAOConnectionManager implements IDBConnectionDictionaryManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	@Autowired
	private IUserFactory userFactory;
	
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

	/**
	 * Save a dictionary in the database
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author karthik jayaraman
	 */
	@Override
	public String addDictionary(IDictionary dictionary) throws QuadrigaStorageException 
	{
		String errmsg = "";
		try
		{
			if(dictionary==null)
			{
				return "Dictionary object is null" ;
			}
			DictionaryDTO dictionaryDTO = dictionaryDTOMapper.getDictionaryDTO(dictionary);
			dictionaryDTO.setId("DICT_"+generateUniqueID());
			sessionFactory.getCurrentSession().save(dictionaryDTO);
		}
		catch(Exception e)
		{
			logger.info("addDictionary ::"+e.getMessage());	
			errmsg="DB Issue";
			throw new QuadrigaStorageException(e);
		}
		return errmsg;
	}

	/**
	 * Get dictionary Item details with dictionary ID and owner name
	 * @return List of IDictionaryItem
	 * @throws QuadrigaStorageException
	 * @author karthik jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IDictionaryItem> getDictionaryItemsDetails(String dictionaryid,String ownerName) throws QuadrigaStorageException 
	{
		List<IDictionaryItem> dictItemList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryItemsDTO dictItems where dictItems.dictionaryDTO.dictionaryowner.username =:ownerName and dictItems.dictionaryDTO.id =:id ORDER BY dictItems.term");
			query.setParameter("ownerName", ownerName);
			query.setParameter("id", dictionaryid);
			
			List<DictionaryItemsDTO> dictItemsDTOList = query.list();
			if(dictItemsDTOList != null && dictItemsDTOList.size() > 0)
			{
				dictItemList = dictionaryDTOMapper.getDictionaryItemList(dictItemsDTOList);
			}
		} 
		catch (Exception e) 
		{
			logger.error("getDictionaryItemsDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictItemList;
	}

	/**
	 * Get dictionary name with dictionary ID
	 * @return Dictionary name as String
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String getDictionaryName(String dictionaryId) throws QuadrigaStorageException {
		DictionaryDTO dictionaryDTO = null;
		String dictName = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DictionaryDTO.findById");
			query.setParameter("id", dictionaryId);
			dictionaryDTO = (DictionaryDTO) query.uniqueResult();
			if(dictionaryDTO != null)
			{
				dictName = dictionaryDTO.getDictionaryname();
			}
		} 
		catch (Exception e) 
		{
			logger.error("getDictionaryName method :",e);
			throw new QuadrigaStorageException();
		}
		return dictName;
	}

	/**
	 * Adds a dictionary item to the database
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addDictionaryItems(String dictionaryId, String item,String id, String pos, String owner) throws QuadrigaStorageException {
		logger.debug(dictionaryId +" , "+item+" , "+id +" , "+pos+" , "+ owner);
		String errMsg = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.id =:id and dictItems.dictionaryItemsDTOPK.termid =:termid and dictItems.term =:term and dictItems.pos =:pos");
			query.setParameter("id", dictionaryId);
			query.setParameter("termid", id);
			query.setParameter("term", item);
			query.setParameter("pos", pos);
			List<DictionaryItemsDTO> dictItemsDTOList = query.list();
			
			if(dictItemsDTOList != null && dictItemsDTOList.size() > 0)
			{
				errMsg = "Item already exists";
			}
			else
			{
				DictionaryItemsDTO dictItemsDTO = new DictionaryItemsDTO();
				dictItemsDTO.setDictionaryItemsDTOPK(new DictionaryItemsDTOPK(dictionaryId, id));
				dictItemsDTO.setTerm(item);
				dictItemsDTO.setPos(pos);
				dictItemsDTO.setCreatedby(owner);
				dictItemsDTO.setCreateddate(new Date());
				dictItemsDTO.setUpdatedby(owner);
				dictItemsDTO.setUpdateddate(new Date());
				sessionFactory.getCurrentSession().update(dictItemsDTO);
			}
		} 
		catch (Exception e) 
		{
			logger.error("addDictionaryItems method :",e);
			errMsg="DB Issue";
			throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	/**
	 * Delete a dictionary item to the database corresponding to dictionary ID and Item ID
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String deleteDictionaryItems(String dictionaryId, String itemid,String ownerName) throws QuadrigaStorageException {
		String errMsg = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.id IN ( Select dict.id from DictionaryDTO dict where dict.id =:id and dict.dictionaryowner.username =:username) and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("id", dictionaryId);
			query.setParameter("username", ownerName);
			query.setParameter("termid", itemid);
			
			DictionaryItemsDTO dictItemsDTO = (DictionaryItemsDTO) query.uniqueResult();
			if(dictItemsDTO != null)
			{
				sessionFactory.getCurrentSession().delete(dictItemsDTO);					
			}
			else
			{
				errMsg = "Item doesnot exists in this dictionary";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteDictionaryItems method :",e);
			errMsg="DB Issue";
			throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	/**
	 * Update a dictionary item to the database corresponding to dictionary ID and Item ID
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String updateDictionaryItems(String dictinaryId, String termid,String term, String pos) throws QuadrigaStorageException {
		String errMsg = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.id =:id and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("id", "dictinaryId");
			query.setParameter("termid", "termid");
			
			DictionaryItemsDTO dictionaryItemsDTO = (DictionaryItemsDTO) query.uniqueResult();
			if(dictionaryItemsDTO != null)
			{
				dictionaryItemsDTO.setTerm(term);
				dictionaryItemsDTO.setPos(pos);
				sessionFactory.getCurrentSession().update(dictionaryItemsDTO);					
			}
			else
			{
				errMsg = "Item doesnot exists in this dictionary";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteDictionaryItems method :",e);
			errMsg="DB Issue";
			throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	/**
	 * Update a dictionary item to the database corresponding to dictionary ID and Item ID
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IUser> showNonCollaboratingUsersRequest(String dictionaryid) throws QuadrigaStorageException {
		List<IUser> nonCollabUsersList = new ArrayList<IUser>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select quadUser.username from QuadrigaUserDTO quadUser where quadUser.username NOT IN (Select dictCollab.quadrigaUserDTO.username from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.id =:id) AND quadUser.username NOT IN (Select dict.dictionaryowner.username from DictionaryDTO dict where dict.id =:id)");
			query.setParameter("id", dictionaryid);
			List<String> userNameList = query.list();
			
			if(userNameList != null && userNameList.size() > 0)
			{
				Iterator<String> userNameItr = userNameList.iterator();
				while(userNameItr.hasNext())
				{
					IUser user = userFactory.createUserObject();
					user.setUserName(userNameItr.next());
					nonCollabUsersList.add(user);
				}
			
			}
		}
		catch(Exception e)
		{
			logger.error("showNonCollaboratingUsersRequest method :",e);
			throw new QuadrigaStorageException();
		}
		return nonCollabUsersList;
	}

	/**
	 * Add dictionary collarborators based on the roles to the database.
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String addCollaborators(ICollaborator collaborator,String dictionaryid, String userName, String sessionUser) throws QuadrigaStorageException 
	{
		String errmsg = null;
		try
		{
			for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
			{
				if(collaboratorRole.getRoleDBid()!=null)
				{
					String collabRole = collaboratorRole.getRoleDBid();
					DictionaryCollaboratorDTO dictCollabDTO = new DictionaryCollaboratorDTO();
					dictCollabDTO.setDictionaryCollaboratorDTOPK(new DictionaryCollaboratorDTOPK(dictionaryid, userName, collabRole));
					dictCollabDTO.setCreatedby(sessionUser);
					dictCollabDTO.setCreateddate(new Date());
					dictCollabDTO.setUpdatedby(sessionUser);
					dictCollabDTO.setUpdateddate(new Date());
					sessionFactory.getCurrentSession().save(dictCollabDTO);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("addCollaborators method :",e);
			errmsg = "DB Exception";
			throw new QuadrigaStorageException();
		}
		return errmsg;
	}

	/**
	 * Delete dictionary collarborators based on the dictionaryid and the collaborator user name
	 * @return void
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteCollaborators(String dictionaryid, String userName) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Delete from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.id =:id and dictCollab.dictionaryCollaboratorDTOPK.collaboratoruser =:collaboratoruser");
			query.setParameter("id", dictionaryid);
			query.setParameter("collaboratoruser",userName);
			int output = query.executeUpdate();
			if(output == 0)
			{
				logger.error("No data to update");
				throw new QuadrigaStorageException();
			}
		}
		catch(Exception e)
		{
			logger.error("deleteCollaborators method :",e);
			throw new QuadrigaStorageException();
		}
	}

	@Override
	public String deleteDictionary(String user, String dictionaryId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userDictionaryPerm(String userId, String dictionaryId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IDictionary> getDictionaryCollabOfUser(String userId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDictionaryCollabPerm(String userId, String dicitonaryId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDictionaryItem> getDictionaryItemsDetailsCollab(
			String dictionaryid) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDictionaryItemsCollab(String dictinaryId, String itemid)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDictionaryOwner(String dictionaryId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDictionaryId(String dictName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ICollaborator> showCollaboratingUsersRequest(String dictionaryid)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
