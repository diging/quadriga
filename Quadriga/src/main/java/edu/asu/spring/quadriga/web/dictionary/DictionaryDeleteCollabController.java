package edu.asu.spring.quadriga.web.dictionary;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import edu.asu.spring.quadriga.domain.factories.impl.ModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DictionaryDeleteCollabController {
	

	@Autowired
	ModifyCollaboratorFormManager collaboratorFormManager;
	
	@Autowired
	IDictionaryManager dictionaryManager;
	
	@Autowired
	CollaboratorFormDeleteValidator deleteValidator;
	
	@Autowired
	ICollaboratorFactory  collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	ModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@Autowired
	ICollaboratorRoleManager collaboratorRoleManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
	    
		validateBinder.setValidator(deleteValidator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport(){
			
			@Override
			public void setAsText(String text) {						
			String[] roleIds = text.split(",");
			List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
			for(String role:roleIds){			
				ICollaboratorRole collabrole = collaboratorRoleManager.getProjectCollaboratorRoleById(role.trim());
				collaboratorRoles.add(collabrole);
			}
					
			setValue(collaboratorRoles);
			}
		});
	}
	
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/deleteCollaborators", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("dictionaryid") String dictionaryId, 
	@Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm, BindingResult result,
	ModelMap model) throws QuadrigaStorageException
	{
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("auth/dictionaries/showDeleteCollaborators");
		
		String errmsg ;
		
		if(result.hasErrors())
		{
			List<ModifyCollaborator> collaborator = collaboratorFormManager.modifyDictCollaboratorManager(dictionaryId);
			collaboratorForm.setCollaborators(collaborator);
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);	
		}
		
		else
		{
		
			for(ModifyCollaborator collaborator:collaboratorForm.getCollaborators())
			{
				errmsg = dictionaryManager.deleteCollaborator(dictionaryId, collaborator.getUserName());	
			}
			
			List<ModifyCollaborator> collaborators = collaboratorFormManager.modifyDictCollaboratorManager(dictionaryId);
			collaboratorForm.setCollaborators(collaborators);
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			
		}
		
		
		return modelAndView;
			
	}
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/showDeleteCollaborators" , method = RequestMethod.GET)
	public ModelAndView displayCollaborators(@PathVariable("dictionaryid") String dictionaryId) throws QuadrigaStorageException{
		
		
		ModelAndView modelAndView = new ModelAndView("auth/dictionaries/showDeleteCollaborators");
		modelAndView.getModelMap().put("dictionaryid", dictionaryId);
		
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
		
		ModifyCollaboratorForm collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();

		List<ModifyCollaborator> collaborators2 = collaboratorFormManager.modifyDictCollaboratorManager(dictionaryId);
		collaboratorForm.setCollaborators(collaborators2);
		
		modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
		
		return modelAndView;
	}


}
