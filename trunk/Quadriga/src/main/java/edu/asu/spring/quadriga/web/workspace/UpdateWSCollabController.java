package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.backingbean.ModifyCollaborator;
import edu.asu.spring.quadriga.backingbean.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@Controller
public class UpdateWSCollabController 
{
	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private IModifyWSCollabManager wsModifyCollabManager;
	
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/updatecollaborators", method = RequestMethod.GET)
	public ModelAndView updateWorkspaceCollaboratorForm(@PathVariable("workspaceid")String workspaceid) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<ICollaborator> wsCollaborators;
		ModifyCollaboratorForm collaboratorForm;
		ModifyCollaborator collaborator;
		List<ModifyCollaborator> collaboratorList = new ArrayList<ModifyCollaborator>();
		
		//create model view
		model = new ModelAndView("auth/workbench/workspace/updatecollaborators");
		
		//retrieve the list of Collaborators and their roles.
		wsCollaborators = wsCollabManager.getWorkspaceCollaborators(workspaceid);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
		
		//create a model for collaborators
		collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
		
		for(ICollaborator collab : wsCollaborators)
		{
			collaborator = new ModifyCollaborator();
			collaborator.setUserName(collab.getUserObj().getUserName());
			collaborator.setCollaboratorRoles(collab.getCollaboratorRoles());
			collaboratorList.add(collaborator);
		}
		
		collaboratorForm.setCollaborators(collaboratorList);
		
        //add the collaborator roles to the model
		model.getModelMap().put("wscollabroles", collaboratorRoles);
		model.getModelMap().put("collaboratorform", collaboratorForm);
		model.getModelMap().put("workspaceid", workspaceid);
		model.getModelMap().put("success", 0);
		
		return model;
	}
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/updatecollaborators", method = RequestMethod.POST)
	public ModelAndView updateWorkspaceCollaborator(@Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
			BindingResult result,@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<ModifyCollaborator> wsCollaborators;
		String userName;
		String collabUser;
		List<ICollaboratorRole> values;
		String collabRoles;
		
		userName = principal.getName();
		
		//create model view
		model = new ModelAndView("auth/workbench/workspace/updatecollaborators");
		
		if(result.hasErrors())
		{
			//add a variable to display the entire page
			model.getModelMap().put("success", 0);

			//add the model map
			model.getModelMap().put("collaboratorform", collaboratorForm);
			model.getModelMap().put("workspaceid", workspaceid);
			
			//retrieve the collaborator roles and assign it to a map
			//fetch the roles that can be associated to the workspace collaborator
			List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
			model.getModelMap().put("wscollabroles", collaboratorRoles);
		}
		else
		{
			wsCollaborators = collaboratorForm.getCollaborators();
			
			//fetch the user and his collaborator roles
			for(ModifyCollaborator collab : wsCollaborators)
			{
				collabRoles = "";
				collabUser = collab.getUserName();
				values = collab.getCollaboratorRoles();
				
				//fetch the role names for the roles and form a string
				for(ICollaboratorRole role : values)
				{
					collabRoles = collabRoles + ","+role.getRoleDBid();
				}
				
				collabRoles = collabRoles.substring(1);
				
				//call the database to modify the record
				wsModifyCollabManager.updateWorkspaceCollaborator(workspaceid, collabUser, collabRoles, userName);
				
				model.getModelMap().put("success", 1);
			}
		}
		return model;
	}
	

}
