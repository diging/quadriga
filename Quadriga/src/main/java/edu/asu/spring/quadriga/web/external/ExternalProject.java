package edu.asu.spring.quadriga.web.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;

@Controller
public class ExternalProject {
	
	@Autowired
	private ProjectManager projectmanager;
	
	@RequestMapping(value="sites/{ProjectUnixName}", method=RequestMethod.GET)
	public String showProject(@PathVariable("ProjectUnixName") String unixName,Model model) {
		System.out.println("in controller project unix name");
		
		IProject project = projectmanager.getProjectDetails();
		
		model.addAttribute("project", project);
		return "website";
		
	}

	
}
