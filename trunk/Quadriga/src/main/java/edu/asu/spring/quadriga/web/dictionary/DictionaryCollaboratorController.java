package edu.asu.spring.quadriga.web.dictionary;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DictionaryCollaboratorController {
	
	@Autowired
	IDictionaryManager dictionaryManager;
	
	@Autowired
	IUserManager userManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryCollaboratorController.class);
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	CollaboratorValidator collaboratorValidator;
	
	@Autowired
	ModifyCollaboratorFormManager collaboratorFormManager;
	
	@Autowired
	IModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
	    
		validateBinder.setValidator(collaboratorValidator);
		
		binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
	    @Override
	    public void setAsText(String text) {

	        IUser user;
			try {
				user = userManager.getUserDetails(text);
				setValue(user);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	    });
	    
	    binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

			   String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getDictCollaboratorRoleById(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
	    }); 
	}
	
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/showAddCollaborators" , method = RequestMethod.GET)
	public ModelAndView displayCollaborators(@PathVariable("dictionaryid") String dictionaryId) throws QuadrigaStorageException{
	
		ModelAndView modelAndView = new ModelAndView("auth/dictionaries/showAddCollaborators");
		modelAndView.getModelMap().put("dictionaryid", dictionaryId);
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		modelAndView.getModelMap().put("collaborator", collaborator); 

		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionaryId);
		modelAndView.getModelMap().put("nonCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getDictCollaboratorRoles();
		
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				iterator.remove();
			}
		}
		modelAndView.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
		
		List<ICollaborator> collaborators = dictionaryManager.showCollaboratingUsers(dictionaryId);
		modelAndView.getModelMap().put("collaboratingUsers", collaborators);
		
		return modelAndView;	
	}
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/addCollaborators" , method = RequestMethod.POST)
	public ModelAndView addCollaborators( @PathVariable("dictionaryid") String dictionaryId, 
			@Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result,
			Principal principal	) throws QuadrigaStorageException
	{
		ModelAndView model = null;
		String errmsg = "";
		String collaboratorUser = collaborator.getUserObj().getUserName();
		List<ICollaboratorRole> roles = collaborator.getCollaboratorRoles();
		model = new ModelAndView("auth/dictionaries/showAddCollaborators");
		

		if(result.hasErrors())
		{
			model.getModelMap().put("collaborator", collaborator);
		}
		
		else
		{
			String username = principal.getName();	
			errmsg = dictionaryManager.addCollaborators(collaborator, dictionaryId,collaboratorUser,username);		
			model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());

		}
				
		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionaryId);
		model.getModelMap().put("nonCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaborator> collaborators = dictionaryManager.showCollaboratingUsers(dictionaryId);
		model.getModelMap().put("collaboratingUsers", collaborators);
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getDictCollaboratorRoles();
		
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				iterator.remove();
			}
		}
		model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
		
		return model;
	
	} 
	
}
