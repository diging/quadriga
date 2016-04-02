package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller manages public website's about page of a project. Information
 * of title and description is editable.
 *
 * @author Rajat Aggarwal
 *
 */

@Controller
public class WebsiteAboutEditController {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@Autowired
	private IAboutTextManager aboutTextManager;

	@PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN') OR hasRole('ROLE_QUADRIGA_USER_STANDARD')")
	@RequestMapping(value = "auth/workbench/projects/{projectId}/settings/editabout", method = RequestMethod.GET)
	public String showAbout(@PathVariable("projectId") String projectId, Model model, Principal principal)
			throws QuadrigaStorageException {
		IProject project = projectManager.getProjectDetails(projectId);

		String title = null;
		String description = null;

		title = aboutTextManager.getAboutTitle(projectId);
		description = aboutTextManager.getAboutDescription(projectId);
		System.out.println(description);
		model.addAttribute("project", project);
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		return "auth/editabout";
	}

	/**
	 * . Any change made in the about project page is updated into the database
	 * here and a "Successfully saved" message is displayed.
	 * 
	 * @author Rajat Aggarwal
	 *
	 */

	@PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN') OR hasRole('ROLE_QUADRIGA_USER_STANDARD')")
	@RequestMapping(value = "auth/workbench/projects/{projectId}/settings/saveabout", method = RequestMethod.POST)
	public String saveAbout(@PathVariable("projectId") String projectId,
			@ModelAttribute("AboutTextBackingBean") AboutTextBackingBean formBean, Principal principal)
			throws QuadrigaStorageException {
		aboutTextManager.saveAbout(projectId, formBean.getTitle(), formBean.getDescription());
		return "auth/saveabout";
	}

}
