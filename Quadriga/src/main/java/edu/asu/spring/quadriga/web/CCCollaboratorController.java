
package edu.asu.spring.quadriga.web;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IUserManager;

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
	
	private IConceptCollection concept;
	
	@Autowired
	private IUserManager usermanager;

	 @InitBinder
	  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		    binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
		    @Override
		    public void setAsText(String text) {

		        IUser user;
				try {
					user = usermanager.getUserDetails(text);
					 setValue(user);
				} catch (QuadrigaStorageException e) {
					e.printStackTrace();
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
	 
	 @RequestMapping(value="auth/conceptcollections/{collection_id}/displayCollaborators", method=RequestMethod.GET)
		public String displayCollaborator(@PathVariable("collection_id") String collectionid, ModelMap model, Principal principal) throws QuadrigaAccessException, QuadrigaStorageException
		{
			List<IUser> noncollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
			
			model.addAttribute("noncollaboratorList", noncollaboratorList);
			concept = collectionFactory.createConceptCollectionObject();
			concept.setId(collectionid);
					
			conceptControllerManager.getCollectionDetails(concept,principal.getName());
			model.addAttribute("conceptcollection", concept);
			
			ICollaborator collaborator =  collaboratorFactory.createCollaborator();
			collaborator.setUserObj(userFactory.createUserObject());
			model.addAttribute("collaborator", collaborator); 
			
			List<ICollaboratorRole> collaboratorRoleList = collaboratorRoleManager.getCollectionCollaboratorRoles();
			Iterator<ICollaboratorRole> iterator = collaboratorRoleList.iterator();
			
			while(iterator.hasNext())
				{
					if(iterator.next().getRoleid().equals("ADMIN"))
					{
						iterator.remove();
					}
				}
			
			model.addAttribute("possibleCollaboratorRoles", collaboratorRoleList);
			
			List<ICollaborator>collaborators =  conceptControllerManager.showCollaboratingUsers(collectionid);
			model.addAttribute("collaborators", collaborators);
			
			return "auth/conceptcollection/showCollaborators";
			
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
		public String addCollaborators(@PathVariable("collection_id") String collectionid, ModelMap model,
				@ModelAttribute ICollaborator collaborator, Principal principal)throws QuadrigaStorageException
		{
			
			String username = principal.getName();
		
			String errmsg = conceptControllerManager.addCollaborators(collaborator, collectionid, username);
		

				if(errmsg.equals("no errors"))
				{
					return "redirect:/auth/conceptcollections/{collection_id}";
				}
				
			return "redirect:/auth/conceptcollections/"+collectionid+"/displayCollaborators";
		}
			
	   @ModelAttribute
		public ICollaborator getCollaborator() {
			ICollaborator collaborator = collaboratorFactory.createCollaborator();
			collaborator.setUserObj(userFactory.createUserObject());
			return collaborator;
		} 
		
		/**
		 * @description deletes the collaborator from current conceptcollection
		 * @param collectionid   id of the conceptcollection
		 * @param model
		 * @param req	contains string array returned by jsp
		 * @return String having path for showcollaborators jsp page
		 * @throws QuadrigaStorageException
		 */
		@RequestMapping(value="auth/conceptcollections/{collection_id}/deleteCollaborator", method = RequestMethod.POST)
		public String deleteCollaborators(@PathVariable("collection_id") String collectionid,Model model,HttpServletRequest req)throws QuadrigaStorageException
		{
			String[] collaborators = req.getParameterValues("selected");
			
			String errmsg = null;
			
				for(int i=0;i<collaborators.length;i++)
				{
					errmsg = conceptControllerManager.deleteCollaborators(collaborators[i], collectionid);
				}
			
				if(errmsg.equals("no errors"))
				{
					return "redirect:/auth/conceptcollections/"+collectionid+"/displayCollaborators";
				}
			
			
			return "redirect:/auth/conceptcollections/"+collectionid+"/displayCollaborators";
		}

}
