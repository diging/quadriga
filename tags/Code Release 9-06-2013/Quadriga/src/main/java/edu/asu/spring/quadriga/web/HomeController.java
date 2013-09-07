package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.factories.IServiceUriFactory;
import edu.asu.spring.quadriga.domain.implementation.Profile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	IServiceUriFactory serviceUriFactory;

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
	public String loginProfile(Model model, Principal principal)
	{
		System.out.println("------------in homecontroller");
		return "auth/home/profile";
	}
	
	@RequestMapping(value = "auth/profile/showadduri", method = RequestMethod.GET)
	public String showAddUri(Model model, Principal principal)
	{
		IProfile serviceUri = serviceUriFactory.createServiceUriObject();
		model.addAttribute("serviceUri", serviceUri);
		
		
		return "auth/home/profile/adduri";
	}
	
	@RequestMapping(value = "auth/profile/adduri", method = RequestMethod.POST)
	public String addUri(@ModelAttribute("serviceUri") Profile serviceUri, Model model, Principal principal)
	{
		String str = serviceUri.getServiceName();
		System.out.println("-----------str "+str);
		
		
		
		return "auth/home/profile/adduri";
	}

}
