package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ProjectCollaboratorDeleteController {
	
	@Autowired
	private IModifyProjCollabManager modifyProjectCollabManager;

	@Autowired
	private IRetrieveProjCollabManager retrieveProjCollabManager;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

	@Autowired
	private IQuadrigaRoleManager quadrigaRoleManager;
	
	@Autowired
	private IRetrieveProjectManager retrieveprojectManager;
	
	@Autowired
	private IRetrieveProjCollabManager projectCollabManager;
	
	@Autowired
	private CollaboratorFormValidator validator;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {
		
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
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"ADMIN","PROJECT_ADMIN" } )})
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborator", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("projectid") String projectId,
	@ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm, BindingResult result,
	Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
		
		ModelAndView modelAndView ;
		modelAndView = new ModelAndView("auth/workbench/showDeleteCollaborators");
		System.out.println("----------------in post1");

		List<ModifyCollaborator>collaborators = collaboratorForm.getCollaborators();
		String errmsg = null ;
		
		for(ModifyCollaborator collaborator: collaborators)
		{
			errmsg = modifyProjectCollabManager.deleteCollaboratorRequest(collaborator.getUserName(), projectId);
			System.out.println("----------------in post2");

		}
		
		List<ModifyCollaborator> collaborators2 = collaboratorFormManager.modifyCollaboratorManager(projectId);
		
		for(ModifyCollaborator collaborator:collaborators2)
		{
			System.out.println("------------collab "+collaborator.getUserName());
		}
		
	
	//	modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
		modelAndView.getModelMap().put("success", 0);
		modelAndView.getModelMap().put("projectId", projectId);
		
		return modelAndView;
		
		
		
		/*List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator

		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> rolesIterator = collaboratorRoles.iterator();
		
		while(rolesIterator.hasNext())
		{
			if(rolesIterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				rolesIterator.remove();
			}		
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		return "auth/workbench/showDeleteCollaborators";
		
		//ModelAndView model = null;
		//model = new ModelAndView("auth/workbench/showDeleteCollaborators");
		
		String userName = collaborator.getUserObj().getUserName();
		
		String errmsg=""; 
		
		if(result.hasErrors()){	
			model.getModelMap().put("collaborator", collaborator);	
		}
		
		else{
			
			errmsg = modifyProjectCollabManager.deleteCollaboratorRequest(userName, projectid);
			model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
		}
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				iterator.remove();
			}
		}
		model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
		
		List<ICollaborator> collaborators = retrieveProjCollabManager.getProjectCollaborators(projectid);
		model.getModelMap().put("collaboratingUsers", collaborators);
		
		model.getModelMap().put("projectid", projectid); */
		
		//return model;	
	}
	
	
	
	
	
	/*public String deleteCollaborators(@PathVariable("projectid") String projectid,Model model,HttpServletRequest req) throws QuadrigaStorageException
	{
		String[] collaborators = req.getParameterValues("selected");
		
		String errmsg = null;
		for(int i=0;i<collaborators.length;i++)
		{
		
			errmsg = modifyProjectCollabManager.deleteCollaboratorRequest(collaborators[i], projectid);
			
		} 
		
		if(errmsg.equals("no errors"))
		{
			return "redirect:/auth/workbench/"+projectid+"/showDeleteCollaborators";
		}
		
		return "redirect:/auth/workbench/{projectid}";
		
	}*/
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"ADMIN","PROJECT_ADMIN" } )})
	@RequestMapping(value = "auth/workbench/{projectid}/showDeleteCollaborators", method = RequestMethod.GET)
	public ModelAndView displayDeleteCollaborator(@PathVariable("projectid") String projectId) 
			throws QuadrigaStorageException, QuadrigaAccessException
			{
		
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("auth/workbench/showDeleteCollaborators");
		List<ICollaborator> projCollaborators;
		ModifyCollaboratorForm collaboratorForm;
		ModifyCollaborator collaborator;
		List<ModifyCollaborator> collaboratorList = new ArrayList<ModifyCollaborator>();
		
		
		projCollaborators = projectCollabManager.getProjectCollaborators(projectId);
		
//		for(ICollaborator collaborator2:projCollaborators)
//		System.out.println("----------project collaborators "+ collaborator2.getUserObj().getUserName());
		
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		
		collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();
		
		for(ICollaborator collab : projCollaborators)
		{
			collaborator = new ModifyCollaborator();
			collaborator.setUserName(collab.getUserObj().getUserName());
			collaborator.setCollaboratorRoles(collab.getCollaboratorRoles());
			collaboratorList.add(collaborator);
		}
		
		collaboratorForm.setCollaborators(collaboratorList);
		
		for(ModifyCollaborator collab:collaboratorForm.getCollaborators())
		{
			System.out.println("---------------"+collab.getUserName());
			
		}
		
		System.out.println("Collaborator From "+collaboratorForm);
		
		modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
		modelAndView.getModelMap().put("projectId", projectId);
		modelAndView.getModelMap().put("projCollabRoles", collaboratorRoles);
		modelAndView.getModelMap().put("success", 0);
		
	 	/*	for(ModifyCollaborator collab: col1.getCollaborators()) 
		{
			System.out.println("---------------"+collab.getUserName());
			
		} */
		
	/*	CollaboratorsBackBean collaboratorList = new CollaboratorsBackBean();
		model.addAttribute("collaboratorBackingBean", collaboratorList);
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator);
		
		
		
		model.addAttribute("projectid", projectid);
		// retrieve the collaborators who are not associated with project
		List<IUser> nonCollaboratingUsers = projectCollabManager.getProjectNonCollaborators(projectid);

		for(IUser user : nonCollaboratingUsers)
		{
			//fetch the quadriga roles and eliminate the restricted user
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					nonCollaboratingUsers.remove(user);
				}
			}
		}
		
		model.addAttribute("notCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator

		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> rolesIterator = collaboratorRoles.iterator();
		
		while(rolesIterator.hasNext())
		{
			if(rolesIterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				rolesIterator.remove();
			}		
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);*/
		
		return modelAndView;
	}


}
