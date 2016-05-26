package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.AnnotationMisconfigurationException;
import edu.asu.spring.quadriga.rest.ConceptCollectionRestController;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This aspect checks if the requested page can be accessed by the user.
 * The aspect should be used only for controllers serving public pages. It requires
 * that the project backing a page is already injected into the method. It will then
 * check if the project is either public (then everybody can see the a page) or
 * if the current user is authenticated with Quadriga and is either the owner of
 * or a collaborator on the project.
 * 
 * This aspect only applies to methods in the package 'edu.asu.spring.quadriga.web' that
 * are annotated with {@link CheckPublicAccess}.
 * 
 * The aspect has to be executed after the {@link InjectProjectAspect}. Its order is 100.
 * 
 * @author Julia Damerow
 *
 */
@Aspect
@Order(value = 100)
@Component
public class PublicAccessAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(PublicAccessAspect.class);


    @Autowired
    private IRetrieveProjectManager projectManager;

    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(check)")
    public Object checkAccess(ProceedingJoinPoint joinPoint,
            CheckPublicAccess check) throws Throwable {
        Object[] args = joinPoint.getArgs();
        int idxOfProject = check.projectIndex();

        if (idxOfProject < 1 || idxOfProject > args.length) {
            throw new AnnotationMisconfigurationException("There are "
                    + args.length + " paramters, and you are trying to get "
                    + idxOfProject + ".");
        }

        Object projectObj = args[idxOfProject-1];

        if (!(projectObj instanceof IProject)) {
            throw new AnnotationMisconfigurationException(
                    "The parameter you are accessing is not a project.");
        }

        IProject project = (IProject) projectObj;

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String userName = auth.getName().toLowerCase();

        // if project is public or user can access the website because they are
        // the project owner or a collaborator on the project, proceed. Otherwise
        // show forbidden page.
        if (project.getProjectAccess() == EProjectAccessibility.PUBLIC
                || (userName != null && projectManager.canAccessProjectWebsite(
                        project.getUnixName(), userName))) {
            return joinPoint.proceed();
        } else {
            logger.debug("Access denied to: " + userName);
            return "public/forbidden";
        }
    }
}
