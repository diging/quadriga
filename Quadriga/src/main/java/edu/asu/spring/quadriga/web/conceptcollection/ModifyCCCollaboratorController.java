package edu.asu.spring.quadriga.web.conceptcollection;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.sevice.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.sevice.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyCCCollaboratorController
{
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private CollaboratorFormValidator validator;
	
	@Autowired
	private IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	private ICCCollaboratorManager collaboratorManager;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
				String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getCCCollaboratorRoleById(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
		
	}
	
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/updatecollaborators", method=RequestMethod.GET)
	 public ModelAndView updateCollaboratorForm(@PathVariable("collectionid") String collectionid) throws QuadrigaStorageException
	 {
		 ModelAndView model;
		 ModifyCollaboratorForm collaboratorForm;
		 List<ModifyCollaborator> modifyCollaborator;
		 List<ICollaboratorRole> collaboratorRoles;
		 
		 model = new ModelAndView("auth/conceptcollection/updatecollaborators");
		 
		 //create a model for collaborators
		 collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
		 
		 modifyCollaborator = collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
		 
		 collaboratorForm.setCollaborators(modifyCollaborator);
		 
		 //fetch the concept collection collaborator roles
		 collaboratorRoles = collaboratorRoleManager.getCollectionCollaboratorRoles();
			
	     //add the collaborator roles to the model
			model.getModelMap().put("cccollabroles", collaboratorRoles);
			model.getModelMap().put("collaboratorform", collaboratorForm);
			model.getModelMap().put("collectionid", collectionid);
			model.getModelMap().put("success", 0);
		 return model;
	 }
	 
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/updatecollaborators", method=RequestMethod.POST)
	 public ModelAndView updateCollaboratorForm(@Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
			 BindingResult result,@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaStorageException
	 {
			ModelAndView model;
			List<ModifyCollaborator> ccCollaborators;
			String userName;
			String collabUser;
			List<ICollaboratorRole> values;
			StringBuilder collabRoles;
			
			userName = principal.getName();
			
			//create model view
			model = new ModelAndView("auth/conceptcollection/updatecollaborators");
			
			
			if(result.hasErrors())
			{
				//add a variable to display the entire page
				model.getModelMap().put("success", 0);

				ccCollaborators =  collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
				collaboratorForm.setCollaborators(ccCollaborators);
				//add the model map
				model.getModelMap().put("collaboratorform", collaboratorForm);
				model.getModelMap().put("collectionid", collectionid);
				
				//retrieve the collaborator roles and assign it to a map
				//fetch the roles that can be associated to the workspace collaborator
				List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getCollectionCollaboratorRoles();
				model.getModelMap().put("cccollabroles", collaboratorRoles);
			}
			else
			{
				ccCollaborators = collaboratorForm.getCollaborators();
				
				//fetch the user and his collaborator roles
				for(ModifyCollaborator collab : ccCollaborators)
				{
					collabRoles = new StringBuilder();
					collabUser = collab.getUserName();
					values = collab.getCollaboratorRoles();
					
					//fetch the role names for the roles and form a string
					for(ICollaboratorRole role : values)
					{
						collabRoles.append(",");
						collabRoles.append(role.getRoleDBid());
					}
					
					collaboratorManager.updateCollaboratorRequest(collectionid, collabUser, collabRoles.toString().substring(1),
							                                                 userName);
					
					model.getModelMap().put("success", 1);
				}
			}
		 
		 return model;
	 }

}
