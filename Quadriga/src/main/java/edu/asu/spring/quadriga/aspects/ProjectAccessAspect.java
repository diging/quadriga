package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProjectAccessAspect 
{
	
	@Around("within(edu.asu.spring.quadriga.web..*)")
	public Object chkProjectAuthorization(ProceedingJoinPoint pjp) throws Throwable 
	{
		System.out.println("Checking project access");
		Object retVal = pjp.proceed();
		return retVal;
	}
	

}
