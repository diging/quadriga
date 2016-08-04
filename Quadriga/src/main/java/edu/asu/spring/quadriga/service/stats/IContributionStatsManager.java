package edu.asu.spring.quadriga.service.stats;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * Interface to implement contribution count in statistics
 *
 */
public interface IContributionStatsManager {
    
    HashMap<String, Integer> getContributionCountByStatus (List<INetwork> networks, String status) throws JSONException;
    
    HashMap<String, Integer> getWorkspaceContribution (IProject project) throws JSONException;

}