package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.publicwebsite.impl.ProjectStats;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.IUserStats;

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
            jsonObject.put("conceptId",
                    conceptStats.getConceptId().replace("\"", ""));
            jsonObject.put("description", conceptStats.getDescription()
                    .replace("\"", ""));
            jsonObject.put("label", conceptStats.getLemma().replace("\"", ""));
            jsonObject.put("count", conceptStats.getCount());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
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
        IProject project = projectManager
                .getProjectDetailsByUnixName(projectUnixName);

        if (project == null) {
            return "auth/accessissue";
        }

        String projectId = project.getProjectId();
        List<INetwork> networks = networkmanager
                .getNetworksInProject(projectId);
        List<IConceptStats> conceptsWithCount = null;
        List<IUserStats> userStats;
        if (!networks.isEmpty()) {
            conceptsWithCount = projectStats.getConceptCount(networks);
            userStats = projectStats.getUserStats(projectId);
            try {
                JSONArray jArray = null;
                int cnt = getCount(conceptsWithCount);
                jArray = getTopConceptsJson(conceptsWithCount.subList(0, cnt),
                        cnt);
                String jsonData = jArray.toString();
                model.addAttribute("jsonstring", jsonData);
                model.addAttribute("networkid", "\"\"");

            } catch (JSONException e) {

                StringBuffer errorMsg = new StringBuffer();
                model.addAttribute("show_error_alert", true);
                errorMsg.append(e.getMessage());
                errorMsg.append("\n");
                model.addAttribute("error_alert_msg", errorMsg.toString());
            }
        }
        model.addAttribute("project", project);
        return "sites/project/statistics";
    }
}