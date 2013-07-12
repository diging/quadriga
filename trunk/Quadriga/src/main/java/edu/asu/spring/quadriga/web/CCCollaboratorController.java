
package edu.asu.spring.quadriga.web;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * javadoc missing
 * @author who wrote this?
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
					// TODO Auto-generated catch block
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
	 
	 @RequestMapping(value="auth/conceptcollections/{collection_id}/displayCollaborators", method=RequestMethod.GET)
		public String displayCollaborator(@PathVariable("collection_id") String collectionid, ModelMap model, Principal principal) throws QuadrigaAccessException
		{
			List<ICollaboratorRole> collaboratorRoles = null;
			List<IUser> noncollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
			
			model.addAttribute("noncollaboratorList", noncollaboratorList);
			concept = collectionFactory.createConceptCollectionObject();
			concept.setId(collectionid);
			
			try {
				conceptControllerManager.getCollectionDetails(concept,principal.getName());
			} catch (QuadrigaStorageException e) {
				// throw that so that it can be handled
				e.printStackTrace();
			}
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
			
			
			
			//model.addAttribute("collaboratingUsers", collaboratingUsers);
			
			return "auth/conceptcollection/showCollaborators";
			
		}
		
		@RequestMapping(value="auth/conceptcollections/{collection_id}/addcollaborators", method=RequestMethod.POST)
		public String addCollaborators(@PathVariable("collection_id") String collectionid, ModelMap model,
				@ModelAttribute ICollaborator collaborator, Principal principal)
		{
			String username = principal.getName();
		
			String errmsg = conceptControllerManager.addCollaborators(collaborator, collectionid, username);
		

				if(errmsg.equals("no errors"))
				{
					//System.out.println("------------------WB1");
					return "redirect:/auth/conceptcollections/{collection_id}";
				}
				
				//System.out.println("------------------WB2");

			return "redirect:/auth/conceptcollection/"+collectionid+"/displayCollaborators";
		}
		
		
		
		@ModelAttribute
		public ICollaborator getCollaborator() {
			ICollaborator collaborator = collaboratorFactory.createCollaborator();
			collaborator.setUserObj(userFactory.createUserObject());
			return collaborator;
		} 

}
