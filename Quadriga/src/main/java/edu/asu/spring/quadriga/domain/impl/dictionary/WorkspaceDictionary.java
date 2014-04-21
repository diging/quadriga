package edu.asu.spring.quadriga.domain.impl.dictionary;


import java.util.Date;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;

public class WorkspaceDictionary implements IWorkspaceDictionary {

	private IWorkSpace workspace;
	private IDictionary dictionary;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
	@Override
	public void setWorkspace(IWorkSpace workspace) {
		this.workspace = workspace;
	}

	@Override
	public IWorkSpace getWorkspace() {
		return workspace;
	}

	@Override
	public void setDictionary(IDictionary dictionary) {
		this.dictionary = dictionary;
	}

	@Override
	public IDictionary getDictionary() {
		return dictionary;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}

}
