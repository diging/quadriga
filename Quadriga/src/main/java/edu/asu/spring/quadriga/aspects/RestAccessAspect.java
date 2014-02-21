package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.aspects.annotations.RestAccessPolicies;
import edu.asu.spring.quadriga.exceptions.RestAccessException;

@Aspect
@Component
public class RestAccessAspect 
{
	@Autowired
	private RestAccessException restAccessException;
	
	private static final Logger logger = LoggerFactory
			.getLogger(RestAccessAspect.class);
	
	@Autowired
	private IAuthorizationManager authorizationManager;
	
	/**
	 * This method prevents the access permissions check for the web package methods
	 * annotated with 'noCheck' for rest interfaces
	 * @param pjp
	 * @param noCheck
	 * @return ProceedingJoinPoint object
	 * @throws Throwable
	 */
	@Around("within(edu.asu.spring.quadriga.rest..*) && @annotation(noCheck)")
	public Object chkProjectAuthorization(ProceedingJoinPoint pjp, NoAuthorizationCheck noCheck) throws Throwable 
	{
		return pjp.proceed();
	}
	
	/**
	 * This method checks the access permissions for the objects before executing the 
	 * methods.This provides run time access permission check to the methods in the 
	 * web package and annotated with 'checks' during rest interface access.
	 * @param pjp
	 * @param checks
	 * @return - no access it throws Access Denied exception.
	 *           if he have access returns ProceedingJointPoint object
	 * @throws Throwable
	 */
	@Around("within(edu.asu.spring.quadriga.rest..*) && @annotation(checks)")
	public Object chkAuthorization(ProceedingJoinPoint pjp, RestAccessPolicies checks) throws Throwable  
	{
		boolean haveAccess;
		String userName;
		String accessObjectId;
		IAuthorization authorization;
		
		haveAccess = true;
		
		//retrieve the logged in User name
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 userName = auth.getName();
		//Loop through all the access policies specified
		ElementAccessPolicy[] policies = checks.value();
		
		for(ElementAccessPolicy policy : policies)
		{
			//retrieve the authorization object based on the type
			authorization = authorizationManager.getAuthorizationObject(policy.type());
			
			//calling the object
			if(policy.paramIndex() > 0)
			{
			accessObjectId = pjp.getArgs()[policy.paramIndex()-1].toString();
			haveAccess = authorization.chkAuthorization(userName,accessObjectId, policy.userRole());
			}
			else
			{
				haveAccess = authorization.chkAuthorizationByRole(userName, policy.userRole());
			}
			
			if(haveAccess){
				break;
			}
		}
		
		if(!haveAccess)
		{
			return restAccessException.accessErrorMsg(400,"You don't have permission");
		}
		Object retVal = pjp.proceed();
		return retVal;
	}
	
}
