package edu.asu.spring.quadriga.web.settings;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IPublicPageManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class PublicPageSettingsController {

	@Autowired
	private IPublicPageFactory publicPageFactory;

	@Autowired
	private IPublicPageManager publicPageContentManager;
	
	@Autowired
	private IRetrieveProjectManager projectManager;

	/**
	 * This method is called during the load of Public page settings form
	 *
	 * @return model - model object
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
			RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/projects/{projectid}/settings", method = RequestMethod.GET)
	public ModelAndView publicPageSettingsForm(@PathVariable("projectid") String projectid)
			throws QuadrigaStorageException, QuadrigaAccessException {
		List<IPublicPage> publicPageList = publicPageContentManager.retrievePublicPageContent(projectid);
		ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
		model.getModelMap().put("publicpagelist", publicPageList);
		model.getModel().put("publicpage", publicPageFactory.createPublicPageObject());
		model.getModelMap().put("publicpageprojectid", projectid);
		model.getModel().put("project", projectManager.getProjectDetails(projectid));

		for (IPublicPage publicPage : publicPageList) {
			model.getModel().put("publicpageObject" + publicPageList.indexOf(publicPage), publicPage);
		}

		return model;
	}

	/**
	 * This method is used update the database with the information provided in
	 * the Public settings page
	 *
	 * @return json
	 * @throws JSONException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "auth/workbench/{projectid}/addpublicpagesuccess")
	public @ResponseBody ResponseEntity<String> addpublicpagesuccess(@RequestParam("data") JSONObject data,
			@PathVariable("projectid") String projectid)
			throws QuadrigaStorageException, QuadrigaAccessException, JSONException {

		PublicPage publicpageentry = new PublicPage();
		publicpageentry.setTitle(data.getString("title"));
		publicpageentry.setDescription(data.getString("desc"));
		publicpageentry.setOrder(data.getInt("order"));
		publicpageentry.setProjectId(projectid);
		publicpageentry.setPublicPageId(data.getString("publicpageid"));
		publicPageContentManager.saveOrUpdatePublicPage(publicpageentry);
		return new ResponseEntity<String>("Successfully updated", HttpStatus.OK);
	}
}