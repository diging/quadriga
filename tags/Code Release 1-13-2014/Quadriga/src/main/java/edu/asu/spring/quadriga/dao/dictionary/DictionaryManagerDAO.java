package edu.asu.spring.quadriga.dao.dictionary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Repository
public class DictionaryManagerDAO extends DAOConnectionManager implements IDBConnectionDictionaryManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DictionaryDTOMapper dictionaryDTOMapper;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private DictionaryFactory dictionaryFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryManagerDAO.class);

	/**
	 * Gets the dictionary of the user matched with his user name
	 * @param user id id
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
	 * @param dictionary object from the screen
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author karthik jayaraman
	 */
	@Override
	public String addDictionary(IDictionary dictionary) throws QuadrigaStorageException 
	{
		String errMsg = "";
		try
		{
			if(dictionary==null)
			{
				return "Dictionary object is null" ;
			}
			DictionaryDTO dictionaryDTO = dictionaryDTOMapper.getDictionaryDTO(dictionary);
			dictionaryDTO.setDictionaryid("DICT_"+generateUniqueID());
			sessionFactory.getCurrentSession().save(dictionaryDTO);
		}
		catch(Exception e)
		{
			logger.info("addDictionary ::"+e.getMessage());	
			errMsg="DB Issue";
			throw new QuadrigaStorageException(e);
		}
		return errMsg;
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
	public List<IDictionaryItem> getDictionaryItemsDetails(String dictionaryid,String ownerName) throws QuadrigaStorageException 
	{
		List<IDictionaryItem> dictItemList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from DictionaryItemsDTO dictItems where dictItems.dictionaryDTO.dictionaryowner.username =:ownerName and dictItems.dictionaryDTO.dictioanryid =:dictionaryid ORDER BY dictItems.term");
			query.setParameter("ownerName", ownerName);
			query.setParameter("dictionaryid", dictionaryid);
			
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
	 * @param dictionary id, item, itemid, pos and owner name
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addDictionaryItems(String dictionaryId, String item,String id, String pos, String owner) throws QuadrigaStorageException {
		logger.debug(dictionaryId +" , "+item+" , "+id +" , "+pos+" , "+ owner);
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid and dictItems.term =:term and dictItems.pos =:pos");
			query.setParameter("dictionaryid", dictionaryId);
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
				sessionFactory.getCurrentSession().save(dictItemsDTO);
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
	 * Delete a dictionary item to the database corresponding to dictionary ID,Item ID and owner name
	 * @param dictionary id
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String deleteDictionaryItems(String dictionaryId, String itemid,String ownerName) throws QuadrigaStorageException {
		String errMsg = "";
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
	 * @param dictionary id 
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String updateDictionaryItems(String dictionaryId, String termid,String term, String pos) throws QuadrigaStorageException {
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("dictionaryid", dictionaryId);
			query.setParameter("termid", termid);
			
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
	 * @param Collaborator object, dictionary id, dictionary owner user name and logged in username
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String addCollaborators(ICollaborator collaborator,String dictionaryid, String userName, String sessionUser) throws QuadrigaStorageException 
	{
		String errMsg = "";
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
			errMsg = "DB Exception";
			throw new QuadrigaStorageException();
		}
		return errMsg;
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

	/**
	 * Delete dictionary from the database corresponding to the dictionary ID and owner name
	 * @param username and dictionary id 
	 * @return Error message
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String deleteDictionary(String user, String dictionaryId) throws QuadrigaStorageException {
		String errMsg = "";
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
			else
			{
				errMsg = "User don't have access to this dictionary";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteDictionary method :",e);
			errMsg = "DB Exception";
			throw new QuadrigaStorageException();
		}
		return errMsg;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException {
		List<IDictionary> dictList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select dictCollab.dictionaryDTO from DictionaryCollaboratorDTO dictCollab where dictCollab.quadrigaUserDTO.username =:username");
			query.setParameter("username", userId);
			List<DictionaryDTO> dictDTOList = query.list();
			
			if(dictDTOList != null && dictDTOList.size() > 0)
			{
				dictList = new ArrayList<IDictionary>();
				Iterator dictDTOIterator = dictDTOList.iterator();
				while(dictDTOIterator.hasNext())
				{
					DictionaryDTO dictionaryDTO =  (DictionaryDTO) dictDTOIterator.next();
					IDictionary dictionary = dictionaryFactory.createDictionaryObject();
					dictionary.setName(dictionaryDTO.getDictionaryname());
					dictionary.setDescription(dictionaryDTO.getDescription());
					dictionary.setId(dictionary.getId());
					dictList.add(dictionary);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryCollabOfUser method :",e);
			throw new QuadrigaStorageException();
		}
		return dictList;
	}

	/**
	 * Get the user role of the collaborator with the user id and dictionary id
	 * @param user id and dictionary id
	 * @return Role of the user
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public String getDictionaryCollabPerm(String userId, String dicitonaryId) throws QuadrigaStorageException {
		String dictCollabRole = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.dictionaryid =:dictionaryid and dictCollab.dictionaryCollaboratorDTOPK.collaboratoruser =:collaboratoruser");
			query.setParameter("dictionaryid", dicitonaryId);
			query.setParameter("collaboratoruser", userId);
			DictionaryCollaboratorDTO dictCollabDTO = (DictionaryCollaboratorDTO) query.uniqueResult();
			
			if(dictCollabDTO != null)
			{
				dictCollabRole = dictCollabDTO.getDictionaryCollaboratorDTOPK().getCollaboratorrole();
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryCollabPerm method :",e);
			throw new QuadrigaStorageException();
		}
		return dictCollabRole;
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
	public List<IDictionaryItem> getDictionaryItemsDetailsCollab(String dictionaryid) throws QuadrigaStorageException {
		List<IDictionaryItem> dictionaryItemList = null;
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
	public String deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Delete from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid");
			query.setParameter("dictionaryid",dictionaryId);
			query.setParameter("termid",itemid);
			int output = query.executeUpdate();
			if(output == 0)
			{
				logger.error("Item does not exists in this dictionary");
				errMsg = "Item does not exists in this dictionary";
				throw new QuadrigaStorageException();
			}
		}
		catch(Exception e)
		{
			logger.error("getDictionaryItemsDetailsCollab method :",e);
			errMsg = "Exception in the database";
			throw new QuadrigaStorageException();
		}
		return errMsg;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ICollaborator> showCollaboratingUsersRequest(String dictionaryid) throws QuadrigaStorageException {
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		List<ICollaborator> collaborators = new ArrayList<ICollaborator>();
		
		String userName = null;
		
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from DictionaryCollaboratorDTO dictCollab where dictCollab.dictionaryCollaboratorDTOPK.dictionaryid =:dictionaryid");
			query.setParameter("dictionaryid", dictionaryid);
			List<DictionaryCollaboratorDTO> dictCollabList = query.list();
			
			Iterator<DictionaryCollaboratorDTO> dictCollabIterator = dictCollabList.iterator();
			HashMap<String, String> userRoleMap = new HashMap<String, String>();
			while(dictCollabIterator.hasNext())
			{
				DictionaryCollaboratorDTO dictCollabDTO = dictCollabIterator.next();
				if(userRoleMap.containsKey(dictCollabDTO.getQuadrigaUserDTO().getUsername()))
				{
					String updatedRoleStr = userRoleMap.get(dictCollabDTO.getQuadrigaUserDTO().getUsername()).concat(dictCollabDTO.getDictionaryCollaboratorDTOPK().getCollaboratorrole()+",");
					userRoleMap.put(dictCollabDTO.getQuadrigaUserDTO().getUsername(), updatedRoleStr);
				}
				else
				{
					userRoleMap.put(dictCollabDTO.getQuadrigaUserDTO().getUsername(),dictCollabDTO.getDictionaryCollaboratorDTOPK().getCollaboratorrole()+",");
				}
			}
			
			Iterator<Entry<String, String>> userRoleMapItr = userRoleMap.entrySet().iterator();
			while(userRoleMapItr.hasNext())
			{
				Map.Entry pairs = (Map.Entry)userRoleMapItr.next();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				userName = (String) pairs.getKey();
				IUser user = userFactory.createUserObject();
				user = userManager.getUserDetails(userName);
				user.setUserName(userName);
				collaborator.setUserObj(user);
				String userRoleList = (String) pairs.getValue();
				collaboratorRoles =  splitAndgetCollaboratorRolesList(userRoleList.substring(0, userRoleList.length()-1));
				collaborator.setCollaboratorRoles(collaboratorRoles);
				
				collaborators.add(collaborator);
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return collaborators;
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param role
	 * @return List<ICollaboratorRole>
	 */
	public List<ICollaboratorRole> splitAndgetCollaboratorRolesList(String role)
	{
        String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;
		
		collabroles = role.split(",");
		
		for(int i=0;i<collabroles.length;i++)
		{
			collaboratorRole = collaboratorRoleManager.getDictCollaboratorRoleByDBId(collabroles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}
		
		return collaboratorRoleList;
	}
	
}
