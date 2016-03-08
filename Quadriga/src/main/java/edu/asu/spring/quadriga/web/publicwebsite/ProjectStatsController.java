package edu.asu.spring.quadriga.web.publicwebsite;

/**
 * This controller has all the mappings required to view the external website of a project, view all the networks in that project
 * and visualize the networks 
 * 
 * @author Sayalee Mehendale
 *
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.publicwebsite.impl.ProjectStats;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@PropertySource(value = "classpath:/user.properties")
@Controller
public class ProjectStatsController {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@Autowired
	private INetworkManager networkmanager;

	@Autowired
	private INetworkTransformationManager transformationManager;

	@Autowired
	private ProjectStats ps;

	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}

	private IProject getProjectDetails(String name)
	        throws QuadrigaStorageException {
		return projectManager.getProjectDetailsByUnixName(name);
	}

	private JSONArray getProjectStatsJson(List<IConceptStats> gtc)
	        throws JSONException {
		JSONArray obj = new JSONArray();

		int cnt = System.getProperty("topCount") == null ? 0 : Integer
		        .parseInt(System.getProperty("topCount"));
		int len = gtc.size() > cnt ? cnt : gtc.size();

		for (int k = 0; k < len; k++) {
			JSONObject jo = new JSONObject();
			IConceptStats c = gtc.get(k);
			jo.put("conceptId", c.getConceptId());
			jo.put("description", c.getDescription());
			jo.put("label", c.getLemma());
			jo.put("frequency", c.getCount());
			obj.put(jo);
		}
		return obj;
	}

	/**
	 * This method gives the visualization of how often concepts appear in the
	 * networks
	 * 
	 * @author Bharath Srikantan & Ajay Modi
	 * @param projectUnixName
	 *            The project unix name
	 * @param model
	 *            Model
	 * @return view
	 * @throws JAXBException
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "sites/{projectUnixName}/statistics", method = RequestMethod.GET)
	public String showProjectStatistics(
	        @PathVariable("projectUnixName") String projectUnixName, Model model)
	        throws JAXBException, QuadrigaStorageException {
		IProject project = getProjectDetails(projectUnixName);

		if (project == null) {
			return "auth/accessissue";
		}

		String projectid = project.getProjectId();
		List<INetwork> Networks = networkmanager
		        .getNetworksInProject(projectid);
		List<IConceptStats> gtc = new ArrayList<IConceptStats>();
		JSONArray jo = new JSONArray();
		StringBuffer errorMsg = new StringBuffer();

		if (!Networks.isEmpty()) {
			gtc = ps.getTopConcepts(Networks);

			try {
				jo = getProjectStatsJson(gtc);
				model.addAttribute("jsonstring", jo);
				model.addAttribute("networkid", "\"\"");
				model.addAttribute("project", project);

			} catch (JSONException e) {
				model.addAttribute("show_error_alert", true);
				errorMsg.append(e.getMessage());
				errorMsg.append("\n");
			}
		}

		model.addAttribute("error_alert_msg", errorMsg.toString());

		return "sites/project/statistics";
	}
}