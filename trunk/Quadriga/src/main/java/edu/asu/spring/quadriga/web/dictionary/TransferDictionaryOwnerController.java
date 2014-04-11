package edu.asu.spring.quadriga.web.dictionary;

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
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.IModifyDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferDictionaryOwnerController
{
	@Autowired
	IRetrieveDictionaryManager dictionaryManager;
	
	@Autowired
	IUserFactory   userFactory;
	
	@Autowired
	IDictionaryManager dictionaryCollabManager;
	
	@Autowired
	ICollaboratorRoleManager roleManager;
	
	@Autowired
	IModifyDictionaryManager modifyDictionaryManager;
	
	@Autowired
	private UserValidator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder){
		validateBinder.setValidator(validator);
	}
	
	/**
	 * This method retrieves the collaborators associated for given dictionary to transfer the
	 * ownership to one of the collaborators
	 * @param dictionaryid
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {} )})
	@RequestMapping(value="auth/dictionaries/changedictionaryowner/{dictionaryid}", method = RequestMethod.GET)
	public ModelAndView transferDictionaryOwner(@PathVariable("dictionaryid") String dictionaryid) throws QuadrigaStorageException
	{
		ModelAndView model;
		IDictionary dictionary;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		model = new ModelAndView("auth/dictionaries/changedictionaryowner");
		
		dictionary = dictionaryManager.getDictionaryDetails(dictionaryid);
		
		//create a model
	    model.getModelMap().put("user", userFactory.createUserObject());
		model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
		model.getModelMap().put("dictionaryowner", dictionary.getOwner().getUserName());
		model.getModelMap().put("dictionaryid", dictionaryid);
		
		//fetch the collaborators
		collaboratingUser = dictionaryCollabManager.showCollaboratingUsers(dictionaryid);
		
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
	 * This method transfer the ownership of the dictionary to the selected collaborator
	 * and assign the old owner as collaborator to the dictionary
	 * @param dictionaryid
	 * @param principal
	 * @param collaboratorUser
	 * @param result
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {} )})
	@RequestMapping(value="auth/dictionaries/changedictionaryowner/{dictionaryid}", method = RequestMethod.POST)
    public ModelAndView transferDictionaryOwner(@PathVariable("dictionaryid") String dictionaryid,Principal principal,
			@Validated @ModelAttribute("user")User collaboratorUser,BindingResult result) throws QuadrigaStorageException
	{
		String userName;
		String newOwner;
		String collaboratorRole;
		ModelAndView model;
		IDictionary dictionary; 
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		userName = principal.getName();
		model = new ModelAndView("auth/dictionaries/changedictionaryowner");
		dictionary = dictionaryManager.getDictionaryDetails(dictionaryid);
		model.getModelMap().put("dictionaryid",dictionaryid);
		
		if(result.hasErrors())
		{
			model.getModelMap().put("user", collaboratorUser);
			
			//create a model
			model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
			model.getModelMap().put("dictionaryowner", dictionary.getOwner().getUserName());
			
			//fetch the collaborators
			collaboratingUser = dictionaryCollabManager.showCollaboratingUsers(dictionaryid);
			
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
        	
        	collaboratorRole = roleManager.getDictCollaboratorRoleById(RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN).getRoleDBid();
        	
			//call the method to transfer the ownership
        	modifyDictionaryManager.transferDictionaryOwner(dictionaryid, userName, newOwner, collaboratorRole);
			
			model.getModelMap().put("success", 1);
			model.getModelMap().put("user", userFactory.createUserObject());
		}
		
		return model;
	}
}
