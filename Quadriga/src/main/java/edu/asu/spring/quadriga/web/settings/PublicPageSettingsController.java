package edu.asu.spring.quadriga.web.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.asu.spring.quadriga.service.publicwebsite.IPublicPageBlockLinkTargets;
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
			RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
	@RequestMapping(value = "auth/workbench/projects/{projectid}/settings", method = RequestMethod.GET)
	public ModelAndView publicPageSettingsForm(@PathVariable("projectid") String projectid)
			throws QuadrigaStorageException, QuadrigaAccessException {
		List<IPublicPage> publicPageList = publicPageContentManager.retrievePublicPageContent(projectid);
		ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
		model.getModelMap().put("publicpagelist", publicPageList);
		model.getModel().put("publicpage", publicPageFactory.createPublicPageObject());
		model.getModelMap().put("publicpageprojectid", projectid);
		model.getModel().put("project", projectManager.getProjectDetails(projectid));
		
		Collections.sort(publicPageList, new Comparator<IPublicPage>() {

            @Override
            public int compare(IPublicPage o1, IPublicPage o2) {
               return o1.getOrder() - o2.getOrder();
            }
        });

		for (IPublicPage publicPage : publicPageList) {
			model.getModel().put("publicpageObject" + publicPageList.indexOf(publicPage), publicPage);
		}
		
		Map<String, String> linkTypes = getLinkTargetMap();
		
		model.getModel().put("linkTypes", linkTypes);

		return model;
	}

    private Map<String, String> getLinkTargetMap() {
        Map<String, String> linkTypes = new HashMap<String, String>();
		linkTypes.put(IPublicPageBlockLinkTargets.ABOUT, "About Text");
		linkTypes.put(IPublicPageBlockLinkTargets.BLOG, "Project Blog");
		linkTypes.put(IPublicPageBlockLinkTargets.BROWSE, "Browse Networks");
		linkTypes.put(IPublicPageBlockLinkTargets.EXPLORE, "Explore Combined Network");
		linkTypes.put(IPublicPageBlockLinkTargets.SEARCH, "Search Networks");
		linkTypes.put(IPublicPageBlockLinkTargets.STATS, "Project Statistics");
        return linkTypes;
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

	    List<String> validationFailed = new ArrayList<String>();    
	    
		IPublicPage publicpageentry = new PublicPage();
		if (validate("title", validationFailed, data)) {
	        publicpageentry.setTitle(data.getString("title"));
	    }
		
		String description = data.getString("desc");
		if (validate("desc", validationFailed, data)) {
		    publicpageentry.setDescription(description);
		}
		
		int order = data.getInt("order");
		if (validate("order", validationFailed, data)) {
		    publicpageentry.setOrder(order);
		}
		
		publicpageentry.setProjectId(projectid);
		
		String pageId = data.getString("publicpageid");
		if (pageId != null && !pageId.trim().isEmpty()) {
		    publicpageentry.setPublicPageId(pageId);
		}
		
		String linkTo = data.getString("linkTo");
		if (validate("linkTo", validationFailed, data)) {
		    Map<String, String> linkTypes = getLinkTargetMap();
		    if (!linkTypes.keySet().contains(linkTo)) {
		        validationFailed.add("linkTo");
		    } else {
		        publicpageentry.setLinkTo(linkTo);
		    }
		}
		
		String linkText = data.getString("linkText");
		if (validate("linkText", validationFailed, data)) {
		    publicpageentry.setLinkText(linkText);
		}
		
		if (!validationFailed.isEmpty()) {
		    JSONObject fieldList = new JSONObject();
		    fieldList.put("fields", validationFailed);
		    
		    return new ResponseEntity<String>(fieldList.toString(), HttpStatus.BAD_REQUEST);
		}
		
		publicPageContentManager.saveOrUpdatePublicPage(publicpageentry);
		return new ResponseEntity<String>("Successfully updated", HttpStatus.OK);
	}
	
	private boolean validate(String field, List<String> validationList, JSONObject data) throws JSONException {
	    String value = data.getString(field);
        if (value == null || value.trim().isEmpty()) {
            validationList.add(field);
            return false;
        } 
        return true;
	}
}