package edu.asu.spring.quadriga.domain.proxy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

public class DictionaryProxy implements IDictionary{

	private String dictionaryId;
	private String dictionaryName;
	private String description;
	private IUser owner;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
	/**
	 *  Full dictionary detail object. This would have object of type {@link Dictionary} 
	 */
	private IDictionary dictionary;
	
	/**
	 *  Access to {@link IRetrieveDictionaryManager} to call manager methods to update actual {@link Dictionary} object.
	 */
	private IRetrieveDictionaryManager dictionaryManager;
	
	/**
	 * Constructor to create {@link DictionaryProxy} with {@link IRetrieveDictionaryManager} manager object.
	 * @param dictionaryManager
	 */
	public DictionaryProxy(IRetrieveDictionaryManager dictionaryManager){
		this.dictionaryManager = dictionaryManager;
	}
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryProxy.class);
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getDictionaryId() {
		return dictionaryId;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
		if(this.dictionary != null){
			this.dictionary.setDictionaryId(dictionaryId);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getDictionaryName() {
		return dictionaryName;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
		if(this.dictionary != null){
			this.dictionary.setDictionaryName(dictionaryName);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
		if(this.dictionary != null){
			this.dictionary.setDescription(description);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
		if(this.dictionary != null){
			this.dictionary.setOwner(owner);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		if(this.dictionary != null){
			this.dictionary.setCreatedBy(createdBy);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		if(this.dictionary != null){
			this.dictionary.setCreatedDate(createdDate);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		if(this.dictionary != null){
			this.dictionary.setUpdatedBy(updatedBy);
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
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
		if(this.dictionary != null){
			this.dictionary.setUpdatedDate(updatedDate);
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 */
	public IDictionary getDictionary() {
		return dictionary;
	}
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IDictionary} object if it is not null
	 */
	public void setDictionary(IDictionary dictionary) {
		this.dictionary = dictionary;
	}
	/**
	 * Getter class for {@link IRetrieveDictionaryManager} object
	 * @return	Returns {@link IRetrieveDictionaryManager} object.
	 */
	public IRetrieveDictionaryManager getDictionaryManager() {
		return dictionaryManager;
	}
	/**
	 * Setter class for {@link IRetrieveDictionaryManager} manager object
	 * @param dictionaryManager	{@link IRetrieveDictionaryManager} object to get access to dictionary manager layer 
	 */
	public void setDictionaryManager(IRetrieveDictionaryManager dictionaryManager) {
		this.dictionaryManager = dictionaryManager;
	}

	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IDictionaryCollaborator} object is null. 
	 * If its null it would use dictionary manager object to fetch full dictionary object and then return {@link List} of {@link IDictionaryCollaborator}
	 * else if local {@link IDictionary} is not null, just returns {@link List} of {@link IDictionaryCollaborator} from local {@link IDictionary}
	 */
	@Override
	public List<IDictionaryCollaborator> getDictionaryCollaborators() {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		return this.dictionary.getDictionaryCollaborators();
	}
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IDictionary} object is null. 
	 * If its null it would use manager object to fetch full dictionary object and then set {@link List} of {@link IDictionaryCollaborator}
	 * else if local {@link IDictionary} is not null, just set {@link List} of {@link IDictionaryCollaborator}
	 */
	@Override
	public void setDictionaryCollaborators(List<IDictionaryCollaborator> dictionaryCollaborators) {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		this.dictionary.setDictionaryCollaborators(dictionaryCollaborators);
	}
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IDictionaryItems} object is null. 
	 * If its null it would use dictionary manager object to fetch full dictionary object and then return {@link List} of {@link IDictionaryItems}
	 * else if local {@link IDictionary} is not null, just returns {@link List} of {@link IDictionaryItems} from local {@link IDictionary}
	 */
	@Override
	public List<IDictionaryItems> getDictionaryItems() {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		return this.dictionary.getDictionaryItems();
	}
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IDictionary} object is null. 
	 * If its null it would use manager object to fetch full dictionary object and then set {@link List} of {@link IDictionaryItems}
	 * else if local {@link IDictionary} is not null, just set {@link List} of {@link IDictionaryItems}
	 */
	@Override
	public void setDictionaryItems(List<IDictionaryItems> dictionaryItems) {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		this.dictionary.setDictionaryItems(dictionaryItems);
	}
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IProjectDictionary} object is null. 
	 * If its null it would use dictionary manager object to fetch full dictionary object and then return {@link List} of {@link IProjectDictionary}
	 * else if local {@link IDictionary} is not null, just returns {@link List} of {@link IProjectDictionary} from local {@link IDictionary}
	 */
	@Override
	public List<IProjectDictionary> getDictionaryProjects() {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		return this.dictionary.getDictionaryProjects();
	}
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IDictionary} object is null. 
	 * If its null it would use manager object to fetch full dictionary object and then set {@link List} of {@link IProjectDictionary}
	 * else if local {@link IDictionary} is not null, just set {@link List} of {@link IProjectDictionary}
	 */
	@Override
	public void setDictionaryProjects(
			List<IProjectDictionary> dictionaryProjects) {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		this.dictionary.setDictionaryProjects(dictionaryProjects);
	}
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkspaceDictionary} object is null. 
	 * If its null it would use dictionary manager object to fetch full dictionary object and then return {@link List} of {@link IWorkspaceDictionary}
	 * else if local {@link IDictionary} is not null, just returns {@link List} of {@link IWorkspaceDictionary} from local {@link IDictionary}
	 */
	@Override
	public List<IWorkspaceDictionary> getDictionaryWorkspaces() {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		return this.dictionary.getDictionaryWorkspaces();
	}
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IDictionary} object is null. 
	 * If its null it would use manager object to fetch full dictionary object and then set {@link List} of {@link IWorkspaceDictionary}
	 * else if local {@link IDictionary} is not null, just set {@link List} of {@link IWorkspaceDictionary}
	 */
	@Override
	public void setDictionaryWorkspaces(
			List<IWorkspaceDictionary> dictionaryWorkspaces) {
		if(this.dictionary == null){
			setDictionaryDetails();
		}
		this.dictionary.setDictionaryWorkspaces(dictionaryWorkspaces);
	}
	/**
	 * This class helps in fetching the full dictionary object using dictionary manager object.
	 * Also sets the values of variables in {@link DictionaryProxy} to local {@link DictionaryP} object.
	 */
	private void setDictionaryDetails(){
		try{
			this.dictionary =  this.dictionaryManager.getDictionaryDetails(this.dictionaryId);
		}catch(QuadrigaStorageException e){
			logger.error("Issue accessing database from dictionary proxy",e);
		}
		
		
		this.dictionary.setDictionaryId(this.dictionaryId);
		this.dictionary.setDictionaryName(this.dictionaryName);
		this.dictionary.setDescription(this.description);
		this.dictionary.setUpdatedBy(this.updatedBy);
		this.dictionary.setUpdatedDate(this.updatedDate);
		this.dictionary.setOwner(this.owner);
		
		
	}

}
