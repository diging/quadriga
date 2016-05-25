package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;

@Aspect
@Order(value = 10)
@Component
public class InjectProjectByIdAspect extends InjectProjectAspect {

    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(ipName)")
    public Object injectProject(ProceedingJoinPoint joinPoint, InjectProjectByName ipName) throws Throwable{
       System.out.println(ipName);
        return super.injectProject(joinPoint);
        
    } 
    @Override
    public String getErrorPage() {
        return null;
    }

}
