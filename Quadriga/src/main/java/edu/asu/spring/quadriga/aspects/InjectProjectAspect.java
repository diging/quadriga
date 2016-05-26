package edu.asu.spring.quadriga.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.PathVariable;

import edu.asu.spring.quadriga.aspects.annotations.GetProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

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
public abstract class InjectProjectAspect {

    public Object injectProject(ProceedingJoinPoint joinPoint) throws Throwable {
        // get all the values we need
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] paras = method.getParameters();

        int projectParamIdx = -1;
        int projVarIndex = -1;

        IProject project = null;
        Object[] arguments = joinPoint.getArgs();

        if (paras != null) {

            for (int i = 0; i < paras.length; i++) {
                Parameter p = paras[i];

                if (p.getAnnotation(InjectProject.class) != null) {
                    projectParamIdx = i;
                }

                if (p.getAnnotation(GetProject.class) != null) {
                    projVarIndex = i;
                }
            }

            if (projectParamIdx != -1) {

                String projectVar = (String) arguments[projVarIndex];
                project = getProject(projectVar);

                if (project == null)
                    return getErrorPage();

                arguments[projectParamIdx] = project;
            }

        }

        return joinPoint.proceed(arguments);
    }

    public abstract String getErrorPage();

    public abstract IProject getProject(String proj) throws QuadrigaStorageException;
}
