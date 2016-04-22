package edu.asu.spring.quadriga.domain;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * Interface to implement contribution count in statistics
 *
 */
public interface IContributionStatsManager {
    
    JSONArray getContributionCountByStatus (List<INetwork> networks, String status) throws JSONException;
    
    JSONArray getWorkspaceContribution (IProject project) throws JSONException;

}