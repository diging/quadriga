package edu.asu.spring.quadriga.web.manageusers;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;

public class ModifyUserRolesController 
{
	@Autowired
	private CollaboratorFormValidator validator;
	
	@Autowired
	private IQuadrigaRoleManager rolemanager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
        validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "user.quadrigaRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
				String[] roleIds = text.split(",");
				List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
				for (String roleId : roleIds) {
					IQuadrigaRole role = rolemanager.getQuadrigaRole(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
		
	}
	
	
	public ModelAndView updateQuadrigaRolesRequest(@PathVariable("userName") String userName)
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		model = new ModelAndView();
		return model;
		//TO:DO
	}

}
