package edu.asu.spring.quadriga.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckAccessById;
import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.AnnotationMisconfigurationException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This aspect checks if the requested page can be accessed by the user. The
 * aspect should be used only for controllers serving public pages. It requires
 * that the project backing a page is already injected into the method. It will
 * then check if the project is either public (then everybody can see the a
 * page) or if the current user is authenticated with Quadriga and is either the
 * owner of or a collaborator on the project.
 * 
 * This aspect only applies to methods in the package
 * 'edu.asu.spring.quadriga.web' that are annotated with
 * {@link CheckPublicAccess}. The project parameter that should be checked for
 * public access has to be annotated with {@link CheckAccess}.
 * 
 * The aspect has to be executed after the {@link InjectProjectAspect}. Its
 * order is 100.
 * 
 * @author Julia Damerow
 *
 */
@Aspect
@Order(value = 100)
@Component
public class AccessByIdAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccessByIdAspect.class);

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(check)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, CheckAccessById check) throws Throwable {

        // Get the Method signature and the arguments passed to the method.
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] paras = method.getParameters();
        Object[] args = joinPoint.getArgs();

        int projectIdx = -1;
        Object projectIdObj = null;
        // Loop through all the parameters and get the indices of the parameters
        // with annotations CheckAccess.
        if (paras != null) {

            for (int i = 0; i < paras.length; i++) {
                Parameter p = paras[i];

                if (p.getAnnotation(CheckAccessById.class) != null) {
                    projectIdx = i;
                }
            }

            if (projectIdx == -1) {
                throw new AnnotationMisconfigurationException("There is no parameter with a CheckAccess annotation.");
            }

            projectIdObj = args[projectIdx];
        }

        if (!(String.class.isAssignableFrom(projectIdObj.getClass()))) {
            System.out.println(projectIdObj.getClass());
            throw new AnnotationMisconfigurationException("The parameter you are accessing is not a projectId.");
        }

        String projectId = (String) projectIdObj;

        IProject project = projectManager.getProjectDetails(projectId);
        if (project == null) {
            throw new AnnotationMisconfigurationException("Project does not exist.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName().toLowerCase();

        // if project is public or user can access the website because they are
        // the project owner or a collaborator on the project, proceed.
        // Otherwise
        // show forbidden page.
        if (project.getProjectAccess() == EProjectAccessibility.PUBLIC
                || (userName != null && projectManager.canAccessProjectWebsite(project.getUnixName(), userName))) {
            return joinPoint.proceed();
        } else {
            logger.debug("Access denied to: " + userName);
            return "public/forbidden";
        }
    }
}