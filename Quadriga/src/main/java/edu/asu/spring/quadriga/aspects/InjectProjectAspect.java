package edu.asu.spring.quadriga.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Aspect
@Component
public class InjectProjectAspect {
    
    @Autowired 
    private IRetrieveProjectManager projectManager;

    @Around("within(edu.asu.spring.quadriga.web..*)")
    public void injectProject(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature .getMethod();
        Parameter[] paras = method.getParameters();
        
        int projectParamIdx = 0;
        Map<String, Integer> pathParamMap = new HashMap<String, Integer>();
        InjectProject injectAnnotation = null;
        
        IProject project = null;
        if (paras != null) {
            for (int i=0; i<paras.length; i++) {
                Parameter p = paras[i];
                
                if (p.getAnnotation(InjectProject.class) != null) {
                    injectAnnotation = p.getAnnotation(InjectProject.class);
                    projectParamIdx = i;
                }
                if (p.getAnnotation(PathVariable.class) != null) {
                    PathVariable pathVar = p.getAnnotation(PathVariable.class);
                    pathParamMap.put(pathVar.value(), i);
                }
            }  
            
            Object[] arguments = joinPoint.getArgs();
            if (injectAnnotation != null) {
                Integer idxOfProjectId = pathParamMap.get(injectAnnotation.unixNameParameter());
                if (idxOfProjectId != null) {
                    String projectId = (String) arguments[idxOfProjectId];
                    project = projectManager.getProjectDetailsByUnixName(projectId);
                    
                    Object[] newArgs = new Object[arguments.length];
                    for(int i=0; i<newArgs.length; i++) {
                        if (i == projectParamIdx) {
                            newArgs[i] = project;
                        } else {
                            newArgs[i] = arguments[i];
                        }
                    }
                    
                    joinPoint.proceed(newArgs);
                }
            }
 
        }
        joinPoint.proceed();
    }
}
