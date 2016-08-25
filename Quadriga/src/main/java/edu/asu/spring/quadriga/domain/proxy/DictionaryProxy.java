package edu.asu.spring.quadriga.domain.proxy;


import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

/**
 * Dictionary class describing the properties 
 *                of a Dictionary object
 * 
 * @author      : Rohit Pendbhaje
 *
 */

public class DictionaryProxy implements IDictionary {

		private String name;
		private String description;
		private String id;
		private IUser owner;
		private String updatedBy;
		private String createdBy;
		private Date createdDate;
		private Date updatedDate; 
		
		private IDictionary dictionary;
		
		private IDictionaryManager dictionaryManager;
		
		public DictionaryProxy(IDictionaryManager dictionaryManager)
		{
			this.dictionaryManager = dictionaryManager;
		}
		
		public IDictionaryManager getDictionaryManager() {
			return dictionaryManager;
		}
		public void setDictionaryManager(IDictionaryManager dictionaryManager) {
			this.dictionaryManager = dictionaryManager;
		}
		@Override
		public void setDictionaryId(String id) {
			this.id = id;
			if(dictionary != null)
				this.dictionary.setDictionaryId(id);
		}
		@Override
		public String getDictionaryId() {
			return this.id;
		}
		@Override
		public void setDictionaryName(String dictionaryName) {
			this.name = dictionaryName;	
			if(dictionary!=null)
				this.dictionary.setDictionaryName(dictionaryName);
		}
		@Override
		public String getDictionaryName() {
			return this.name;
		}
		@Override
		public void setDescription(String description) {
			this.description = description;
			if(dictionary != null)
				this.dictionary.setDescription(description);
			
		}
		@Override
		public String getDescription() {
			return this.description;
		}
		@Override
		public void setOwner(IUser owner) {
			this.owner = owner;
			if(dictionary != null)
				this.dictionary.setOwner(owner);
			
		}
		@Override
		public IUser getOwner() {
			return this.owner;
		}
		
		private void setDictionaryDetails() throws QuadrigaStorageException
		{
			dictionary = dictionaryManager.getDictionaryDetails(this.name);
			
			this.dictionary.setDescription(this.description);
			this.dictionary.setDictionaryId(this.id);
			this.dictionary.setOwner(this.owner);
			this.dictionary.setCreatedBy(this.createdBy);
			this.dictionary.setUpdatedBy(this.updatedBy);
			this.dictionary.setCreatedDate(this.createdDate);
			this.dictionary.setUpdatedDate(this.updatedDate);
		}
		
		@Override
		public List<IDictionaryCollaborator> getDictionaryCollaborators() throws QuadrigaStorageException {

			if(this.dictionary == null)
				setDictionaryDetails();
			return this.dictionary.getDictionaryCollaborators();
		}
		
		@Override
		public void setDictionaryCollaborators(
				List<IDictionaryCollaborator> dictionaryCollaborators) throws QuadrigaStorageException {

			if(this.dictionary == null)
				setDictionaryDetails();
			
			this.dictionary.setDictionaryCollaborators(dictionaryCollaborators);
		}
		
		@Override
		public List<IDictionaryItems> getDictionaryItems() throws QuadrigaStorageException {

			if(this.dictionary == null)
				setDictionaryDetails();
			return this.dictionary.getDictionaryItems();
		}
		@Override
		public void setDictionaryItems(List<IDictionaryItems> dictionaryItems) throws QuadrigaStorageException {
			
			if(this.dictionary == null)
				setDictionaryDetails();
			this.dictionary.setDictionaryItems(dictionaryItems);
		}
		@Override
		public List<IProjectDictionary> getDictionaryProjects() throws QuadrigaStorageException {
			
			if(this.dictionary == null)
				setDictionaryDetails();			
			return this.dictionary.getDictionaryProjects();
		}
		@Override
		public void setDictionaryProjects(
				List<IProjectDictionary> dictionaryProjects) throws QuadrigaStorageException {
			
			if(this.dictionary == null)
				setDictionaryDetails();
			this.dictionary.setDictionaryProjects(dictionaryProjects);
			
		}
		@Override
		public List<IWorkspaceDictionary> getDictionaryWorkspaces() throws QuadrigaStorageException {
			
			if(this.dictionary == null)
				setDictionaryDetails();
			return this.dictionary.getDictionaryWorkspaces();
			
		}
		@Override
		public void setDictionaryWorkspaces(
				List<IWorkspaceDictionary> dictionaryWorkspaces) throws QuadrigaStorageException {
			
			if(dictionary == null)
				setDictionaryDetails();
			this.dictionary.setDictionaryWorkspaces(dictionaryWorkspaces);
			
		}
		@Override
		public String getCreatedBy() {
			return this.createdBy;
		}
		@Override
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
			if(dictionary != null)
				this.dictionary.setCreatedBy(createdBy);
		}
		@Override
		public Date getCreatedDate() {
			return this.createdDate;
		}
		@Override
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
			if(dictionary != null)
				this.dictionary.setCreatedDate(createdDate);	
		}
		@Override
		public String getUpdatedBy() {
			return this.updatedBy;
		}
		@Override
		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
			if(dictionary != null)
				this.dictionary.setUpdatedBy(updatedBy);
			
		}
		@Override
		public Date getUpdatedDate() {
			return this.updatedDate;
			
		}
		@Override
		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
			if(dictionary != null)
				this.dictionary.setUpdatedDate(updatedDate);
		}
		
}
