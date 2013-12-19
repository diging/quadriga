package edu.asu.spring.quadriga.dao.sql.conceptcollection.impl;

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

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.ConceptcollectionsCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsItemsDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsItemsDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * @author karthik
 *
 */
@Repository
public class CCManagerDAO extends DAOConnectionManager implements IDBConnectionCCManager {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	protected ConceptCollectionDTOMapper conceptCollectionDTOMapper;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CCManagerDAO.class);
	
	/**
	 * Queries the database and builds a list of concept collection objects owned by particular user
	 * 
	 * @return List containing user objects of all collections of the user
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IConceptCollection> getConceptsOwnedbyUser(String userName) throws QuadrigaStorageException 
	{
		List<IConceptCollection> conceptCollectionList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsDTO concept where concept.collectionowner.username =:userName");
			query.setParameter("userName", userName);
			List<ConceptcollectionsDTO> conceptcollectionsDTOList = query.list();
			
			if(conceptcollectionsDTOList != null && conceptcollectionsDTOList.size() > 0)
			{
				conceptCollectionList = conceptCollectionDTOMapper.getConceptCollectionList(conceptcollectionsDTOList);
			}
		}
		catch(Exception e)
		{
			logger.info("getConceptsOwnedbyUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return conceptCollectionList;
	}
	
	
	/**
	 * Queries the database and builds a list of concept collection objects with the user as collaborator
	 * 
	 * @return List containing user objects of all collections of the user
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(String userName) throws QuadrigaStorageException 
	{
		List<IConceptCollection> conceptCollectionList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select conceptCollab.conceptcollectionsDTO from ConceptcollectionsCollaboratorDTO conceptCollab where conceptCollab.quadrigaUserDTO.username =:userName");
			query.setParameter("userName", userName);
			List<ConceptcollectionsDTO> conceptcollectionsDTOList = query.list();
			
			if(conceptcollectionsDTOList != null && conceptcollectionsDTOList.size() > 0)
			{
				conceptCollectionList = conceptCollectionDTOMapper.getConceptCollectionList(conceptcollectionsDTOList);
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return conceptCollectionList;
	}
	
	/**
	 * Save the concept collection to the database
	 * 
	 * @return List containing user objects of all collections of the user
	 * @throws QuadrigaStorageException 
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addCollection(IConceptCollection conceptCollection) throws QuadrigaStorageException 
	{
		try
		{
			ConceptcollectionsDTO conceptcollectionsDTO = conceptCollectionDTOMapper.getConceptCollectionDTO(conceptCollection);
			conceptcollectionsDTO.setId(generateUniqueID());
			sessionFactory.getCurrentSession().save(conceptcollectionsDTO);
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}
	
	@Override
	public void getCollectionDetails(IConceptCollection collection,String username) throws QuadrigaStorageException, QuadrigaAccessException {
		IUser owner = null;
		collection.setCollaborators(new ArrayList<ICollaborator>());
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsDTO conceptColl where conceptColl.id =:id");
			query.setParameter("id", collection.getId());
			
			ConceptcollectionsDTO conceptcollectionsDTO = (ConceptcollectionsDTO) query.uniqueResult();
			
			if(conceptcollectionsDTO != null)
			{
				collection.setDescription(conceptcollectionsDTO.getDescription());
				collection.setName(conceptcollectionsDTO.getCollectionname());
				owner = userManager.getUserDetails(conceptcollectionsDTO.getCollectionowner().getUsername());
				collection.setOwner(owner);
				
				if(conceptcollectionsDTO.getConceptcollectionsItemsDTOList() != null && conceptcollectionsDTO.getConceptcollectionsItemsDTOList().size() > 0)
				{
					Iterator<ConceptcollectionsItemsDTO> ccItemsIterator = conceptcollectionsDTO.getConceptcollectionsItemsDTOList().iterator();
					while(ccItemsIterator.hasNext())
					{
						collection.addItem(conceptCollectionDTOMapper.getConceptCollectionItems(ccItemsIterator.next()));
					}	
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * Get the user list of non owners and collaborators
	 * @param collectionid
	 * @return List of users
	 * @throws QuadrigaStorageException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IUser> showNonCollaboratorRequest(String collectionid) throws QuadrigaStorageException
	{
		List<IUser> userList = new ArrayList<IUser>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from QuadrigaUserDTO user where user.username NOT IN (Select quadrigaUserDTO.username from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id)  AND user.username NOT IN (Select ccCollab.conceptcollectionsDTO.collectionowner.username from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id)");
			query.setParameter("id", collectionid);
			
			List<QuadrigaUserDTO> quadrigaUserDTOList = query.list();
			if(quadrigaUserDTOList != null && quadrigaUserDTOList.size() > 0)
			{
				userList = userDTOMapper.getUsers(quadrigaUserDTOList);
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return userList;
	}
	
	/**
	 * retrieves data from database to retrieve collaborators
	 * 
	 * @param collectionid
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ICollaborator> showCollaboratorRequest(String collectionid) throws QuadrigaStorageException 
	{
		List<ICollaborator> collaboratorList = new ArrayList<ICollaborator>();
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id");
			query.setParameter("id", collectionid);
			List<ConceptcollectionsCollaboratorDTO> ccCollabList = query.list();
			
			Iterator<ConceptcollectionsCollaboratorDTO> ccCollabIterator = ccCollabList.iterator();
			HashMap<String, String> userRoleMap = new HashMap<String, String>();
			while(ccCollabIterator.hasNext())
			{
				ConceptcollectionsCollaboratorDTO ccCollaboratorDTO = ccCollabIterator.next();
				if(userRoleMap.containsKey(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername()))
				{
					userRoleMap.get(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername()).concat(ccCollaboratorDTO.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole()+",");
				}
				else
				{
					userRoleMap.put(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername(),ccCollaboratorDTO.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole()+",");
				}
			}
			
			Iterator<Entry<String, String>> userRoleMapItr = userRoleMap.entrySet().iterator();
			while(userRoleMapItr.hasNext())
			{
				Map.Entry pairs = (Map.Entry)userRoleMapItr.next();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				IUser user = userFactory.createUserObject();
				user = userManager.getUserDetails((String) pairs.getKey());
				collaborator.setUserObj(user);
				//fetch the collaborator roles
				String userRoleList = (String) pairs.getValue();
				collaboratorRoles = splitAndgetCollaboratorRolesList(userRoleList.substring(0, userRoleList.length()-2));
				collaborator.setCollaboratorRoles(collaboratorRoles);
				collaboratorList.add(collaborator);
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return collaboratorList;
	}
	
	/**
	 * splits the comma seperated roles string coming from database and converts
	 * into list of roles
	 * 
	 * @param role
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 * 
	 */
	public List<ICollaboratorRole> splitAndgetCollaboratorRolesList(String role) {
		if(role == null || role.isEmpty())
		{
			logger.error("splitAndgetCollaboratorRolesList: input argument role is null");
			return null;
		}
		String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;

		collabroles = role.split(",");

		for (int i = 0; i < collabroles.length; i++) {
			collaboratorRole = collaboratorRoleManager.getCollectionCollabRoleByDBId(collabroles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}

		return collaboratorRoleList;
	}

	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager#saveItem(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveItem(String lemma, String id, String pos, String desc,
			String conceptId, String username) throws QuadrigaStorageException,
			QuadrigaAccessException {
		String errMsg = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from QuadrigaUserDTO user where user.username =:username and ( user.username IN (Select quadrigaUserDTO.username from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id) OR user.username IN (Select conceptColl.collectionowner.username from ConceptcollectionsDTO conceptColl where conceptColl.id =:id))");
			query.setParameter("username", username);
			query.setParameter("id", conceptId);
			
			List<QuadrigaUserDTO> quadrigaUserDTOList = query.list();
			if(quadrigaUserDTOList != null && quadrigaUserDTOList.size() > 0)
			{
				ConceptcollectionsItemsDTO conceptcollectionsItemsDTO = new ConceptcollectionsItemsDTO();
				conceptcollectionsItemsDTO.setCreateddate(new Date());
				conceptcollectionsItemsDTO.setDescription(desc);
				conceptcollectionsItemsDTO.setLemma(lemma);
				conceptcollectionsItemsDTO.setPos(pos);
				conceptcollectionsItemsDTO.setUpdateddate(new Date());
				conceptcollectionsItemsDTO.setConceptcollectionsItemsDTOPK(new ConceptcollectionsItemsDTOPK(conceptId, id));
				sessionFactory.getCurrentSession().save(conceptcollectionsItemsDTO);
			}
			else
			{
				errMsg = "User dont have access to the collection";
				logger.error(errMsg);
				throw new QuadrigaAccessException("Hmmm!!  Need to try much more hard to get into this");
			}
		}
		catch(Exception e)
		{
			logger.info("saveItem method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}

	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager#validateId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String validateId(String collectionname) throws QuadrigaStorageException {
		String errMsg = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ConceptcollectionsDTO.findByCollectionname");
			query.setParameter("collectionname", collectionname);
			List<ConceptcollectionsDTO> conceptcollectionsDTOList = query.list();
			
			if(! (conceptcollectionsDTOList != null  && conceptcollectionsDTOList.size()> 0 ) )
			{
				errMsg = "collection id is invalid.";
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return errMsg;
	}


	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager#deleteItems(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String deleteItems(String id, String collectionId, String username) throws QuadrigaStorageException {
		String errMsg = null;
		if(id == null || collectionId==null || username==null || id.isEmpty() || collectionId.isEmpty() || username.isEmpty())
		{
			logger.error("deleteItems: input argument IConceptCollection is null");
			return null;
		}
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsItemsDTO ccItems where ccItems.conceptcollectionsDTO.id =:collectionId and ccItems.conceptcollectionsItemsDTOPK.item =:id");
			query.setParameter("id", id);
			query.setParameter("collectionId", collectionId);
			List<ConceptcollectionsItemsDTO> ccItemsDTOList = query.list();
			
			if(ccItemsDTOList != null && ccItemsDTOList.size() > 0)
			{
				sessionFactory.getCurrentSession().delete(ccItemsDTOList.get(0));
			}
			else
			{
				errMsg = "collection id is invalid.";
			}
		}
		catch(Exception e)
		{
			logger.info("deleteItems method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return errMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateItem(IConcept concept, String collectionId,String username) throws QuadrigaStorageException {
		String errMsg = null;
		if(concept == null || collectionId==null || username==null ||  collectionId.isEmpty() || username.isEmpty())
		{
			logger.error("updateItem: input argument IConceptCollection is null");
			return null;
		}
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsItemsDTO ccItems where ccItems.conceptcollectionsDTO.id =:collectionId and ccItems.conceptcollectionsItemsDTOPK.item =:id");
			query.setParameter("id", concept.getId());
			query.setParameter("collectionId", collectionId);
			List<ConceptcollectionsItemsDTO> ccItemsDTOList = query.list();
			
			if(ccItemsDTOList != null && ccItemsDTOList.size() > 0)
			{
				ConceptcollectionsItemsDTO ccItemsDTO = ccItemsDTOList.get(0);
				ccItemsDTO.setLemma(concept.getLemma());
				ccItemsDTO.setPos(concept.getPos());
				ccItemsDTO.setDescription(concept.getDescription());
				ccItemsDTO.setUpdateddate(new Date());
				sessionFactory.getCurrentSession().update(ccItemsDTOList.get(0));
			}
			else
			{
				errMsg = "collection id is invalid.";
			}
		}
		catch(Exception e)
		{
			logger.info("deleteItems method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return errMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getConceptCollectionId(String ccName) throws QuadrigaStorageException {
		String ccID = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ConceptcollectionsDTO.findByCollectionname");
			query.setParameter("collectionname", ccName);
			List<ConceptcollectionsDTO> ccIDList = query.list();
			
			if(ccIDList != null  && ccIDList.size()> 0)
			{
				ccID = ccIDList.get(0).getId();
			}
		}
		catch(Exception e)
		{
			logger.info("deleteItems method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return ccID;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getCollaborators(IConceptCollection collection) throws QuadrigaStorageException {
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id");
			query.setParameter("id", collection.getId());
			List<ConceptcollectionsCollaboratorDTO> ccCollabList = query.list();
			
			if(ccCollabList != null && ccCollabList.size() > 0)
			{
				Iterator<ConceptcollectionsCollaboratorDTO> ccCollabIterator = ccCollabList.iterator();
				HashMap<String, String> userRoleMap = new HashMap<String, String>();
				while(ccCollabIterator.hasNext())
				{
					ConceptcollectionsCollaboratorDTO ccCollaboratorDTO = ccCollabIterator.next();
					if(userRoleMap.containsKey(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername()))
					{
						String updatedRoleStr = userRoleMap.get(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername()).concat(ccCollaboratorDTO.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole()+",");
						userRoleMap.put(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername(), updatedRoleStr);
					}
					else
					{
						userRoleMap.put(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername(),ccCollaboratorDTO.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole()+",");
					}
				}
				
				Iterator<Entry<String, String>> userRoleMapItr = userRoleMap.entrySet().iterator();
				while(userRoleMapItr.hasNext())
				{
					Map.Entry pairs = (Map.Entry)userRoleMapItr.next();
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user = userManager.getUserDetails((String) pairs.getKey());
					collaborator.setUserObj(user);
					String userRoleList = (String) pairs.getValue();
					collaboratorRoles = splitAndgetCollaboratorRolesList(userRoleList.substring(0, userRoleList.length()-1));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					collection.getCollaborators().add(collaborator);
				}
			}	
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}

}
