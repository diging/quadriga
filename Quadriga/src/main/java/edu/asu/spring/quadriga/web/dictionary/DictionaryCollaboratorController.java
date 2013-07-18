package edu.asu.spring.quadriga.web.dictionary;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
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
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class DictionaryCollaboratorController {
	
	@Autowired
	IDictionaryManager dictionaryManager;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	ICollaboratorRoleManager collaboratorRoleManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
	    @Override
	    public void setAsText(String text) {

	        IUser user;
			try {
				user = userManager.getUserDetails(text);
				setValue(user);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	    });
	    
	    binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

			   String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getDictCollaboratorRoleById(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
	    }); 
	}
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/showCollaborators" , method = RequestMethod.GET)
	public String displayCollaborators(@PathVariable("dictionaryid") String dictionary_id, ModelMap model){
		
		model.addAttribute("dictionaryid", dictionary_id);
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator); 

		List<IUser> nonCollaboratingUsers = dictionaryManager.showNonCollaboratingUsers(dictionary_id);
		model.addAttribute("nonCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getDictCollaboratorRoles();
		
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals("ADMIN"))
			{
				iterator.remove();
			}
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		List<IUser> collaboratingUsers = dictionaryManager.showCollaboratingUsers(dictionary_id);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		return "auth/dictionaries/showCollaborators";
	}


	@ModelAttribute
	public ICollaborator getCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	} 
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/addCollaborators" , method = RequestMethod.POST)
	public String addCollaborators(@PathVariable("dictionaryid") String dictionary_id, 
			@ModelAttribute ICollaborator collaborator, ModelMap model, Principal principal)
	{
		String username = principal.getName();
		String errmsg = null;
		errmsg = dictionaryManager.addCollaborators(collaborator, dictionary_id,username);
		
		if(errmsg=="")
		{
			return "redirect:/auth/dictionaries/{dictionaryid}";
		}
		
		return "redirect:/auth/dictionaries"+dictionary_id+"/showcollaborators";
		
	}
	
	@RequestMapping(value="auth/dictionaries/{dictionaryid}/deleteCollaborators", method = RequestMethod.POST)
	public String deleteCollaborators(@PathVariable("dictionaryid") String dictionaryid, HttpServletRequest req, ModelMap model)
	{
		String[] collaborators = req.getParameterValues("selected");
		
		String errmsg = null;
		System.out.println("--------------dc1");
		
		for(int i=0; i<collaborators.length; i++)
		{
			System.out.println("--------------collab:"+collaborators[i]);
			errmsg = dictionaryManager.deleteCollaborator(dictionaryid, collaborators[i]);
		}
		System.out.println("--------------dc2");

		
		if(errmsg.equals(""))
		{
			return "redirect:/auth/dictionaries/"+dictionaryid+"/showCollaborators";
		}
		return "redirect:/auth/dictionaries/{dictionaryid}";
			
	}
	
}
