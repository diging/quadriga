package edu.asu.spring.quadriga.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WorkbenchController {

	@RequestMapping(value="/workbench", method = RequestMethod.GET)
	public String getProjectsOfUser() {
		
		// FIXME: to be implemented
		return "workbench";
	}
}
