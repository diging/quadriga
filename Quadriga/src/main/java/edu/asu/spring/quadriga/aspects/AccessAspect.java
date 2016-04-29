package edu.asu.spring.quadriga.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;

/**
 * Aspect based Access authentication through UI. Can be used around a method in
 * any controllers to authenticate the user accessing through UI.
 * 
 * @author Kiran Kumar
 *
 */
@Aspect
@Component
public class AccessAspect {
    @Autowired
    private IAuthorizationManager authorizationManager;

    /**
     * This method prevents the access permissions check for the web package
     * methods annotated with 'noCheck'.
     * 
     * @param pjp
     * @param noCheck
     * @return ProceedingJoinPoint object
     * @throws Throwable
     */
    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(noCheck)")
    public Object chkProjectAuthorization(ProceedingJoinPoint pjp, NoAuthorizationCheck noCheck) throws Throwable {
        return pjp.proceed();
    }

    /**
     * This method checks the access permissions for the objects before
     * executing the methods.This provides run time access permission check to
     * the methods in the web package and annotated with 'checks'.
     * 
     * @param pjp
     * @param checks
     * @return - no access it throws Access Denied exception. if he have access
     *         returns ProceedingJointPoint object
     * @throws Throwable
     */
    @Around("within(edu.asu.spring.quadriga.web..*) && @annotation(checks)")
    public Object chkAuthorization(ProceedingJoinPoint pjp, AccessPolicies checks) throws Throwable {
        boolean haveAccess = true;

        // retrieve the logged in User name
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName().toLowerCase();
        // Loop through all the access policies specified
        ElementAccessPolicy[] policies = checks.value();

        for (ElementAccessPolicy policy : policies) {
            // retrieve the authorization object based on the type
            IAuthorization authorization = authorizationManager.getAuthorizationObject(policy.type());

            // calling the object
            if (policy.paramIndex() > 0) {
                String accessObjectId = pjp.getArgs()[policy.paramIndex() - 1].toString();
                haveAccess = authorization.chkAuthorization(userName, accessObjectId, policy.userRole());
            } else {
                haveAccess = authorization.chkAuthorizationByRole(userName, policy.userRole());
            }

            if (haveAccess)
                break;
        }

        if (!haveAccess) {
            throw new QuadrigaAccessException();
        }
        return pjp.proceed();
    }

}