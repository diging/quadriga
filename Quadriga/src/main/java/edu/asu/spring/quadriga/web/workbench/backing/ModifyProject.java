package edu.asu.spring.quadriga.web.workbench.backing;

import org.springframework.stereotype.Service;

/**
 * Domain class to hold the project variables to display in the
 * UI pages
 * @author Kiran Kumar Batna
 *
 */
@Service
public class ModifyProject 
{
	private String internalid;
	private String name;
	private String description;
	private String projectOwner;
	
	public ModifyProject()
	{
		
	}
	
	public String getInternalid() {
		return internalid;
	}
	public void setInternalid(String internalid) {
		this.internalid = internalid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}


}
