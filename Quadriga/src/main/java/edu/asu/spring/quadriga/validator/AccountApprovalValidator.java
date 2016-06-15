package edu.asu.spring.quadriga.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.manageusers.beans.ApproveAccount;

/**
 * Validator for checking approval/rejection object when approving or rejecting
 * account requests.
 * 
 * @author jdamerow
 *
 */
public class AccountApprovalValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(ApproveAccount.class);
    }

    @Override
    public void validate(Object arg0, Errors err) {
        ApproveAccount account = (ApproveAccount) arg0;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "action", "account_approval.action.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "username", "account_approval.error");
        
        if (account.getAction() == null) {
            return;
        }
        
        if (account.getAction().equals("approve")) {
            ValidationUtils.rejectIfEmpty(err, "roles", "account_approval.account_roles.required"); 
        }
        
    }

}
