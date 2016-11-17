package edu.asu.spring.quadriga.aspects.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class executes at run time to check the access permissions for the given
 * element
 * 
 * @author Kiran Kumar
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementAccessPolicy {

    CheckedElementType type();

    // name of param :
    int paramIndex();

    // role of user
    String[] userRole();
}
