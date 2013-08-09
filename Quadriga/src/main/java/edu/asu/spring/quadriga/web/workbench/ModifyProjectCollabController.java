package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorForm;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorForm;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.impl.workbench.ModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;

@Controller
public class ModifyProjectCollabController 
{
	@Autowired
	private IRetrieveProjCollabManager retrieveProjManager;
	
	@Autowired
	private ICollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private ModifyProjCollabManager projectManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

	@RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.GET)
	public ModelAndView updateCollaboratorRequestForm(@PathVariable("projectid") String projectid) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<ICollaborator> projCollaborators;
		ICollaboratorForm collaboratorList;
		
		//create model view
		model = new ModelAndView("auth/workbench/updatecollaborators");
		
		//retrieve the list of Collaborators and their roles.
		projCollaborators = retrieveProjManager.getProjectCollaborators(projectid);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		
		//create a model for collaborators
		collaboratorList = collaboratorFactory.createCollaboratorForm();
		collaboratorList.setCollaborator(projCollaborators);
		
        //add the collaborator roles to the model
		model.getModelMap().put("projcollabroles", collaboratorRoles);
		model.getModelMap().put("collaboratorform", collaboratorList);
		model.getModelMap().put("projectid", projectid);
		model.getModelMap().put("success", 0);
		
		return model;
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.POST)
	public ModelAndView updateCollaboratorRequest(@Validated @ModelAttribute("collaboratorform") CollaboratorForm collaboratorList,
			BindingResult result,@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<ICollaborator> projCollaborators;
		String userName;
		String collabUser;
		List<ICollaboratorRole> values;
		String collabRoles;
		
		userName = principal.getName();
		
		//create model view
		model = new ModelAndView("auth/workbench/updatecollaborators");
		
		if(collaboratorList == null)
		{
			System.out.println("Collaborator List is empty");
		}
		else
		{
			System.out.println("Collaborator List is not empty"+collaboratorList);
			if(collaboratorList.getCollaborator() == null)
			{
				System.out.println("Collaborator get is null");
			}
		}
		
		if(result.hasErrors())
		{
			//add a variable to display the entire page
			model.getModelMap().put("success", 0);
			
			//add the model map
			model.getModelMap().put("collaboratorform", collaboratorList);
			model.getModelMap().put("projectid", projectid);
			
			//retrieve the collaborator roles and assign it to a map
			//fetch the roles that can be associated to the workspace collaborator
			List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
			model.getModelMap().put("projcollabroles", collaboratorRoles);
		}
		else
		{
			projCollaborators = collaboratorList.getCollaborator();
			
			//fetch the user and his collaborator roles
			for(ICollaborator collab : projCollaborators)
			{
				collabUser = collab.getUserObj().getUserName();
				values = collab.getCollaboratorRoles();
				
				//fetch the role names for the roles and form a string
				collabRoles = StringUtils.join(values, ",");
				
				System.out.println("Roles : "+collabRoles);
				
			     projectManager.updateCollaboratorRequest(projectid, collabUser, collabRoles, userName);
				
				model.getModelMap().put("success", 1);
			}
		}
		return model;
	}

}
