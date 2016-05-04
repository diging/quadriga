package edu.asu.spring.quadriga.aspects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckPublicAccess {

    /**
     * The projectIndex should point to the parameter that holds the 
     * project that needs to be checked. To keep it consistent with
     * {@link ElementAccessPolicy}, we'll start counting at 1.
     * 
     * @return
     */
    int projectIndex();
}
