package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Aspect
@Order(value = 10)
@Component
public class InjectProjectByNameAspect extends InjectProjectAspect {

    @Autowired
    private IRetrieveProjectManager projectManager;
    
    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(ipName)")
    public Object injectProject(ProceedingJoinPoint joinPoint, InjectProjectByName ipName) throws Throwable {
        return super.injectProject(joinPoint);
    }

    @Override
    public String getErrorPage() {
        return "public/404";
    }

    @Override
    public IProject getProject(String proj) throws QuadrigaStorageException {
        return projectManager.getProjectDetailsByUnixName(proj);
    }

}
