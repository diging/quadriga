package edu.asu.spring.quadriga.aspects;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;

public interface IAuthorizationManager {

	public abstract IAuthorization getAuthorizationObject(CheckedElementType type);

}