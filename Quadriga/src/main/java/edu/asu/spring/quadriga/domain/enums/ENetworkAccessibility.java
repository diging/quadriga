package edu.asu.spring.quadriga.domain.enums;

public enum ENetworkAccessibility 
{
	PROJECT_COLLABORATORS("default_accessibility_collaborator"),
	AUTHENTICATED_USERS("default_accessiblity_authenticated"),
	PUBLIC("default_accessibility_public");
	
	private String id;
	
	public String getId() 
	{
		return id;
	}

	private ENetworkAccessibility(String id)
	{
		this.id = id;
	}

}
