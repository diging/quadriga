package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class EditProjectController {

	@Autowired
	IModifyProjectManager projectManager;

	@Autowired
	IRetrieveProjectManager retrieveProjectManager;

	@Resource(name = "projectconstants")
	private Properties messages;

	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectController.class);

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/editProjectPageURL/{projectid}", method = RequestMethod.GET)
	public ModelAndView editProjectPageURL(@PathVariable("projectid") String projectid, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {
		ModelAndView model;
		IProject project;
		logger.info("Updating project details");
		model = new ModelAndView("auth/workbench/editProjectPageURL");
		project = retrieveProjectManager.getProjectDetails(projectid);
		model.getModelMap().put("project", project);
		model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
		model.getModelMap().put("success", 0);
		return model;
	}

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/editProjectPageURL/{projectid}", method = RequestMethod.POST)
	public ModelAndView editProjectPageURL(@Validated @ModelAttribute("project") Project project, BindingResult result,
			@PathVariable("projectid") String projectid, Principal principal)
					throws QuadrigaStorageException, QuadrigaAccessException {
		ModelAndView model;
		String userName = principal.getName();

		logger.info("Update project details");
		model = new ModelAndView("auth/workbench/editProjectPageURL");
		if (result.hasErrors()) {
			logger.error("Update project details error:", result);
			model.getModelMap().put("project", project);
			model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
			model.getModelMap().put("success", 0);
		} else {
			projectManager.updateProjectURL(project.getProjectId(), project.getProjectAccess().name(),
					project.getUnixName(), userName);
			model.getModelMap().put("success", 1);
		}
		return model;
	}

}
