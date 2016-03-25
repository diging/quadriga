package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * Public website settings page. Lists all the settings that owner of project or
 * admin can make regarding public website of project
 * 
 * @author Rajat Aggarwal
 *
 */

@Controller
public class PublicWebsiteSettings {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN') OR hasRole('ROLE_QUADRIGA_USER_STANDARD')")
	@RequestMapping(value = "auth/workbench/projects/{projectId}/settings")
	public String showSettings(@PathVariable("projectId") String projectId, Model model, Principal principal)
			throws QuadrigaStorageException {

		IProject project = projectManager.getProjectDetails(projectId);
		model.addAttribute(project);
		return "auth/settings";
	}

}
