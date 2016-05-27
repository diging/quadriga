package edu.asu.spring.quadriga.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class intercepts all controller methods. If one of the methods is
 * annotated with {@link InjectProjectById} or {@link InjectProjectByName}, it
 * will find the parameter annotated with {@link ProjectIdentifier}. Retrieves
 * the Project Name/ Project Id in the variable and injects the projects into
 * the variable annotated with {@link InjectProject} For example, the following
 * method annotations:
 * 
 * <code>
 * @InjectProjectByName
 * public String showProject( @ProjectIdentifier @PathVariable("ProjectUnixName") String unixName, @InjectProject IProject project)
 * </code>
 * 
 * will result in a project object filled with the information of the project
 * with the unix name specified inthe unixName variable.
 * 
 * @author Julia Damerow, Nischal Samji
 *
 */
public abstract class InjectProjectAspect {

    public Object injectProject(ProceedingJoinPoint joinPoint) throws Throwable {

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

                if (p.getAnnotation(ProjectIdentifier.class) != null) {
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

    /**
     * This method returns the error page when there is no project associated
     * for the given project variable.
     * 
     * @return Returns the error page's model as a string
     */
    public abstract String getErrorPage();

    /**
     * @param proj
     *            Project Id/ Unix name to retrieve the associated project.
     * @return Returns a project object associated with the project id/ unix
     *         name.
     * @throws QuadrigaStorageException
     */
    public abstract IProject getProject(String proj) throws QuadrigaStorageException;
}
