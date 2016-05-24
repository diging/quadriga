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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This class intercepts all controller methods. If one of the parameters of a
 * method is annotated with {@link InjectProject}, it will find the parameter
 * annotated with {@link PathVariable} that has refers to the same variable in
 * the path. It will then try to find the project with the unix name provided in
 * the path variable. For example, the following method annotations:
 * 
 * <code>
 * public String showProject(@PathVariable("ProjectUnixName") String unixName, @InjectProject(unixNameParameter = "ProjectUnixName") IProject project)
 * </code>
 * 
 * will result in a project object filled with the information of the project
 * with the unix name "unixName".
 * 
 * @author Julia Damerow
 *
 */
@Aspect
@Order(value = 10)
@Component
public abstract class InjectProjectAspect {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Around("within(edu.asu.spring.quadriga.web..*)")
    public Object injectProject(ProceedingJoinPoint joinPoint) throws Throwable {
        // get all the values we need
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] paras = method.getParameters();

        int projectParamIdx = -1;
        Map<String, Integer> pathParamMap = new HashMap<String, Integer>();
        InjectProject injectAnnotation = null;

        IProject project = null;
        Object[] arguments = joinPoint.getArgs();

        if (paras != null) {
            // iterate over parameters and find out where the unixname is
            // injected
            // and where the project should be injected
            for (int i = 0; i < paras.length; i++) {
                Parameter p = paras[i];

                // this tests if the paramters is annotated with InjectProject
                if (p.getAnnotation(InjectProject.class) != null) {
                    injectAnnotation = p.getAnnotation(InjectProject.class);
                    projectParamIdx = i;
                }
                // this gets all parameters annotated with PathVariable
                if (p.getAnnotation(PathVariable.class) != null) {
                    PathVariable pathVar = p.getAnnotation(PathVariable.class);
                    pathParamMap.put(pathVar.value(), i);
                }
            }

            // if we want to inject a project
            if (injectAnnotation != null) {
                // get the index of the parameter that holds the unix name from
                // the path
                Integer idxOfProjectId = pathParamMap.get(injectAnnotation.unixNameParameter());
                if (idxOfProjectId != null) {

                    // get project by its unix name
                    String projectUnixName = (String) arguments[idxOfProjectId];
                    project = projectManager.getProjectDetailsByUnixName(projectUnixName);

                    if (project == null)
                        return "public/404";

                    // replace the annotated project parameter with the
                    // retrieved project
                    Object[] newArgs = new Object[arguments.length];
                    for (int i = 0; i < newArgs.length; i++) {
                        if (i == projectParamIdx) {
                            newArgs[i] = project;
                        } else {
                            newArgs[i] = arguments[i];
                        }
                    }
                    // lets replace the old arguments with the new ones
                    arguments = newArgs;
                }
            }

        }

        // continue with controller method
        return joinPoint.proceed(arguments);
    }
}
