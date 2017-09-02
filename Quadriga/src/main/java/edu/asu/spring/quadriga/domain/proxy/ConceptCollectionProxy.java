package edu.asu.spring.quadriga.domain.proxy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

public class ConceptCollectionProxy implements IConceptCollection {

	private String conceptCollectionId;
	private String conceptCollectionName;
	private String description;
	private IUser owner;
	private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    
    /**
	 *  Full concept collection detail object. This would have object of type {@link ConceptCollection} 
	 */
	private IConceptCollection conceptCollection;
	
	/**
	 *  Access to {@link IConceptCollectionManager} to call manager methods to update actual {@link ConceptCollection} object.
	 */
	private IConceptCollectionManager conceptCollectionManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionProxy.class);
	
	/**
	 * Constructor to create {@link ConceptCollectionProxy} with {@link IConceptCollectionManager} manager object.
	 * @param conceptCollectionManager
	 */
	public ConceptCollectionProxy(IConceptCollectionManager conceptCollectionManager){
		this.conceptCollectionManager = conceptCollectionManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getConceptCollectionId() {
		return conceptCollectionId;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setConceptCollectionId(String conceptCollectionId) {
		this.conceptCollectionId = conceptCollectionId;
		if(this.conceptCollection != null){
			this.conceptCollection.setConceptCollectionId(conceptCollectionId);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getConceptCollectionName() {
		return conceptCollectionName;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setConceptCollectionName(String conceptCollectionName) {
		this.conceptCollectionName = conceptCollectionName;
		if(this.conceptCollection != null){
			this.conceptCollection.setConceptCollectionName(conceptCollectionName);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
		if(this.conceptCollection != null){
			this.conceptCollection.setDescription(description);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public IUser getOwner() {
		return owner;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
		if(this.conceptCollection != null){
			this.conceptCollection.setOwner(owner);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		if(this.conceptCollection != null){
			this.conceptCollection.setCreatedBy(createdBy);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		if(this.conceptCollection != null){
			this.conceptCollection.setCreatedDate(createdDate);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		if(this.conceptCollection != null){
			this.conceptCollection.setUpdatedBy(updatedBy);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IConceptCollection} object if it is not null
	 */
	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
		if(this.conceptCollection != null){
			this.conceptCollection.setUpdatedDate(updatedDate);
		}
	}

	public IConceptCollectionManager getConceptCollectionManager() {
		return conceptCollectionManager;
	}

	public void setConceptCollectionManager(
			IConceptCollectionManager conceptCollectionManager) {
		this.conceptCollectionManager = conceptCollectionManager;
	}
	
	
	/**
	 * This class helps in fetching the full dictionary object using dictionary manager object.
	 * Also sets the values of variables in {@link DictionaryProxy} to local {@link DictionaryP} object.
	 */
	private void setConceptCollectionDetails(){
		try{
			 this.conceptCollectionManager.fillConceptCollection(this.conceptCollection);
		}catch(QuadrigaStorageException e){
			logger.error("Issue accessing database from Concept Collection proxy",e);
		}
		
		
		this.conceptCollection.setConceptCollectionId(this.conceptCollectionId);
		this.conceptCollection.setConceptCollectionName(this.conceptCollectionName);
		this.conceptCollection.setDescription(this.description);
		this.conceptCollection.setUpdatedBy(this.updatedBy);
		this.conceptCollection.setUpdatedDate(this.updatedDate);
		this.conceptCollection.setOwner(this.owner);
		
		
	}
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IConceptCollection} object is null. 
	 * If its null it would use concept collection manager object to fetch full concept collection object and then return {@link List} of {@link IConceptCollectionCollaborator}
	 * else if local {@link IConceptCollection} is not null, just returns {@link List} of {@link IConceptCollectionCollaborator} from local {@link IConceptCollection}
	 */
	@Override
	public List<IConceptCollectionCollaborator> getConceptCollectionCollaborators() {
		if(this.conceptCollection == null){
			setConceptCollectionDetails();
		}
		return this.conceptCollection.getConceptCollectionCollaborators();
	}
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IConceptCollection} object is null. 
	 * If its null it would use manager object to fetch full concept collection object and then set {@link List} of {@link IConceptCollectionCollaborator}
	 * else if local {@link IConceptCollection} is not null, just set {@link List} of {@link IConceptCollectionCollaborator}
	 */
	@Override
	public void setConceptCollectionCollaborators(
			List<IConceptCollectionCollaborator> conceptCollectionCollaborators) {
		if(this.conceptCollection == null){
			setConceptCollectionDetails();
		}
		this.conceptCollection.setConceptCollectionCollaborators(conceptCollectionCollaborators);
	}

    public void setWorkspaces(List<IWorkspace> workspaces) {
        conceptCollection.setWorkspaces(workspaces);
    }

    public List<IWorkspace> getWorkspaces() {
        return conceptCollection.getWorkspaces();
    }

    public void setProjects(List<IProject> projects) {
        conceptCollection.setProjects(projects);
    }

    public List<IProject> getProjects() {
        return conceptCollection.getProjects();
    }

    public void setConcepts(List<IConcept> concepts) {
        conceptCollection.setConcepts(concepts);
    }

    public List<IConcept> getConcepts() {
        return conceptCollection.getConcepts();
    }

}
