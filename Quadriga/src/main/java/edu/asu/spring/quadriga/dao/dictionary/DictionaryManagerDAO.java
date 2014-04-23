package edu.asu.spring.quadriga.dao.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IItem;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

@Repository
public class DictionaryManagerDAO extends DAOConnectionManager implements IDBConnectionDictionaryManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	@Autowired
	private DictionaryCollaboratorDTOMapper collaboratorMapper;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	@Resource(name = "projectconstants")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryManagerDAO.class);

	/**
	 * Gets the dictionary of the user matched with his user name
	 * @param user id id
	 * @return List of Dictionary 
	 * @throws QuadrigaStorageException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryDTO> getDictionaryOfUser(String userId) throws QuadrigaStorageException 
	{
		List<DictionaryDTO> dictionaryDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryDTO dictionary where dictionary.dictionaryowner.username =:username");
			query.setParameter("username", userId);
			dictionaryDTOList = query.list();
//			if(dictionaryDTOList != null && dictionaryDTOList.size() > 0)
//			{
//				dictionaryList = dictionaryDTOMapper.getDictionaryList(dictionaryDTOList);
//			}
		} 
		catch (HibernateException e) 
		{
			logger.error("getDictionaryOfUser method :",e);
		}
		return dictionaryDTOList;
	}

	/**
	 * Save a dictionary in the database
	 * @param dictionary object from the screen
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author karthik jayaraman
	 */
	@Override
	public void addDictionary(IDictionary dictionary) throws QuadrigaStorageException 
	{
		try
		{
			String dictionaryId = messages.getProperty("dictionary_internalid.name") + generateUniqueID();
			dictionary.setDictionaryId(dictionaryId);
			DictionaryDTO dictionaryDTO = dictionaryDTOMapper.getDictionaryDTO(dictionary);
			sessionFactory.getCurrentSession().save(dictionaryDTO);
		}
		catch(HibernateException e)
		{
			logger.error("addDictionary :",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * Get dictionary Item details with dictionary ID and owner name
	 * @param dictionary id and owner name
	 * @return List of IDictionaryItem
	 * @throws QuadrigaStorageException
	 * @author karthik jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IItem> getDictionaryItemsDetails(String dictionaryid,String ownerName) throws QuadrigaStorageException 
	{
		List<IItem> dictItemList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryItemsDTO dictItems where dictItems.dictionaryDTO.dictionaryowner.username =:ownerName and dictItems.dictionaryDTO.dictionaryid =:dictionaryid ORDER BY dictItems.term");
			query.setParameter("ownerName", ownerName);
			query.setParameter("dictionaryid", dictionaryid);
			
			List<DictionaryItemsDTO> dictItemsDTOList = query.list();
			if(dictItemsDTOList != null && dictItemsDTOList.size() > 0)
			{
				dictItemList = dictionaryDTOMapper.getDictionaryItemList(dictItemsDTOList);
			}
		} 
		catch (HibernateException e) 
		{
			logger.error("getDictionaryItemsDetails method :",e);
			throw new QuadrigaStorageException();
		}
		return dictItemList;
	}

	/**
	 * Get dictionary name with dictionary ID
	 * @param dictionary id
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
			query.setParameter("dictionaryid", dictionaryId);
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
	 * @param dictionary id, item, itemid, pos and owner name
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addDictionaryItems(String dictionaryId, String item,String termid, String pos, String owner) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid and dictItems.term =:term and dictItems.pos =:pos");
			query.setParameter("dictionaryid", dictionaryId);
			query.setParameter("termid", termid);
			query.setParameter("term", item);
			query.setParameter("pos", pos);
			List<DictionaryItemsDTO> dictItemsDTOList = query.list();
			
			if((dictItemsDTOList.size() == 0)|| (dictItemsDTOList==null))
			{
				DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
				DictionaryItemsDTO dictionaryItems = dictionaryDTOMapper.getDictionaryItemsDTO(dictionary, item, termid, pos, owner);
				sessionFactory.getCurrentSession().save(dictionaryItems);
			}
		} 
		catch (HibernateException e) 
		{
			logger.error("addDictionaryItems method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Delete a dictionary item to the database corresponding to dictionary ID,Item ID and owner name
	 * @param dictionary id
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteDictionaryItems(String dictionaryId, String itemid,String ownerName) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid IN ( Select dict.dictionaryid from DictionaryDTO dict where dict.dictionaryid =:dictionaryid and dict.dictionaryowner.username =:username) and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("dictionaryid", dictionaryId);
			query.setParameter("username", ownerName);
			query.setParameter("termid", itemid);
			
			DictionaryItemsDTO dictItemsDTO = (DictionaryItemsDTO) query.uniqueResult();
			if(dictItemsDTO != null)
			{
				sessionFactory.getCurrentSession().delete(dictItemsDTO);					
			}
		}
		catch(Exception e)
		{
			logger.error("deleteDictionaryItems method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Update a dictionary item to the database corresponding to dictionary ID and Item ID
	 * @param dictionary id 
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void updateDictionaryItems(String dictionaryId, String termid,String term, String pos) throws QuadrigaStorageException {
		DictionaryItemsDTO dictionaryItems = null;
		DictionaryItemsDTOPK dictionaryItemsKey = null;
		try
		{
			dictionaryItemsKey = new DictionaryItemsDTOPK(dictionaryId,termid);
			dictionaryItems = (DictionaryItemsDTO) sessionFactory.getCurrentSession().get(DictionaryItemsDTO.class, dictionaryItemsKey);
			if(dictionaryItems != null)
			{
				dictionaryItems.setTerm(term);
				dictionaryItems.setPos(pos);
				sessionFactory.getCurrentSession().update(dictionaryItems);					
			}
		}
		catch(HibernateException e)
		{
			logger.error("deleteDictionaryItems method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Get non collaborators for the dictionary
	 * @param dictionary id 
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
			Query query = sessionFactory.getCurrentSession().createQuery("Select quadUser.username from QuadrigaUserDTO quadUser where quadUser.username NOT IN (Select dictCollab.quadrigaUserDTO.username from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.dictionaryid =:dictionaryid) AND quadUser.username NOT IN (Select dict.dictionaryowner.username from DictionaryDTO dict where dict.dictionaryid =:dictionaryid)");
			query.setParameter("dictionaryid", dictionaryid);
			List<String> userNameList = query.list();
			
			for(String userName : userNameList)
			{
				QuadrigaUserDTO userDTO = getUserDTO(userName);
				IUser user = userMapper.getUser(userDTO);
				nonCollabUsersList.add(user);
				
			}
		}
		catch(HibernateException e)
		{
			logger.error("showNonCollaboratingUsersRequest method :",e);
			throw new QuadrigaStorageException();
		}
		return nonCollabUsersList;
	}

	/**
	 * Add dictionary collarborators based on the roles to the database.
	 * @param Collaborator object, dictionary id, dictionary owner user name and logged in username
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addCollaborators(ICollaborator collaborator,String dictionaryid, String userName, String sessionUser) throws QuadrigaStorageException 
	{
		try
		{
			DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryid);
			
			for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
			{
				if(collaboratorRole.getRoleDBid()!=null)
				{
					String collabRole = collaboratorRole.getRoleDBid();
					DictionaryCollaboratorDTO dictCollabDTO = collaboratorMapper.getDictionaryCollaboratorDTO(dictionary, sessionUser, collabRole);
					sessionFactory.getCurrentSession().save(dictCollabDTO);
				}
			}
		}
		catch(HibernateException e)
		{
			logger.error("addCollaborators method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Delete dictionary collarborators based on the dictionaryid and the collaborator user name
	 * @param username and dictionary id 
	 * @return void
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteCollaborators(String dictionaryid, String userName) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Delete from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.dictionaryid =:dictionaryid and dictCollab.dictionaryCollaboratorDTOPK.collaboratoruser =:collaboratoruser");
			query.setParameter("dictionaryid", dictionaryid);
			query.setParameter("collaboratoruser",userName);
			int output = query.executeUpdate();
			if(output == 0)
			{
				throw new QuadrigaStorageException();
			}
		}
		catch(Exception e)
		{
			logger.error("deleteCollaborators method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Delete dictionary from the database corresponding to the dictionary ID and owner name
	 * @param username and dictionary id 
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteDictionary(String user, String dictionaryId) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryDTO dict where dict.dictionaryid =:dictionaryid and dict.dictionaryowner.username =:username");
			query.setParameter("username", user);
			query.setParameter("dictionaryid",dictionaryId);
			DictionaryDTO dictionaryDTO = (DictionaryDTO) query.uniqueResult();
			
			if(dictionaryDTO != null)
			{
				sessionFactory.getCurrentSession().delete(dictionaryDTO);
			}
		}
		catch(Exception e)
		{
			logger.error("deleteDictionary method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Get user dictionary permission from the database
	 * @param user id and dictionary id
	 * @return Boolean for the permission 
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public boolean userDictionaryPerm(String userId, String dictionaryId) throws QuadrigaStorageException {
		Boolean isValidUser =  Boolean.FALSE;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryDTO dict where dict.dictionaryid =:dictionaryid and dict.dictionaryowner.username =:username");
			query.setParameter("username", userId);
			query.setParameter("dictionaryid",dictionaryId);
			DictionaryDTO dictionaryDTO = (DictionaryDTO) query.uniqueResult();
			
			if(dictionaryDTO != null)
			{
				isValidUser = Boolean.TRUE;
			}
		}
		catch(Exception e)
		{
			logger.error("userDictionaryPerm method :",e);
			throw new QuadrigaStorageException();
		}
		return isValidUser;
	}

	/**
	 * Get user dictionary of the user with the collaborator role
	 * @param user id
	 * @return List of Dictionary objects
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DictionaryDTO> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException {
		//List<IDictionary> dictList = null;
		List<DictionaryDTO> dictDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select dictCollab.dictionaryDTO from DictionaryCollaboratorDTO dictCollab where dictCollab.quadrigaUserDTO.username =:username");
			query.setParameter("username", userId);
			 dictDTOList = query.list();
//			dictList = new ArrayList<IDictionary>();
//			for(DictionaryDTO dictionaryDTO : dictDTOList)
//			{
//				IDictionary dictionary = dictionaryDTOMapper.getDictionary(dictionaryDTO);
//				dictList.add(dictionary);
//			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryCollabOfUser method :",e);
			throw new QuadrigaStorageException();
		}
		return dictDTOList;
	}

	/**
	 * Get the user role of the collaborator with the user id and dictionary id
	 * @param user id and dictionary id
	 * @return Role of the user
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public List<String> getDictionaryCollaboratorRoles(String userId, String dicitonaryId) throws QuadrigaStorageException {
		List<String> collaboratorRoles = new ArrayList<String>();
		try
		{
			DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dicitonaryId);
			List<DictionaryCollaboratorDTO> dictionaryCollaborators = dictionary.getDictionaryCollaboratorDTOList();
			
			for(DictionaryCollaboratorDTO collaborator : dictionaryCollaborators)
			{
				if(collaborator.getQuadrigaUserDTO().getUsername().equals(userId))
				{
					collaboratorRoles.add(collaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole());
				}
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryCollaboratorRoles method :",e);
			throw new QuadrigaStorageException();
		}
		return collaboratorRoles;
	}

	/**
	 * Get the Dictionary Items corresponding to a dictionary ID
	 * @param udictionary id
	 * @return List of dictionary items
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IItem> getDictionaryItemsDetailsCollab(String dictionaryid) throws QuadrigaStorageException {
		List<IItem> dictionaryItemList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid ORDER BY dictItems.term");
			query.setParameter("dictionaryid",dictionaryid);
			List<DictionaryItemsDTO> dictItemsDTOList = query.list();
			
			if(dictItemsDTOList != null && dictItemsDTOList.size() > 0)
			{
				dictionaryItemList = dictionaryDTOMapper.getDictionaryItemList(dictItemsDTOList);
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryItemsDetailsCollab method :",e);
			throw new QuadrigaStorageException();
		}
		return dictionaryItemList;
	}

	/**
	 * Delete Dictionary Items corresponding to a dictionary ID and Term id
	 * @param dictionary id and term id
	 * @return error messages if any
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Delete from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("dictionaryid",dictionaryId);
			query.setParameter("termid",itemid);
			int output = query.executeUpdate();
			if(output == 0)
			{
				logger.error("Item does not exists in this dictionary");
				throw new QuadrigaStorageException();
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryItemsDetailsCollab method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * Get Dictionary owner name corresponding to a dictionary ID
	 * @param dictionary id
	 * @return Owner username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String getDictionaryOwner(String dictionaryId) throws QuadrigaStorageException {
		String ownerUserName = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select dict.dictionaryowner.username from DictionaryDTO dict where dict.dictionaryid =:dictionaryid");
			query.setParameter("dictionaryid",dictionaryId);
			ownerUserName = (String) query.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error("getDictionaryOwner method :",e);
			throw new QuadrigaStorageException();
		}
		return ownerUserName;
	}

	/**
	 * Get Dictionary ID name corresponding to a dictionary ID
	 * @param dictionary id
	 * @return Owner username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String getDictionaryId(String dictName) throws QuadrigaStorageException {
		String dictID = null;
		DictionaryDTO dictDTO = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DictionaryDTO.findByDictionaryname");
			query.setParameter("dictionaryname",dictName);
			dictDTO = (DictionaryDTO) query.uniqueResult();
			
			if(dictDTO != null)
			{
				dictID = dictDTO.getDictionaryid();
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryId method :",e);
			throw new QuadrigaStorageException();
		}
		return dictID;
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public DictionaryDTO getDictionaryDetails(String userName)
			throws QuadrigaStorageException {

		DictionaryDTO dictDTO = null;
		Query query = sessionFactory.getCurrentSession().getNamedQuery("DictionaryDTO.findAll");
		dictDTO = (DictionaryDTO) query.uniqueResult();
		//IDictionary dictionary = dictionaryDTOMapper.getDictionary(dictDTO);
		return dictDTO;
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryDTO> getDictionaryDTOList(String userName)
			throws QuadrigaStorageException {

		List<DictionaryDTO> dictionaryDTOList = null;
		Query query = sessionFactory.getCurrentSession().getNamedQuery("from DictionaryDTO dictionary where dictionary.username =: userName");
		query.setParameter("userName",userName);
		dictionaryDTOList = query.list();
		return dictionaryDTOList;
	}

	/**
	 * This method retrieves the collaborators associated with given dictionary
	 * @param : dictionaryid - dictionary id
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public List<ICollaborator> showCollaboratingUsersRequest(String dictionaryid) throws QuadrigaStorageException {
		List<ICollaborator> collaborators = new ArrayList<ICollaborator>();
		
		
		try
		{
			DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryid);
			
			collaborators = collaboratorMapper.getDictionaryCollaboratorList(dictionary);
		}
		catch(HibernateException e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return collaborators;
	}

	
}
