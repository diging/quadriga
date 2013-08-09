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
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/showCollaborators" , method = RequestMethod.GET)
	public String displayCollaborators(@PathVariable("dictionaryid") String dictionary_id, ModelMap model){
		
		logger.info("coming to this page");
		model.addAttribute("dictionaryid", dictionary_id);
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator); 

		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionary_id);
		model.addAttribute("nonCollaboratingUsers", nonCollaboratingUsers);
		
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
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		
		List<ICollaborator> collaborators = dictionaryManager.showCollaboratingUsers(dictionary_id);
		model.addAttribute("collaboratingUsers", collaborators);
		
		return "auth/dictionaries/showCollaborators";
	}


	/*@ModelAttribute
	public ICollaborator getCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	} */
	
	
	/*@RequestMapping(value="auth/dictionaries/{dictionaryid}/addCollaborators" , method = RequestMethod.POST)
	public String addDictCollaborators(HttpServletRequest req,@PathVariable("dictionaryid") String dictionary_id, 
			 ModelMap model, Principal principal)
	{
		
		String [] values = req.getParameterValues("roleselected");
		String username = req.getParameter("userName");
		String errmsg = "";
		
		String sessionUser = principal.getName();
		logger.info("Username selected in the jsp "+ username);
		
		if(values != null && username != null){
			
			errmsg = dictionaryManager.addCollaborators(values, dictionary_id, username, sessionUser);
			
			logger.info("Checkbox data is null");	
			
		}
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getDictCollaboratorRoles();
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();

		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals("ADMIN"))
			{
				iterator.remove();
			}
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);

		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionary_id);
		model.addAttribute("nonCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaborator> collaborators = dictionaryManager.showCollaboratingUsers(dictionary_id);
		model.addAttribute("collaboratingUsers", collaborators);

		return "auth/dictionaries/showCollaborators";
	
	} */
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/addCollaborators" , method = RequestMethod.POST)
	public ModelAndView addCollaborators( @PathVariable("dictionaryid") String dictionary_id, 
			@Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result,
			Principal principal	)
	{
		System.out.println("----------------in controller");
		ModelAndView model = null;
		String errmsg = "";
		String collaboratorUser = collaborator.getUserObj().getUserName();
		List<ICollaboratorRole> roles = collaborator.getCollaboratorRoles();
		model = new ModelAndView("auth/dictionaries/showCollaborators");

		if(result.hasErrors())
		{
			model.getModelMap().put("collaborator", collaborator);
		}
		
		else
		{
		String username = principal.getName();
			
		errmsg = dictionaryManager.addCollaborators(collaborator, dictionary_id,collaboratorUser,username);		
		
		model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());

		}
				
		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionary_id);
		model.getModelMap().put("nonCollaboratingUsers", nonCollaboratingUsers);
		
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
		
		
		List<ICollaborator> collaborators = dictionaryManager.showCollaboratingUsers(dictionary_id);
		model.getModelMap().put("collaboratingUsers", collaborators);

		return model;
	
	} 
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/deleteCollaborators", method = RequestMethod.POST)
	public String deleteCollaborators(@PathVariable("dictionaryid") String dictionaryid, HttpServletRequest req, ModelMap model)
	{
		String[] collaborators = req.getParameterValues("selected");
		
		String errmsg = null;
		
		for(int i=0; i<collaborators.length; i++)
		{
			errmsg = dictionaryManager.deleteCollaborator(dictionaryid, collaborators[i]);
		}
		
		if(errmsg.equals(""))
		{
			return "redirect:/auth/dictionaries/"+dictionaryid+"/showCollaborators";
		}
		return "redirect:/auth/dictionaries/{dictionaryid}";
			
	}
	
}
