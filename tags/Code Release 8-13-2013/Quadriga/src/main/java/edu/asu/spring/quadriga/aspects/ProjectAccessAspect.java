package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;

@Aspect
@Component
public class ProjectAccessAspect 
{
	
	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(noCheck)")
	public Object chkProjectAuthorization(ProceedingJoinPoint pjp, NoAuthorizationCheck noCheck) throws Throwable 
	{
		return pjp.proceed();
	}
	

	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(checks)")
	public Object chkAuthorization(ProceedingJoinPoint pjp, AccessPolicies checks) throws Throwable 
	{
		System.out.println("Checking project access " + pjp.getArgs());
//		return "auth/accessissue";
		Object retVal = pjp.proceed();
		return retVal;
	}
}
