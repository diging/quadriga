package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveJsonProjects;

@Service
public class RetrieveJsonProjectsManager implements IRetrieveJsonProjects {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RetrieveJsonProjectsManager.class);
	
	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;

	
	/**
	 * This method returns the JSON string with list of projects associated with
	 * the logged in user as owner of projects 
	 * @param sUserName
	 * @return String - JSON string.
	 * @throws QuadrigaStorageException,JSONException
	 * @author Sowjanya Ambati
	 */
	
	@Override
	@Transactional
	public String getProjectList(String sUserName)
			throws QuadrigaStorageException, JSONException {
		List<IProject> projectList;
		JSONArray dataArray = new JSONArray();
		JSONObject core = new JSONObject();
		try {
		projectList = dbConnect.getProjectList(sUserName);
		for (IProject project : projectList) {
			
				JSONObject data = new JSONObject();
				data.put("id", project.getInternalid());
				data.put("parent", "#");
				String projectLink = null;
				projectLink = "<a href='#' id='"
						+ project.getInternalid()
						+ "' name='"
						+ project.getName()
						+ "' onclick='javascript:clickproject(this.id,this.name);' > "
						+ project.getName() + "</a>";
				
				data.put("text", projectLink);
				dataArray.put(data);
		}
		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("owner", dataList);
		}catch (QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace  details",
					e);
		}
		
		return core.toString(1);
	}
	/**
	 * This method returns JSON string with list of projects associated with
	 * the logged in user as workspace collaborator.
	 * @param sUserName
	 * @return String - JSON string.
	 * @throws QuadrigaStorageException , JSONException
	 * @author Sowjanya Ambati
	 */
	@Override
	@Transactional
	public String getProjectListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException ,JSONException{
		List<IProject> projectList;
		JSONArray dataArray = new JSONArray();
		JSONObject core = new JSONObject();
		try {
		projectList = dbConnect.getProjectListAsWorkspaceCollaborator(sUserName);
		for (IProject project : projectList) {
			
				JSONObject data = new JSONObject();
				data.put("id", project.getInternalid());
				data.put("parent", "#");
				String projectLink = null;
				projectLink = "<a href='#' id='"
						+ project.getInternalid()
						+ "' name='"
						+ project.getName()
						+ "' onclick='javascript:clickproject(this.id,this.name);' > "
						+ project.getName() + "</a>";
				
				data.put("text", projectLink);
				dataArray.put(data);
		}
		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("wscollboarator", dataList);
		}catch (QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace  details",
					e);
		}
		
		return core.toString(1);
	}
	
	/**
	 * This method returns JSON string with list of projects associated with
	 * the logged in user as workspace owner
	 * @param sUserName
	 * @return String - JSON string.
	 * @throws QuadrigaStorageException , JSONException
	 * @author Sowjanya Ambati
	 */
	@Override
	@Transactional
	public String getProjectListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException ,JSONException{
		List<IProject> projectList;
		JSONArray dataArray = new JSONArray();
		JSONObject core = new JSONObject();
		try {
		projectList = dbConnect.getProjectListAsWorkspaceOwner(sUserName);
		for (IProject project : projectList) {
			
				JSONObject data = new JSONObject();
				data.put("id", project.getInternalid());
				data.put("parent", "#");
				String projectLink = null;
				projectLink = "<a href='#' id='"
						+ project.getInternalid()
						+ "' name='"
						+ project.getName()
						+ "' onclick='javascript:clickproject(this.id,this.name);' > "
						+ project.getName() + "</a>";
				
				data.put("text", projectLink);
				dataArray.put(data);
		}
		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("wsowner", dataList);
		}catch (QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace  details",
					e);
		}
		
		return core.toString(1);
	}
	
	/**
	 * This method returns JSON string with list of projects associated with
	 * the logged in user as a collaborator.
	 * @param sUserName
	 * @return String - JSON string.
	 * @throws QuadrigaStorageException , JSONException
	 * @author Sowjanya Ambati
	 */
	@Override
	@Transactional
	public String getCollaboratorProjectList(String sUserName)
			throws QuadrigaStorageException ,JSONException {
		List<IProject> projectList;
		JSONArray dataArray = new JSONArray();
		JSONObject core = new JSONObject();
		try {
		projectList = dbConnect.getCollaboratorProjectList(sUserName);
		for (IProject project : projectList) {
			
				JSONObject data = new JSONObject();
				data.put("id", project.getInternalid());
				data.put("parent", "#");
				String projectLink = null;
				projectLink = "<a href='#' id='"
						+ project.getInternalid()
						+ "' name='"
						+ project.getName()
						+ "' onclick='javascript:clickproject(this.id,this.name);' > "
						+ project.getName() + "</a>";
				
				data.put("text", projectLink);
				dataArray.put(data);
		}
		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("collaborator", dataList);
		}catch (QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace  details",
					e);
		}
		
		return core.toString(1);
	}

	/**
	 * This method returns JSON string with list of all projects associated with
	 * the logged in user.
	 * @param sUserName
	 * @return String - JSON string.
	 * @throws QuadrigaStorageException , JSONException
	 * @author Sowjanya Ambati
	 */
	@Override
	@Transactional
	public String getAllProjects(String sUserName)
			throws QuadrigaStorageException, JSONException {
		JSONArray dataArray = new JSONArray();
		JSONObject core = new JSONObject();
		try {
		List<IProject> allProjectsList = new ArrayList<IProject>() ;
		List<IProject> projectList = dbConnect.getProjectList(sUserName);
		for(IProject project : projectList){
			allProjectsList.add(project);
		}
		projectList = null;
		projectList = dbConnect.getProjectListAsWorkspaceCollaborator(sUserName);
		for(IProject project : projectList){
			if(!allProjectsList.contains(project)) {
			allProjectsList.add(project);
			}
		}
		projectList = null;
		projectList = dbConnect.getCollaboratorProjectList(sUserName); 
		for(IProject project : projectList){
			if(!allProjectsList.contains(project)) {
			allProjectsList.add(project);
			}
		}
		projectList = null;
		projectList = dbConnect.getProjectListAsWorkspaceOwner(sUserName);
		for(IProject project : projectList){
			if(!allProjectsList.contains(project)) {
			allProjectsList.add(project);
			}
		}
		for (IProject project : allProjectsList) {
			
				JSONObject data = new JSONObject();
				data.put("id", project.getInternalid());
				data.put("parent", "#");
				String projectLink = null;
				projectLink = "<a href='#' id='"
						+ project.getInternalid()
						+ "' name='"
						+ project.getName()
						+ "' onclick='javascript:clickproject(this.id,this.name);' > "
						+ project.getName() + "</a>";
				
				data.put("text", projectLink);
				dataArray.put(data);
		}
		JSONObject dataList = new JSONObject();
		dataList.put("data", dataArray);

		core.put("allprojects", dataList);
		}catch (QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace  details",
					e);
		}
		
		return core.toString(1);
	}

}
