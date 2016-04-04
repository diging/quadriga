
package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller is to map project/about page for public website. A formatted
 * string is sent to jsp which is displayed in the correct HTML format
 * 
 * @author Rajat Aggarwal
 *
 */

@Controller
public class WebsiteAboutController {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@Autowired
	private IAboutTextManager aboutTextManager;

	@RequestMapping(value = "sites/{ProjectUnixName}/about", method = RequestMethod.GET)
	public String showAbout(@PathVariable("ProjectUnixName") String unixName, Model model, Principal principal)
			throws QuadrigaStorageException {
		IProject project = projectManager.getProjectDetailsByUnixName(unixName);
		String projectId = project.getProjectId();
		model.addAttribute("project", project);
		model.addAttribute("aboutText", aboutTextManager.getDTOByProjectId(projectId));
		return "sites/public/PublicWebsiteAbout";
	}

}
