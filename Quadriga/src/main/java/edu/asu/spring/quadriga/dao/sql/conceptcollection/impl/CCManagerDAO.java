package edu.asu.spring.quadriga.dao.sql.conceptcollection.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.conceptcollection.ICCManagerDAO;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.dto.ConceptcollectionsCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsItemsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Repository
public class CCManagerDAO extends DAOConnectionManager implements ICCManagerDAO {

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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void getCollectionDetails(IConceptCollection collection,String username) throws QuadrigaStorageException, QuadrigaAccessException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsItemsDTO ccItems where ccItems.conceptcollectionsDTO.id =:id and  ccItems.conceptcollectionsDTO.collectionowner.username =:username and ccItems.conceptcollectionsDTO.conceptcollectionsCollaboratorDTOList IS NULL ");
			query.setParameter("id", collection.getId());
			query.setParameter("userName", username);
			
			List<ConceptcollectionsItemsDTO> conceptcollectionsItemsDTOList = query.list();
			
			if(conceptcollectionsItemsDTOList != null && conceptcollectionsItemsDTOList.size() > 0)
			{
				Iterator<ConceptcollectionsItemsDTO> ccItemsIterator = conceptcollectionsItemsDTOList.iterator();
				while(ccItemsIterator.hasNext())
				{
					collection.addItem(conceptCollectionDTOMapper.getConceptCollectionItems(ccItemsIterator.next()));
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
	@Override
	public List<ICollaborator> showCollaboratorRequest(String collectionid) throws QuadrigaStorageException 
	{
		List<IUser> userList = new ArrayList<IUser>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptcollectionsCollaboratorDTO ccCollab where ccCollab.conceptcollectionsDTO.id =:id GROUP BY ccCollab.quadrigaUserDTO.username ");
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
					userRoleMap.put(ccCollaboratorDTO.getQuadrigaUserDTO().getUsername(),ccCollaboratorDTO.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole());
				}
			}
			
			/*if(ccCollabList != null && ccCollabList.size() > 0)
			{
				
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				IUser user = userFactory.createUserObject();
				user = userManager.getUserDetails(resultset.getString(1));
				collaborator.setUserObj(user);
				//fetch the collaborator roles
				collaboratorRoles = splitAndgetCollaboratorRolesList(resultset.getString(2));

				collaborator.setCollaboratorRoles(collaboratorRoles);
				collaboratorList.add(collaborator);
				
				userList = userDTOMapper.getUsers(quadrigaUserDTOList);
			}*/
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return null;
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

}
