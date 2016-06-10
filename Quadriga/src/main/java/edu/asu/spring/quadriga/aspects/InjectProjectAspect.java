package edu.asu.spring.quadriga.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

/**
 * This class intercepts all controller methods. If one of the methods is
 * annotated with {@link InjectProjectById} or {@link InjectProjectByName}, it
 * will find the parameter annotated with {@link ProjectIdentifier}. Retrieves
 * the Project Name/ Project Id in the variable and injects the projects into
 * the variable annotated with {@link InjectProject} For example, the following
 * method annotations:
 * 
 * <code>
 * &#64;InjectProjectByName
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
    
    @Autowired
    private IPassThroughProjectManager passThroughManager;

    public Object injectProject(ProceedingJoinPoint joinPoint) throws Throwable {

        // Get the Method signature and the arguments passed to the method.
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] paras = method.getParameters();

        int projectParamIdx = -1;
        int projVarIndex = -1;

        IProject project = null;

        // Get all arguments that are passed to the calling method.
        Object[] arguments = joinPoint.getArgs();

        // Loop through all the parameters and get the indices of the parameters
        // with annotations InjectProject and ProjectIdentifier.
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

                // Get the project based on the project identifier.
                String projectVar = (String) arguments[projVarIndex];
                project = getProject(projectVar);

                // let's see if there is a project with an external id
                if (project == null) {
                    project = getProjectByExternalId(projectVar);
                }
                
                // If there is no project, associated return an error page.
                if (project == null) {
                    return getErrorPage();
                }

                // Inject the project at the index
                arguments[projectParamIdx] = project;
            }

        }

        // Continue with the controller method.
        return joinPoint.proceed(arguments);
    }
    
    protected IProject getProjectByExternalId(String proj) throws QuadrigaStorageException {
        String[] parts = proj.split("\\+");
        // if not following convention, no projet is found
        if (parts.length != 2) {
            return null;
        }
        return passThroughManager.getPassthroughProject(parts[0], parts[1]);
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
