
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

import edu.asu.spring.quadriga.db.sql.workbench.DBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.sevice.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description this class will handle all the collaborators controls in conceptcollection
 * @author rohit pendbhaje
 *
 */
@Controller
public class CCCollaboratorController {
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	CollaboratorValidator collaboratorValidator;
	
	private IConceptCollection concept;
	
	@Autowired
	private IUserManager usermanager;
	
	private static final Logger logger = LoggerFactory.getLogger(CCCollaboratorController.class);

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
	 
	 @RequestMapping(value="auth/conceptcollections/{collectionid}/showAddCollaborators", method=RequestMethod.GET)
		public String displayCollaborator(@PathVariable("collectionid") String collectionid, ModelMap model, Principal principal) throws QuadrigaAccessException, QuadrigaStorageException
		{
			
		 
		    List<IUser> nonCollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
			model.addAttribute("nonCollaboratorList", nonCollaboratorList);
			
			concept = collectionFactory.createConceptCollectionObject();
			concept.setId(collectionid);		
			conceptControllerManager.getCollectionDetails(concept,principal.getName());
			
			model.addAttribute("collectionid", collectionid);
			
			ICollaborator collaborator =  collaboratorFactory.createCollaborator();
			collaborator.setUserObj(userFactory.createUserObject());
			model.addAttribute("collaborator", collaborator); 
			
			List<ICollaboratorRole> collaboratorRoleList = collaboratorRoleManager.getCollectionCollaboratorRoles();
			Iterator<ICollaboratorRole> iterator = collaboratorRoleList.iterator();
			
			while(iterator.hasNext())
				{
					if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
					{
						iterator.remove();
					}
				}
			
			model.addAttribute("possibleCollaboratorRoles", collaboratorRoleList);
			
			List<ICollaborator>collaboratingUsers =  conceptControllerManager.showCollaboratingUsers(collectionid);
			model.addAttribute("collaboratingUsers", collaboratingUsers);
		
			return "auth/conceptcollection/showAddCollaborators";
			
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
			
		   ModelAndView modelAndView = null;
		   modelAndView = new ModelAndView("auth/conceptcollection/showAddCollaborators");
		   
		   if(result.hasErrors()){
			   
			   modelAndView.getModelMap().put("collaborator", collaborator);
		   }
		
		   else{
			   
			String username = principal.getName();
			String errmsg = conceptControllerManager.addCollaborators(collaborator, collectionid, username);
			modelAndView.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());  
		   
		   }
		   
		   List<IUser> nonCollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
		   modelAndView.getModelMap().put("nonCollaboratorList", nonCollaboratorList);
			
			List<ICollaboratorRole> collaboratorRoleList = collaboratorRoleManager.getCollectionCollaboratorRoles();
			Iterator<ICollaboratorRole> iterator = collaboratorRoleList.iterator();
			
			while(iterator.hasNext())
				{
					if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
					{
						iterator.remove();
					}
				}
			
			modelAndView.getModelMap().put("possibleCollaboratorRoles", collaboratorRoleList);
			
			modelAndView.getModelMap().put("collectionid", collectionid);
			
			List<ICollaborator>collaborators =  conceptControllerManager.showCollaboratingUsers(collectionid);
			for(ICollaborator collaborator2:collaborators)
			{
				System.out.println("--------collaborator "+collaborator2.getUserObj().getUserName());
			}
			modelAndView.getModelMap().put("collaborators", collaborators);
			
		    return modelAndView;
	}
			
	
	

}
