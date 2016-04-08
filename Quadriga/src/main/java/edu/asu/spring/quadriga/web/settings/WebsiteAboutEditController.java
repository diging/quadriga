package edu.asu.spring.quadriga.web.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

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

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/projects/{projectId}/settings/editabout", method = RequestMethod.GET)
	public String editAbout(@PathVariable("projectId") String projectId, Model model, Principal principal)
			throws QuadrigaStorageException {
		IProject project = projectManager.getProjectDetails(projectId);
		model.addAttribute("aboutText", aboutTextManager.getAboutTextByProjectId(projectId));
		model.addAttribute("project", project);
		return "auth/editabout";
	}

	/**
	 * . Any change made in the about project page is updated into the database
	 * here and a "You successfully edited the about text" message is displayed.
	 * 
	 * @author Rajat Aggarwal
	 *
	 */

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/projects/{projectId}/settings/saveabout", method = RequestMethod.POST)
	public String saveAbout(@PathVariable("projectId") String projectId,
			@ModelAttribute("AboutTextBackingBean") AboutTextBackingBean formBean, Model model, Principal principal)
			throws QuadrigaStorageException {
		aboutTextManager.saveAbout(projectId, formBean.getTitle(), formBean.getDescription());
		IProject project = projectManager.getProjectDetails(projectId);
		model.addAttribute("show_success_alert", true);
		model.addAttribute("success_alert_msg", "You successfully edited the about text");
		model.addAttribute("aboutText", aboutTextManager.getAboutTextByProjectId(projectId));
		model.addAttribute("project", project);
		return "auth/editabout";
	}

}