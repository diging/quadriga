package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IServiceUriFactory;
import edu.asu.spring.quadriga.domain.implementation.Profile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.validator.ProfileValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	IServiceUriFactory serviceUriFactory;
	
	@Autowired
	IUserProfileManager profileManager;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	ProfileValidator projectValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		
	//	binder.setValidator(projectValidator);
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "auth/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		model.addAttribute("serverTime", formattedDate );

		return "auth/home";
	}
	
	@RequestMapping(value="auth/profile", method = RequestMethod.GET)
	public String loginProfile(Model model, Principal principal) throws QuadrigaStorageException
	{
		IProfile serviceUri = serviceUriFactory.createServiceUriObject();
		model.addAttribute("serviceUri", serviceUri);
		
		
		IUser user = userManager.getUserDetails(principal.getName());
		model.addAttribute("user", user);
		
		List<IProfile> profileList = profileManager.showUserProfile(principal.getName());
		model.addAttribute("profileList", profileList);

		return "auth/home/profile";
	}
	
	@RequestMapping(value = "auth/profile/showadduri", method = RequestMethod.GET)
	public String showAddUri(Model model, Principal principal) throws QuadrigaStorageException
	{
		IProfile serviceUri = serviceUriFactory.createServiceUriObject();
		model.addAttribute("serviceUri", serviceUri);
		
		
		return "auth/home/profile/adduri";
	}
	
	@RequestMapping(value = "auth/profile/adduri", method = RequestMethod.POST)
	public String addUri(@Validated @ModelAttribute("serviceUri") Profile serviceUri, BindingResult result, Model model, Principal principal) throws QuadrigaStorageException
	{
		String service = serviceUri.getServiceName();
		String uri = serviceUri.getUri();
		
		if(result.hasErrors())
		{
			model.addAttribute("serviceUri", serviceUri);
		}
		
		else{
			
			String errmsg = profileManager.addUserProfile(principal.getName(),service,uri);
			model.addAttribute("success",0);
		}
		
		return "auth/home/profile/adduri";
	}

}
