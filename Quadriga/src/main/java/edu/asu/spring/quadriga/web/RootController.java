package edu.asu.spring.quadriga.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String redirectToLogin() {
		return "redirect:auth/home";
	}
}
