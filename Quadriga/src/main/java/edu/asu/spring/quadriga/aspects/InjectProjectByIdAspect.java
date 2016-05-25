package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;

public class InjectProjectByIdAspect extends InjectProjectAspect {

    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(ipId)")
    public Object injectProject(ProceedingJoinPoint joinPoint, InjectProjectById ipId) throws Throwable {
        return super.injectProject(joinPoint);
    }
    
    @Override
    public String getErrorPage() {
        return null;
    }

}
