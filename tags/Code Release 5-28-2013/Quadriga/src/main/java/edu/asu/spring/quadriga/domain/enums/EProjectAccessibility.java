package edu.asu.spring.quadriga.domain.enums;

/**
 * @description   : enumerated data type to hold project accessibility values
 * 
 * @author        : Kiran
 *
 */
public enum EProjectAccessibility 
{
	ACCESSIBLE("project_accessibility_accessible"),
	NOT_ACCESSIBLE("project_accessibility_accessible");
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

