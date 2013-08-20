package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;

@Controller
public class TransferProjOwnerController 
{
	@Autowired
	IModifyProjectManager projectManager;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private CollaboratorValidator validator;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(TransferProjOwnerController.class);
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {

				IUser user;
				try {
					user = userManager.getUserDetails(text);
					setValue(user);
				} catch (QuadrigaStorageException e) {
					logger.error("In TransferProjOwnerController class :"+e);
				}
				
			}
		});
		binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

				String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getProjectCollaboratorRoleById(roleId);
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
	}
	
	/**
	 * This method is used to load the project ownership transfer form
	 * @param projectid
	 * @param principal
	 * @return ModelAndView object
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 * @author kiranbatna
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"null"} )})
	@RequestMapping(value = "auth/workbench/transferprojectowner/{projectid}", method = RequestMethod.GET)
	public ModelAndView transferProjectOwnerRequestForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IProject project;
		List<ICollaboratorRole> collaboratorRoles;
		ICollaborator collaborator;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		//create a view
		model = new ModelAndView("auth/workbench/transferprojectowner");
		
		//retrieve the project details
		project = retrieveProjectManager.getProjectDetails(projectid);
		
		//check if the user has access logged in user is project owner
			//adding the collaborator model
			collaborator =  collaboratorFactory.createCollaborator();
			model.getModelMap().put("collaborator", collaborator);
			
			//create a model
			model.getModelMap().put("projectname", project.getName());
			model.getModelMap().put("projectowner", project.getOwner().getUserName());
			model.getModelMap().put("projectid", projectid);
			
			//fetch the collaborators
			collaboratingUser = project.getCollaborators();
			
			for(ICollaborator collabuser : collaboratingUser)
			{
				userList.add(collabuser.getUserObj());
			}
			
			model.getModelMap().put("collaboratinguser", userList);
			
			//get all the project collaborator roles
			collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
			
	        //add the collaborator roles to the model
			model.getModelMap().put("projcollabroles", collaboratorRoles);
			
			//create model attribute
			model.getModelMap().put("success", 0);
		return model;
	}
	
    /**
     * This method submits the transfer request form
     * @param projectid
     * @param principal
     * @param collaborator
     * @param result
     * @return ModelAndView object
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"null"} )})
	@RequestMapping(value = "auth/workbench/transferprojectowner/{projectid}", method = RequestMethod.POST)
	public ModelAndView transferProjectOwnerRequest(@PathVariable("projectid") String projectid,Principal principal,
			@Validated @ModelAttribute("collaborator") Collaborator collaborator,BindingResult result) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName;
		String newOwner;
		IProject project;
		String roleIDList = "";
		List<ICollaboratorRole> collaboratorRoles;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		//create a view
		model = new ModelAndView("auth/workbench/transferprojectowner");
		userName = principal.getName();
		
		//retrieve the project details
		project = retrieveProjectManager.getProjectDetails(projectid);
		
		model.getModelMap().put("projectid", projectid);
		
			if(result.hasErrors())
			{
				model.getModelMap().put("collaborator", collaborator);
				
				//create a model
				model.getModelMap().put("projectname", project.getName());
				model.getModelMap().put("projectowner", project.getOwner().getUserName());
				
				
				//fetch the collaborators
				collaboratingUser = project.getCollaborators();
				
				for(ICollaborator collabuser : collaboratingUser)
				{
					userList.add(collabuser.getUserObj());
				}
				
				model.getModelMap().put("collaboratinguser", userList);
				
				//get all the project collaborator roles
				collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
				
		        //add the collaborator roles to the model
				model.getModelMap().put("projcollabroles", collaboratorRoles);
				
				model.getModelMap().put("success", 0);
			}
			else
			{
	        	//fetch the new owner
	        	newOwner = collaborator.getUserObj().getUserName();
	        	
	        	//if the collaborators are null
	        	if(collaborator.getCollaboratorRoles().equals(null))
	        	{
	        		logger.info("In TransferProjOwnerController class collaborator object is NULL");
	        		throw new QuadrigaStorageException();
	        	}
	        	//fetch the roles to the existing owner
				for(ICollaboratorRole role : collaborator.getCollaboratorRoles())
				{
					roleIDList = roleIDList + "," + role.getRoleDBid();
				}
				
				//removing the first ',' from the list
				roleIDList = roleIDList.substring(1);
				
				//call the method to transfer the ownership
				projectManager.transferProjectOwnerRequest(projectid, userName, newOwner, roleIDList);
				
				model.getModelMap().put("success", 1);
				model.getModelMap().put("successmsg", "Project Ownership transferred successfully.");
				
				model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
			}
		return model;
	}

}
