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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyCCCollaboratorController
{
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private IQuadrigaRoleManager collaboratorRoleManager;
	
	@Autowired
	private CollaboratorFormValidator validator;
	
	@Autowired
	private IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	private ICCCollaboratorManager collaboratorManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
				String[] roleIds = text.split(",");
				List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
				for (String roleId : roleIds) {
				    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES, roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
		
	}
	
	/**
	 * This method retrieves the collaborators associated with given concept collection for updation the
	 * collaborator roles.
	 * @param collectionid
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN} )})
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/updatecollaborators", method=RequestMethod.GET)
	 public ModelAndView updateCollaboratorForm(@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	 {
		 ModelAndView model;
		 ModifyCollaboratorForm collaboratorForm;
		 List<ModifyCollaborator> modifyCollaborator;
		 List<IQuadrigaRole> collaboratorRoles;
		 
		 model = new ModelAndView("auth/conceptcollection/updatecollaborators");
		 
		 String userName = principal.getName();

		 //fetch the concept collection details
		 IConceptCollection conceptCollection = collectionFactory.createConceptCollectionObject();
		 conceptCollection.setConceptCollectionId(collectionid);
		 conceptControllerManager.getCollectionDetails(conceptCollection, userName);
		 
		 
		 //create a model for collaborators
		 collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
		 
		 modifyCollaborator = collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
		 
		 collaboratorForm.setCollaborators(modifyCollaborator);
		 
		 //fetch the concept collection collaborator roles
		 collaboratorRoles = collaboratorRoleManager.getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES) ;
			
	     //add the collaborator roles to the model
			model.getModelMap().put("cccollabroles", collaboratorRoles);
			model.getModelMap().put("collaboratorform", collaboratorForm);
			model.getModelMap().put("collectionid", collectionid);
			model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
			model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
			model.getModelMap().put("success", 0);
		 return model;
	 }
	 
	/**
	 * This method updated the roles of selected collaborator associated with
	 * the given concept collection
	 * @param collaboratorForm
	 * @param result
	 * @param collectionid
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 3, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN} )})
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/updatecollaborators", method=RequestMethod.POST)
	 public ModelAndView updateCollaboratorForm(@Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
			 BindingResult result,@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	 {
			ModelAndView model;
			List<ModifyCollaborator> ccCollaborators;
			String userName;
			String collabUser;
			List<IQuadrigaRole> values;
			StringBuilder collabRoles;
			
			userName = principal.getName();
			
			//create model view
			model = new ModelAndView("auth/conceptcollection/updatecollaborators");
			
			
			if(result.hasErrors())
			{
				//fetch the concept collection details
				IConceptCollection conceptCollection = collectionFactory.createConceptCollectionObject();
				conceptCollection.setConceptCollectionId(collectionid);
				conceptControllerManager.getCollectionDetails(conceptCollection, userName);

				//add a variable to display the entire page
				model.getModelMap().put("success", 0);

				ccCollaborators =  collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
				collaboratorForm.setCollaborators(ccCollaborators);
				//add the model map
				model.getModelMap().put("collaboratorform", collaboratorForm);
				model.getModelMap().put("collectionid", collectionid);
				model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
				model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
				
				//retrieve the collaborator roles and assign it to a map
				//fetch the roles that can be associated to the workspace collaborator
				List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager.getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES);
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
					for(IQuadrigaRole role : values)
					{
						collabRoles.append(",");
						collabRoles.append(role.getDBid());
					}
					
					//adding the logic to retrieve the user name of full name is empty
					if(!collabUser.isEmpty())
					{
						collaboratorManager.updateCollaborators(collectionid, collabUser, collabRoles.toString().substring(1),
                                userName);
					}
					
					
					
					model.getModelMap().put("success", 1);
				}
			}
		 
		 return model;
	 }

}
