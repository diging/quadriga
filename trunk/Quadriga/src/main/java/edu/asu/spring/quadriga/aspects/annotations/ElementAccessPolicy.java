package edu.asu.spring.quadriga.aspects.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ElementAccessPolicy {

	CheckedElementType type();
	
	RetrievalMethod method();
	
	// name of param :
	String paramName();
	
	// role of user
	String[] userRole();
}
