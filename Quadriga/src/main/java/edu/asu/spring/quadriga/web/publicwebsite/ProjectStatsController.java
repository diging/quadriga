package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.ConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.publicwebsite.impl.ProjectStats;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This controller has all the mappings required to view the statistics of the
 * project
 * 
 * @author Ajay Modi
 *
 */

@PropertySource(value = "classpath:/user.properties")
@Controller
public class ProjectStatsController {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@Autowired
	private INetworkManager networkmanager;

	@Autowired
	private ProjectStats projectStats;

	@Autowired
	private Environment env;

	@Autowired
	private IEditorManager editorManager;

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IProject iprojectManager;

	@Autowired
	private IProjectWorkspace workspace; 

	private static final Logger logger = LoggerFactory.getLogger(ProjectStatsController.class);
	private static final String SUBMITTED = "SUBMITTED";
	private static final String APPROVED = "APPROVED";
	private static final String REJECTED = "REJECTED";



	private int getCount(List<IConceptStats> conceptList) {
		int cnt = Integer.parseInt(env.getProperty("project.stats.topcount"));
		int len = conceptList.size() > cnt ? cnt : conceptList.size();
		return len;
	}

	private JSONArray getTopConceptsJson(List<IConceptStats> conceptsList,
			int length) throws JSONException {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < length; i++) {
			JSONObject jsonObject = new JSONObject();
			IConceptStats conceptStats = conceptsList.get(i);
			jsonObject.put("conceptId", conceptStats.getConceptId().replace("\"", ""));
			jsonObject.put("description", conceptStats.getDescription().replace("\"", ""));
			jsonObject.put("label", conceptStats.getLemma().replace("\"", ""));
			jsonObject.put("count", conceptStats.getCount());
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * This method returns Json Array containing date and corresponding count of number 
	 * of networks that were Approved/Rejected/Submitted depending on 'status' 
	 * @author Bharath Srikantan
	 * @throws JSONException 
	 *
	 */
	private JSONArray getContributionCountByStatus (List<INetwork> networks, String status) throws JSONException
	{
		String DATE_FORMAT = "dd-MMM-yy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		JSONArray contributionsJson = new JSONArray();
		HashMap<String, Integer> contributionCount = new HashMap<String, Integer>();

		for(INetwork network : networks)
		{
			if(network.getStatus().equals(status) || status.equals("SUBMITTED"))
			{
				if(contributionCount.containsKey(sdf.format(network.getCreatedDate())))
				{
					contributionCount.put(sdf.format(network.getCreatedDate()), contributionCount.get(sdf.format(network.getCreatedDate()))+1);
				}
				else
				{
					contributionCount.put(sdf.format(network.getCreatedDate()), 1);
				}
			}
		}
		for(Entry<String, Integer> entry : contributionCount.entrySet())
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("date", entry.getKey());
			jsonObject.put("count", entry.getValue());
			contributionsJson.put(jsonObject);
		}
		return contributionsJson;    	
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
	 * @throws JSONException 
	 */

	@RequestMapping(value = "sites/{projectUnixName}/statistics", method = RequestMethod.GET)
	public String showProjectStatistics(
			@PathVariable("projectUnixName") String projectUnixName, Model model, Principal principal)
					throws JAXBException, QuadrigaStorageException, JSONException {

		IProject project = projectManager.getProjectDetailsByUnixName(projectUnixName);

		if (project == null) {
			return "auth/accessissue";
		}

		String projectId = project.getProjectId();
		List<INetwork> networks = networkmanager.getNetworksInProject(projectId);
		List<IConceptStats> conceptsWithCount = null;
		JSONArray submittedNetworkCount = null, approvedNetworkCount = null, rejectedNetworkCount  = null, workspaceCount = null;
		String submittedNetworks = null, approvedNetworks = null, rejectedNetworks = null;
		
		if (!networks.isEmpty()) {
			conceptsWithCount = projectStats.getConceptCount(networks);

			try {
				JSONArray jArray = null;
				int cnt = getCount(conceptsWithCount);
				jArray = getTopConceptsJson(conceptsWithCount.subList(0, cnt),
						cnt);
				String jsonData = jArray.toString(); 
				model.addAttribute("labelCount", jsonData);
				model.addAttribute("networkid", "\"\"");

			} catch (JSONException e) {

				StringBuffer errorMsg = new StringBuffer();
				model.addAttribute("show_error_alert", true);
				errorMsg.append(e.getMessage());
				errorMsg.append("\n");
				model.addAttribute("error_alert_msg", errorMsg.toString());
			}

			submittedNetworkCount= getContributionCountByStatus(networks,SUBMITTED);
			approvedNetworkCount= getContributionCountByStatus(networks,APPROVED);
			rejectedNetworkCount= getContributionCountByStatus(networks,REJECTED);
			
			submittedNetworks = submittedNetworkCount.toString();
			approvedNetworks = approvedNetworkCount.toString();
			rejectedNetworks = rejectedNetworkCount.toString();
		}
		model.addAttribute("project", project);
		model.addAttribute("submittedNetworksData",submittedNetworks);
		model.addAttribute("approvedNetworksData",approvedNetworks);
		model.addAttribute("rejectedNetworksData",rejectedNetworks);
		return "sites/project/statistics";
	}
}