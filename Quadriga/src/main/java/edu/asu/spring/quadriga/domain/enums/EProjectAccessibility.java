package edu.asu.spring.quadriga.domain.enums;

/**
 * @description   : enumerated data type to hold project accessibility values
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public enum EProjectAccessibility 
{
	PUBLIC("project_accessibility_accessible"),
	PRIVATE("project_accessibility_accessible");
	private String id;
	
	private EProjectAccessibility(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
}

