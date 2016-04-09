package edu.asu.spring.quadriga.web.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

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

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/projects/{ProjectUnixName}/settings",method = RequestMethod.GET)
	public String showSettings(Model model,	Principal principal, @PathVariable("ProjectUnixName") String projectId, @InjectProject(unixNameParameter = "ProjectUnixName") IProject project) throws QuadrigaStorageException {
		model.addAttribute(project);
		return "auth/settings";
	}

}
