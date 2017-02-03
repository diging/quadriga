package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.publicwebsite.impl.ProjectStats;
import edu.asu.spring.quadriga.service.stats.IContributionStatsManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This controller has all the mappings required to view the statistics of the
 * project
 * 
 * @author Ajay Modi
 *
 */

@PropertySource(value = "classpath:/settings.properties")
@Controller
public class ProjectStatsController {

    @Autowired
    private INetworkManager networkmanager;

    @Autowired
    private ProjectStats projectStats;

    @Autowired
    private Environment env;

    @Autowired
    private IContributionStatsManager contributionManager;

    private static final String SUBMITTED = "SUBMITTED";

    private int getCount(List<IConceptStats> conceptList) {
        int cnt = Integer.parseInt(env.getProperty("project.stats.topcount"));
        int len = conceptList.size() > cnt ? cnt : conceptList.size();
        return len;
    }

    private JSONArray getTopConceptsJson(List<IConceptStats> conceptsList, int length) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = new JSONObject();
            IConceptStats conceptStats = conceptsList.get(i);
            jsonObject.put("conceptId", conceptStats.getConceptId());
            jsonObject.put("description", StringEscapeUtils.escapeHtml(conceptStats.getDescription()));
            jsonObject.put("label", StringEscapeUtils.escapeHtml(conceptStats.getLemma()));
            jsonObject.put("count", conceptStats.getCount());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    private JSONArray getContributionCountJson(List<INetwork> networks, String status) throws JSONException {
        JSONArray contributionsJson = new JSONArray();
        HashMap<String, Integer> contributionCount = contributionManager.getContributionCountByStatus(networks, status);

        for (Entry<String, Integer> entry : contributionCount.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", entry.getKey());
            jsonObject.put("count", entry.getValue());
            contributionsJson.put(jsonObject);
        }
        return contributionsJson;
    }

    private JSONArray getWorkspaceContributionJson(IProject project) throws JSONException {
        JSONArray workspaceCountArray = new JSONArray();
        HashMap<String, Integer> workspaceCount = contributionManager.getWorkspaceContribution(project);

        for (Entry<String, Integer> entry : workspaceCount.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", entry.getKey());
            jsonObject.put("count", entry.getValue());
            workspaceCountArray.put(jsonObject);
        }
        return workspaceCountArray;
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
    @CheckPublicAccess
    @InjectProjectByName
    @RequestMapping(value = "sites/{projectUnixName}/statistics", method = RequestMethod.GET)
    public String showProjectStatistics(@ProjectIdentifier @PathVariable("projectUnixName") String projectUnixName,
            @CheckAccess @InjectProject IProject project, Model model, Principal principal)
            throws JAXBException, QuadrigaStorageException {

        String projectId = project.getProjectId();
        model.addAttribute("project", project);
        model.addAttribute("labelCount",'0');
        List<INetwork> networks = networkmanager.getNetworksInProject(projectId, null);
        List<IConceptStats> conceptsWithCount = null;

        if (!networks.isEmpty()) {
            conceptsWithCount = projectStats.getConceptCount(networks);
            try {
                int cnt = getCount(conceptsWithCount);
                JSONArray labelCount = getTopConceptsJson(conceptsWithCount.subList(0, cnt), cnt);

                JSONArray submittedNetworkCount = getContributionCountJson(networks, SUBMITTED);
                JSONArray approvedNetworkCount = getContributionCountJson(networks, INetworkStatus.APPROVED);
                JSONArray rejectedNetworkCount = getContributionCountJson(networks, INetworkStatus.REJECTED);
                JSONArray workspaceCount = getWorkspaceContributionJson(project);

                model.addAttribute("submittedNetworksData",
                        submittedNetworkCount.length() > 0 ? submittedNetworkCount.toString() : null);
                model.addAttribute("approvedNetworksData",
                        approvedNetworkCount.length() > 0 ? approvedNetworkCount.toString() : null);
                model.addAttribute("rejectedNetworksData",
                        rejectedNetworkCount.length() > 0 ? rejectedNetworkCount.toString() : null);
                model.addAttribute("workspaceData", workspaceCount.length() > 0 ? workspaceCount.toString() : null);
                model.addAttribute("networks", networks);
                model.addAttribute("labelCount", labelCount.length() > 0 ? labelCount.toString() : '0');
                model.addAttribute("networkid", "\"\"");
            } catch (JSONException e) {

                StringBuffer errorMsg = new StringBuffer();
                model.addAttribute("show_error_alert", true);
                errorMsg.append(e.getMessage());
                errorMsg.append("\n");
                model.addAttribute("error_alert_msg", errorMsg.toString());
            }
        }

        return "sites/project/statistics";
    }
}
