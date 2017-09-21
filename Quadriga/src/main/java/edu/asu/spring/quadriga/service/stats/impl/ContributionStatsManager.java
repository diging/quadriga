package edu.asu.spring.quadriga.service.stats.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.service.stats.IContributionStatsManager;

/**
 * This class is used to get the contribution count of network and workspace 
 * @author Bharath Srikantan
 */
@Service
public class ContributionStatsManager implements IContributionStatsManager {

    /**
     * This method returns HashMap containing date as string and corresponding count of number 
     * of networks that were Approved/Rejected/Submitted depending on 'status' 
     * @author Bharath Srikantan
     *
     */
    @Override
    public HashMap<String, Integer> getContributionCountByStatus (List<INetwork> networks, String status) {
        String DATE_FORMAT = "dd-MMM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        HashMap<String, Integer> contributionCount = new HashMap<String, Integer>();

        for(INetwork network : networks) { 
            String networkCreationDate = sdf.format(network.getCreatedDate());
            if(network.getStatus().equals(status) || status.equals("SUBMITTED")) {
                if(contributionCount.containsKey(networkCreationDate)) {
                    contributionCount.put(networkCreationDate, contributionCount.get(networkCreationDate)+1);
                } else {
                    contributionCount.put(networkCreationDate, 1);
                }
            }
        }
        return contributionCount;       
    }

    /**
     * This method returns HashMap containing date as string and corresponding count of number 
     * of workspace created on that date. 
     * @author Bharath Srikantan
     *
     */
    @Override
    public HashMap<String, Integer> getWorkspaceContribution (IProject project) {
        String DATE_FORMAT = "dd-MMM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        HashMap<String, Integer> contributionCount = new HashMap<String, Integer>();

        for(IWorkspace ws : project.getWorkspaces()) {
            String workspaceCreationDate = sdf.format(ws.getCreatedDate());
            if(contributionCount.containsKey(workspaceCreationDate)) {
                contributionCount.put(workspaceCreationDate, contributionCount.get(workspaceCreationDate)+1);
            } else {
                contributionCount.put(workspaceCreationDate, 1);
            }   
        }
        return contributionCount;          
    }

}
