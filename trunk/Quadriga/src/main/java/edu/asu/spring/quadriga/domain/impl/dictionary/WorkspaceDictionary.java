package edu.asu.spring.quadriga.domain.impl.dictionary;

import java.sql.Date;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

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
	public String getupdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedDate(Date createdDate) {
		this.createdDate = createdDate;
		
	}

	@Override
	public Date getUpdatedDate() {
		return createdDate;
	}

}
