package edu.asu.spring.quadriga.domain.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IContributionStatsManager;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

/**
 * This method returns Json Array containing date and corresponding count of number 
 * of networks that were Approved/Rejected/Submitted depending on 'status' 
 * @author Bharath Srikantan
 * @throws JSONException 
 *
 */
@Service
public class ContributionStatsManager implements IContributionStatsManager{

    @Override
    public JSONArray getContributionCountByStatus (List<INetwork> networks, String status) throws JSONException
    {
        String DATE_FORMAT = "dd-MMM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        JSONArray contributionsJson = new JSONArray();
        HashMap<String, Integer> contributionCount = new HashMap<String, Integer>();

        for(INetwork network : networks)
        {
            String networkCreationDate = sdf.format(network.getCreatedDate());
            if(network.getStatus().equals(status) || status.equals("SUBMITTED"))
            {
                if(contributionCount.containsKey(networkCreationDate))
                {
                    contributionCount.put(networkCreationDate, contributionCount.get(networkCreationDate)+1);
                }
                else
                {
                    contributionCount.put(networkCreationDate, 1);
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

    @Override
    public JSONArray getWorkspaceContribution (IProject project) throws JSONException
    {
        String DATE_FORMAT = "dd-MMM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        JSONArray workspaceCount = new JSONArray();
        HashMap<String, Integer> contributionCount = new HashMap<String, Integer>();

        for(IProjectWorkspace ws : project.getProjectWorkspaces())
        {
            String workspaceCreationDate = sdf.format(ws.getCreatedDate());
            if(contributionCount.containsKey(workspaceCreationDate))
            {
                contributionCount.put(workspaceCreationDate, contributionCount.get(workspaceCreationDate)+1);
            }
            else
            {
                contributionCount.put(workspaceCreationDate, 1);
            }   
        }
        for(Entry<String, Integer> entry : contributionCount.entrySet())
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", entry.getKey());
            jsonObject.put("count", entry.getValue());
            workspaceCount.put(jsonObject);
        }
        return workspaceCount;          
    }

}
