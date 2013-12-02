package edu.asu.spring.quadriga.dao.sql.conceptcollection.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.conceptcollection.ICCManagerDAO;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptcollectionsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;

@Repository
public class CCManagerDAO extends DAOConnectionManager implements ICCManagerDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	protected ConceptCollectionDTOMapper conceptCollectionDTOMapper;
	
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
			sessionFactory.getCurrentSession().save(conceptcollectionsDTO);
		}
		catch(Exception e)
		{
			logger.info("getCollaboratedConceptsofUser method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}

}
