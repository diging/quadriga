package edu.asu.spring.quadriga.domain.proxy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * This class acts a proxy to {@link WorkSpace} while fetching {@link List} of {@link IWorkSpace}. 
 * {@link WorkSpaceProxy} would load the {@link List} of associated using lazy load concept.
 * 
 * @author Lohith Dwaraka
 *
 */
public class WorkSpaceProxy implements IWorkSpace {
	
	private String workspaceId;
	private String workspaceName;
	private String description;
	private IUser owner;
	private IProject project;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	/**
	 *  Full workspace detail object. This would have object of type {@link WorkSpace} 
	 */
	private IWorkSpace workspace;
	/**
	 *  Access to {@link IListWSManager} to call manager methods to update actual {@link WorkSpace} object.
	 */
	private IListWSManager wsManager;
	private static final Logger logger = LoggerFactory
			.getLogger(WorkSpaceProxy.class);
	
	
	/**
	 * Constructor to create {@link WorkSpaceProxy} with {@link IListWSManager} manager object.
	 * @param wsManager							{@link IListWSManager} object
	 */
	public WorkSpaceProxy(IListWSManager wsManager) {
		this.wsManager = wsManager;
	}
	
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
		if(this.workspace!=null){
			this.workspace.setWorkspaceId(workspaceId);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getWorkspaceId() {
		return this.workspaceId;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
		if(this.workspace!=null){
			this.workspace.setWorkspaceName(workspaceName);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getWorkspaceName() {
		return this.workspaceName;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
		if(this.workspace != null){
			this.workspace.setDescription(description);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
		if(this.workspace != null){
			this.workspace.setOwner(owner);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public IUser getOwner() {
		return this.owner;
	}
	
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then return {@link List} of {@link IWorkspaceCollaborator}
	 * else if local {@link IWorkSpace} is not null, just returns {@link List} of {@link IWorkspaceCollaborator} from local {@link IWorkSpace}
	 */
	@Override
	public List<IWorkspaceCollaborator> getWorkspaceCollaborators() {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		return this.workspace.getWorkspaceCollaborators();
	}
	
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then set {@link List} of {@link IWorkspaceCollaborator}
	 * else if local {@link IWorkSpace} is not null, just set {@link List} of {@link IWorkspaceCollaborator}
	 */
	@Override
	public void setWorkspaceCollaborators(
			List<IWorkspaceCollaborator> workspaceCollaborators) {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		this.workspace.setWorkspaceCollaborators(workspaceCollaborators);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public IProject getProject() {
		return this.project;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setProject(IProject project) {
		this.project = project;
		if(this.workspace != null){
			this.workspace.setProject(project);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then return {@link List} of {@link IWorkspaceBitStream}
	 * else if local {@link IWorkSpace} is not null, just returns {@link List} of {@link IWorkspaceBitStream} from local {@link IWorkSpace}
	 */
	@Override
	public List<IWorkspaceBitStream> getWorkspaceBitStreams() {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		return this.workspace.getWorkspaceBitStreams();
	}
	
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then set {@link List} of {@link IWorkspaceBitStream}
	 * else if local {@link IWorkSpace} is not null, just set {@link List} of {@link IWorkspaceBitStream}
	 */
	@Override
	public void setWorkspaceBitStreams(
			List<IWorkspaceBitStream> workspaceBitStreams) {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		this.workspace.setWorkspaceBitStreams(workspaceBitStreams);
		
	}
	
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then return {@link List} of {@link IWorkspaceConceptCollection}
	 * else if local {@link IWorkSpace} is not null, just returns {@link List} of {@link IWorkspaceConceptCollection} from local {@link IWorkSpace}
	 */
	@Override
	public List<IWorkspaceConceptCollection> getWorkspaceConceptCollections() {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		return this.workspace.getWorkspaceConceptCollections();
	}
	
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then set {@link List} of {@link IWorkspaceConceptCollection}
	 * else if local {@link IWorkSpace} is not null, just set {@link List} of {@link IWorkspaceConceptCollection}
	 */
	@Override
	public void setWorkspaceConceptCollections(
			List<IWorkspaceConceptCollection> workspaceConceptCollections) {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		this.workspace.setWorkspaceConceptCollections(workspaceConceptCollections);
		
	}
	
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then return {@link List} of {@link IWorkspaceDictionary}
	 * else if local {@link IWorkSpace} is not null, just returns {@link List} of {@link IWorkspaceDictionary} from local {@link IWorkSpace}
	 */
	@Override
	public List<IWorkspaceDictionary> getWorkspaceDictinaries() {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		return this.workspace.getWorkspaceDictinaries();
	}
	
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then set {@link List} of {@link IWorkspaceDictionary}
	 * else if local {@link IWorkSpace} is not null, just set {@link List} of {@link IWorkspaceDictionary}
	 */
	@Override
	public void setWorkspaceDictionaries(
			List<IWorkspaceDictionary> workspaceDictionaries) {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		this.workspace.setWorkspaceDictionaries(workspaceDictionaries);
		
	}
	
	/**
	 * {@inheritDoc}
	 * This method checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then return {@link List} of {@link IWorkspaceNetwork}
	 * else if local {@link IWorkSpace} is not null, just returns {@link List} of {@link IWorkspaceNetwork} from local {@link IWorkSpace}
	 */
	@Override
	public List<IWorkspaceNetwork> getWorkspaceNetworks() {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		return this.workspace.getWorkspaceNetworks();
	}
	
	/**
	 * {@inheritDoc}
	 * This method also checks if local {@link IWorkSpace} object is null. 
	 * If its null it would use workspace manager object to fetch full workspace object and then set {@link List} of {@link IWorkspaceNetwork}
	 * else if local {@link IWorkSpace} is not null, just set {@link List} of {@link IWorkspaceNetwork}
	 */
	@Override
	public void setWorkspaceNetworks(List<IWorkspaceNetwork> workspaceNetworks) {
		if(this.workspace == null){
			setWorkSpaceDetails();
		}
		this.workspace.setWorkspaceNetworks(workspaceNetworks);
		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getCreatedBy() {
		return this.createdBy;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		if(this.workspace !=null){
			this.workspace.setCreatedBy(createdBy);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		if(this.workspace != null){
			this.workspace.setCreatedDate(createdDate);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getUpdatedBy() {
		return this.updatedBy;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		if(this.workspace != null){
			this.workspace.setUpdatedBy(updatedBy);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date getUpdatedDate() {
		return this.updatedDate;
	}
	
	/**
	 * {@inheritDoc}
	 * Also updates the local {@link IWorkSpace} object if it is not null
	 */
	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
		if(this.workspace != null){
			this.workspace.setUpdatedDate(updatedDate);
		}
	}

	/**
	 * This class helps in fetching the full workspace object using workspace manager object.
	 * Also sets the values of variables in {@link WorkSpaceProxy} to local {@link WorkSpace} object.
	 */
	private void setWorkSpaceDetails(){
		try{
			this.workspace =  this.wsManager.getWorkspaceDetails(this.workspaceId);
		}catch(QuadrigaStorageException e){
			logger.error("Issue accessing database from project proxy",e);
		} catch (QuadrigaAccessException e) {
			logger.error("Issue user accessing project",e);
		}
		this.workspace.setWorkspaceName(this.workspaceName);
		this.workspace.setWorkspaceId(this.workspaceId);
		this.workspace.setDescription(this.description);
		this.workspace.setUpdatedBy(this.updatedBy);
		this.workspace.setUpdatedDate(this.updatedDate);
		this.workspace.setOwner(this.owner);
		this.workspace.setProject(this.project);
		
	}
	
}
