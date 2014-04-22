package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferCCOwnerController 
{

	@Autowired
	private UserValidator validator;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	ICollaboratorRoleManager roleManager;
	
	@Autowired
	IDBConnectionModifyCCManager conceptCollectionModifyManager;
	
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder){
		validateBinder.setValidator(validator);
	}
	
	/**
	 * This method retrieves the collaborators associated with the concept collection
	 * for change of ownership to any one of the collaborator.
	 * @param collectionId
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {} )})
	@RequestMapping(value = "auth/conceptcollections/transferconceptcollectionowner/{collectionid}", method = RequestMethod.GET)
	public ModelAndView transferConceptCollectionOwner(@PathVariable("collectionid") String collectionId,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String userName;
		ModelAndView model;
		IConceptCollection conceptCollection;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		model = new ModelAndView("auth/conceptcollections/transferconceptcollectionowner");
		userName = principal.getName();
		
		//retrieve the concept collection details
		conceptCollection = collectionFactory.createConceptCollectionObject();
		conceptCollection.setConceptCollectionId(collectionId);
		conceptCollectionManager.getCollectionDetails(conceptCollection, userName);
		
		//create a model
		model.getModelMap().put("user", userFactory.createUserObject());
		model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
		model.getModelMap().put("collectionowner", conceptCollection.getOwner().getName());
		model.getModelMap().put("collectionid", collectionId);
		
		//fetch the collaborators
		collaboratingUser = conceptCollectionManager.showCollaboratingUsers(collectionId);
		
		for(ICollaborator collabuser : collaboratingUser)
		{
			userList.add(collabuser.getUserObj());
		}
		
		model.getModelMap().put("collaboratinguser", userList);
		
		//create model attribute
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This method transfer the ownership of given concept collection to the selected
	 * collaborator and assigns the old owner as collaborator to the concept collection.
	 * @param collaboratorUser
	 * @param result
	 * @param collectionId
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {} )})
	@RequestMapping(value = "auth/conceptcollections/transferconceptcollectionowner/{collectionid}", method = RequestMethod.POST)
	public ModelAndView transferConceptCollectionOwner(@Validated @ModelAttribute("user")User collaboratorUser,BindingResult result,
			@PathVariable("collectionid") String collectionId,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName;
		String newOwner;
		String collaboratorRole;
		IConceptCollection conceptCollection;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		//create a view
		model = new ModelAndView("auth/conceptcollections/transferconceptcollectionowner");
		userName = principal.getName();
		
		//retrieve the concept collection details
		conceptCollection = collectionFactory.createConceptCollectionObject();
		conceptCollection.setConceptCollectionId(collectionId);
		conceptCollectionManager.getCollectionDetails(conceptCollection,userName);
		
		model.getModelMap().put("collectionid", collectionId);
		
		if(result.hasErrors())
		{
			model.getModelMap().put("user", collaboratorUser);
			
			//create a model
			model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
			model.getModelMap().put("collectionowner", conceptCollection.getOwner().getName());
			
			//fetch the collaborators
			collaboratingUser = conceptCollectionManager.showCollaboratingUsers(collectionId);
			
			for(ICollaborator collabuser : collaboratingUser)
			{
				userList.add(collabuser.getUserObj());
			}
			
			model.getModelMap().put("collaboratinguser", userList);
			
			model.getModelMap().put("success", 0);
		}
		else
		{
        	//fetch the new owner
        	newOwner = collaboratorUser.getUserName();
        	
        	collaboratorRole = roleManager.getCCCollaboratorRoleById(RoleNames.ROLE_CC_COLLABORATOR_ADMIN).getRoleDBid();       	
			
        	//call the method to transfer the ownership
        	conceptCollectionModifyManager.transferCollectionOwnerRequest(collectionId,userName , newOwner, collaboratorRole);
			
			model.getModelMap().put("success", 1);
			model.getModelMap().put("user", userFactory.createUserObject());
		}
	return model;
	}
	
}
