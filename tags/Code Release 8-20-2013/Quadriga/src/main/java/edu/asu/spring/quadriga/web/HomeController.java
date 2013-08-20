package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

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

}
