
package edu.asu.spring.quadriga.web.conceptcollection;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
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

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description this class will handle all the collaborators controls in conceptcollection
 * @author rohit pendbhaje
 *
 */
@Controller
public class AddCCCollaboratorController {
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	private ICCCollaboratorManager CollaboratorManager;
	
	@Autowired
	private IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	CollaboratorValidator collaboratorValidator;
	
	@Autowired
	private IUserManager usermanager;
	
	private static final Logger logger = LoggerFactory.getLogger(AddCCCollaboratorController.class);

	 @InitBinder
	  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,  WebDataBinder validateBinder) throws Exception {
		 
		 validateBinder.setValidator(collaboratorValidator);
		 
		 binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
		    @Override
		    public void setAsText(String text) {

		        IUser user;
				try {
					user = usermanager.getUserDetails(text);
					 setValue(user);
				} catch (QuadrigaStorageException e) {
					logger.error("",e);
				}
		    }
		    });
		    
		    binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {
		    @Override
		    public void setAsText(String text){
		    	String roleIds[] = text.split(",");
		    	List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
		    	for(String roleId: roleIds)
		    	{
		    		ICollaboratorRole role = collaboratorRoleManager.getCCCollaboratorRoleById(roleId.trim());
		    		roles.add(role);
		    	}
		    	setValue(roles);	
		    }
		    });
	  }
	 
	 /**
	  * @description maps non-collaborating users, collaborating users and their roles for current conceptcollection and   
	  * @param collectionid  id of the collection
	  * @param model  
	  * @param principal
	  * @return String having path for showcollaborators jsp page.
	  * @throws QuadrigaAccessException
	  * @throws QuadrigaStorageException
	  */
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/addcollaborators", method=RequestMethod.GET)
	 public ModelAndView addCollaborator(@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaAccessException, QuadrigaStorageException
		{
			ICollaborator collaborator;
			ModelAndView model;
			List<IUser> nonCollaboratorList;
			List<ICollaboratorRole> collaboratorRoleList;
			List<ICollaborator>collaboratingUsers;

			model = new ModelAndView( "auth/conceptcollection/addcollaborators");
			
			//fetch the non collaborators and add it to the model
			nonCollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
			
			//remove the restricted user
			Iterator<IUser> userIterator = nonCollaboratorList.iterator();
			while(userIterator.hasNext())
			{
				//fetch the quadriga roles and eliminate the restricted user
				IUser user = userIterator.next();
				List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
				for(IQuadrigaRole role : userQuadrigaRole)
				{
					if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
					{
						userIterator.remove();
						break;
					}
				}
			}
			model.getModelMap().put("nonCollaboratorList", nonCollaboratorList);
			
			model.getModelMap().put("collectionid", collectionid);
			
			collaborator =  collaboratorFactory.createCollaborator();
			collaborator.setUserObj(userFactory.createUserObject());
			model.getModelMap().put("collaborator", collaborator);
			
			collaboratorRoleList = collaboratorRoleManager.getCollectionCollaboratorRoles();
			Iterator<ICollaboratorRole> collabRoleIterator = collaboratorRoleList.iterator();
			while(collabRoleIterator.hasNext())
			{
				ICollaboratorRole collabRole = collabRoleIterator.next();
				if(collabRole.getRoleid().equals(RoleNames.ROLE_CC_COLLABORATOR_ADMIN))
				{
					collabRoleIterator.remove();
				}
			}
			model.getModelMap().put("collaboratorRoles",collaboratorRoleList);
		    collaboratingUsers =  conceptControllerManager.showCollaboratingUsers(collectionid);
		    model.getModelMap().put("collaboratingUsers", collaboratingUsers);
			return model;
		}
		
		/**
		 * @description this method will add collaborators for current conceptcollection
		 * @param collectionid   id of the collection
		 * @param model
		 * @param collaborator  object returned by jsp to controller
		 * @param principal
		 * @return String having path for showcollaborators jsp page.
		 * @throws QuadrigaStorageException
		 */
	   @RequestMapping(value="auth/conceptcollections/{collection_id}/addcollaborators", method=RequestMethod.POST)
		public ModelAndView addCollaborators(@PathVariable("collection_id") String collectionid, Principal principal,
		@Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result)throws QuadrigaStorageException
		{
		   List<IUser> nonCollaboratorList;
		   List<ICollaboratorRole> collaboratorRoleList;
		   ModelAndView model;
		   model = new ModelAndView("auth/conceptcollection/addcollaborators");
		   
		   model.getModelMap().put("collectionid", collectionid);
		   if(result.hasErrors())
		   {
			   model.getModelMap().put("collaborator", collaborator);
		   }
		   else
		   {
			  String username = principal.getName();
			  CollaboratorManager.addCollaborators(collaborator, collectionid, username);
			  model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());  
		   }
		   
		   nonCollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
			//remove the restricted user
			Iterator<IUser> userIterator = nonCollaboratorList.iterator();
			while(userIterator.hasNext())
			{
				//fetch the quadriga roles and eliminate the restricted user
				IUser user = userIterator.next();
				List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
				for(IQuadrigaRole role : userQuadrigaRole)
				{
					if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
					{
						userIterator.remove();
						break;
					}
				}
			}
		   model.getModelMap().put("nonCollaboratorList", nonCollaboratorList);
			
		   collaboratorRoleList = collaboratorRoleManager.getCollectionCollaboratorRoles();
			Iterator<ICollaboratorRole> collabRoleIterator = collaboratorRoleList.iterator();
			while(collabRoleIterator.hasNext())
			{
				ICollaboratorRole collabRole = collabRoleIterator.next();
				if(collabRole.getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
				{
					collabRoleIterator.remove();
				}
			}
		   model.getModelMap().put("collaboratorRoles",collaboratorRoleList);
			
			List<ICollaborator>collaborators =  conceptControllerManager.showCollaboratingUsers(collectionid);
			model.getModelMap().put("collaboratingUsers", collaborators);
			
		    return model;
	}
}
