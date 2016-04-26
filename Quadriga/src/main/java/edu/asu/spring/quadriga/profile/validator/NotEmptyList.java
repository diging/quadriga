package edu.asu.spring.quadriga.profile.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;


	
	@Constraint(validatedBy = NotEmptyListValidator.class)
	@Target( { ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NotEmptyList {
	  
		
		public abstract String message() default "{select some record}";
	    public abstract Class[] groups() default {};
	    public abstract Class[] payload() default {};
	
	}


