package edu.asu.spring.quadriga.web.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyWSCollabController 
{
	
	@Autowired
	IModifyWSCollabManager wsManager;
	
	@Autowired
	IRetrieveWSCollabManager wsCollabManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IUserFactory userFactory;
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addCollaborators", method = RequestMethod.GET)
	public ModelAndView addWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<IUser> nonCollaboratingUser;
		ICollaborator collaborator;
		
		model = new ModelAndView("auth/workbench/workspace/addCollaborators");
		
		//adding the collaborator model
		collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.getModelMap().put("collaborator", collaborator);
		
		//adding the workspace id
		model.getModelMap().put("workspaceid", workspaceid);
		
		
		//fetch the users who are not collaborators to the workspace
		nonCollaboratingUser = wsCollabManager.getWorkspaceNonCollaborators(workspaceid);
		
		//remove the restricted user from the list
		for(IUser user : nonCollaboratingUser)
		{
			//fetch the quadriga roles and eliminate the restricted user
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					nonCollaboratingUser.remove(user);
				}
			}
		}
		
		//add the users list to the model
		model.getModelMap().put("noncollabusers", nonCollaboratingUser);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
		
        //add the collaborator roles to the model
		model.getModelMap().put("wscollabroles", collaboratorRoles);
		
		return model;
	}
	

}
