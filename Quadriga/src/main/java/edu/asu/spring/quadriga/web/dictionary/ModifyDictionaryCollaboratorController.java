package edu.asu.spring.quadriga.web.dictionary;

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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyDictionaryCollaboratorController 
{
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private IQuadrigaRoleManager collaboratorRoleManager;
	
	@Autowired
	IDictionaryCollaboratorManager dictionaryManager;
	
	@Autowired
	IRetrieveDictionaryManager retrieveDictionaryManager;
	
	@Autowired
	private CollaboratorFormValidator validator;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
				String[] roleIds = text.split(",");
				List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
				for (String roleId : roleIds) {
				    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES, roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
		
	}
	
	/**
	 * This method retrieves the collaborators associated with a dictionary for updating
	 * the selected collaborator roles
	 * @param dictionaryid
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN} )})
	 @RequestMapping(value="auth/dictionaries/{dictionaryid}/updatecollaborators", method=RequestMethod.GET)
	 public ModelAndView updateCollaboratorForm(@PathVariable("dictionaryid") String dictionaryid) throws QuadrigaStorageException,QuadrigaAccessException
	 {
		 ModelAndView model;
		 ModifyCollaboratorForm collaboratorForm;
		 List<ModifyCollaborator> modifyCollaborator;
		 List<IQuadrigaRole> collaboratorRoles;
		 
		 model = new ModelAndView("auth/dictionaries/updatecollaborators");
		 
		 //fetch the dictionary details
		 IDictionary dictionary = retrieveDictionaryManager.getDictionaryDetails(dictionaryid);
		 
		 //create a model for collaborators
		 collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
		 
		 modifyCollaborator = collaboratorFormManager.modifyDictCollaboratorManager(dictionaryid);
		 
		 collaboratorForm.setCollaborators(modifyCollaborator);
		 
		 //fetch the concept collection collaborator roles
		 collaboratorRoles = collaboratorRoleManager.getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);
			
	     //add the collaborator roles to the model
			model.getModelMap().put("dictcollabroles", collaboratorRoles);
			model.getModelMap().put("collaboratorform", collaboratorForm);
			model.getModelMap().put("dictionaryid", dictionaryid);
			model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
			model.getModelMap().put("dictionarydesc", dictionary.getDescription());
			model.getModelMap().put("success", 0);
		 return model;
	 }

	/**
	 * This method updates the roles of selected collaborator associated 
	 * with the given dictionary
	 * @param collaboratorForm
	 * @param result
	 * @param dictionaryid
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	 @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 3, userRole = {RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN} )})
	 @RequestMapping(value="auth/dictionaries/{dictionaryid}/updatecollaborators", method=RequestMethod.POST)
	 public ModelAndView updateCollaboratorForm(@Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
			 BindingResult result,@PathVariable("dictionaryid") String dictionaryid,Principal principal) throws QuadrigaStorageException
	 {
			ModelAndView model;
			List<ModifyCollaborator> dictCollaborators;
			String userName;
			String collabUser;
			List<IQuadrigaRole> values;
			StringBuilder collabRoles;
			
			userName = principal.getName();
			
			//create model view
			model = new ModelAndView("auth/dictionaries/updatecollaborators");
			
			if(result.hasErrors())
			{
				//fetch the dictionary details
				IDictionary dictionary = retrieveDictionaryManager.getDictionaryDetails(dictionaryid);
				
				//add a variable to display the entire page
				model.getModelMap().put("success", 0);

				dictCollaborators =  collaboratorFormManager.modifyDictCollaboratorManager(dictionaryid);
				collaboratorForm.setCollaborators(dictCollaborators);
				//add the model map
				model.getModelMap().put("collaboratorform", collaboratorForm);
				model.getModelMap().put("dictionaryid", dictionaryid);
				model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
				model.getModelMap().put("dictionarydesc", dictionary.getDescription());
				
				//retrieve the collaborator roles and assign it to a map
				//fetch the roles that can be associated to the workspace collaborator
				List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager.getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);
				model.getModelMap().put("dictcollabroles", collaboratorRoles);
			}
			else
			{
				dictCollaborators = collaboratorForm.getCollaborators();
				
				//fetch the user and his collaborator roles
				for(ModifyCollaborator collab : dictCollaborators)
				{
					collabRoles = new StringBuilder();
					collabUser = collab.getUserName();
					values = collab.getCollaboratorRoles();
					
					//fetch the role names for the roles and form a string
					for(IQuadrigaRole role : values)
					{
						collabRoles.append(",");
						collabRoles.append(role.getDBid());
					}
					
					dictionaryManager.updateCollaborators(dictionaryid, collabUser, collabRoles.toString().substring(1), userName);
					
					model.getModelMap().put("success", 1);
				}
			}
			
			return model;
		 
	 }
}
