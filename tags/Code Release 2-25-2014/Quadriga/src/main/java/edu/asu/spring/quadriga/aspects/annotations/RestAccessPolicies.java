package edu.asu.spring.quadriga.aspects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User defined annotation for rest interface methods to be exceuted 
 * at run time
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RestAccessPolicies {

	ElementAccessPolicy[] value();
}
