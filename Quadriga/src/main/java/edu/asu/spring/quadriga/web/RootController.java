package edu.asu.spring.quadriga.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class redirects requests to the root of Quadriga to the Quadriga home page.
 * @author Julia Damerow
 *
 */
@Controller
public class RootController {

	/**
	 * Method that answers requests to root.
	 * @return the redirect path to the Quadriga home page.
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String redirectToLogin() {
		return "redirect:auth/home";
	}
}
