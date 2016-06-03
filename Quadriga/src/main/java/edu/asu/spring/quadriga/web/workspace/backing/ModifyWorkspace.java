package edu.asu.spring.quadriga.web.workspace.backing;

import org.springframework.stereotype.Service;

/**
 * This is domain class containing the workspace object variables 
 * required for displaying in the UI pages.
 * @author Kiran Batna
 *
 */
@Service
public class ModifyWorkspace 
{
	private String name;
	private String description;
	private String id;
	
	public ModifyWorkspace()
	{
		
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
