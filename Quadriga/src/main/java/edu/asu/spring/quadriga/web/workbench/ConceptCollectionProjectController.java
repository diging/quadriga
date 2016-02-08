package edu.asu.spring.quadriga.web.workbench;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ConceptCollectionProjectController {

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired 
	IRetrieveProjectManager projectManager;

	@Autowired
	private IProjectConceptCollectionManager projectConceptCollectionManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionProjectController.class);



	@RequestMapping(value = "auth/workbench/{projectid}/conceptcollections", method = RequestMethod.GET)
	public String listProjectConceptCollection(@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		List<IProjectConceptCollection> projectConceptCollectionList = null;
		try {
			//TODO: listProjectConceptCollection() needs to be modified 
			projectConceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}

		model.addAttribute("projectConceptCollectionList", projectConceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/conceptcollections";
	}


	@RequestMapping(value = "auth/workbench/{projectid}/conceptcollectionsJson", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String listProjectConceptCollectionJson(@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException, QuadrigaException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		List<IProjectConceptCollection> projectConceptCollectionList = null;
		try {
			//TODO: listProjectConceptCollection() needs to be modified 
			projectConceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}
		
		JSONArray ja = new JSONArray();
        for(IProjectConceptCollection conceptCollection : projectConceptCollectionList){
            JSONObject j = new JSONObject();
            try {
                
                j.put("id", conceptCollection.getConceptCollection().getConceptCollectionId());
                j.put("name", conceptCollection.getConceptCollection().getConceptCollectionName());
                ja.put(j);
                
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                throw new QuadrigaException(e.getMessage(),e);
            }
            
        }
        
		return ja.toString();
	}


	//	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	//	@RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.GET)
	//	public String addProjectConceptCollection(
	//			@PathVariable("projectid") String projectid, Model model)
	//					throws QuadrigaAccessException
	//	
	//	{
	//		try {
	//			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
	//					.getAuthentication().getPrincipal();
	//			String userId = user.getUsername();
	//
	//			List<IProjectConceptCollection> conceptCollectionList = null;
	//			//TODO: getCollectionsOwnedbyUser() needs to be changed according to mapper
	//			try {
	//				conceptCollectionList = conceptCollectionManager.getCollectionsOwnedbyUser(userId);
	//			} catch (QuadrigaStorageException e) {
	//				throw new QuadrigaStorageException();
	//			}
	//			if (conceptCollectionList == null) {
	//				logger.info("conceptCollectionList list is empty");
	//			}
	//			//TODO: iterator needs to be changed
	//			Iterator<IProjectConceptCollection> I = conceptCollectionList.iterator(); 
	//			while(I.hasNext()){
	//				IConceptCollection con = I.next();
	//				logger.info(" "+con.getConceptCollectionName());
	//			}
	//			model.addAttribute("conceptCollectionList", conceptCollectionList);
	//			IProject project = projectManager.getProjectDetails(projectid);
	//			model.addAttribute("project", project);
	//			model.addAttribute("projectid", projectid);
	//			model.addAttribute("userId", userId);
	//		} catch (Exception e) {
	//			logger.error(" ----",e);
	//		}
	//		return "auth/workbench/project/addconceptcollections";
	//	}
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.GET)
	public String addProjectConceptCollection(
			@PathVariable("projectid") String projectid, Model model)
					throws QuadrigaAccessException

					{
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();

			List<IConceptCollection> conceptCollectionList = null;
			//TODO: getCollectionsOwnedbyUser() needs to be changed according to mapper
			try {
				conceptCollectionList = conceptCollectionManager.getCollectionsOwnedbyUser(userId);
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException();
			}
			if (conceptCollectionList == null) {
				logger.info("conceptCollectionList list is empty");
			}
			//TODO: iterator needs to be changed
			Iterator<IConceptCollection> itr = conceptCollectionList.iterator(); 
			while(itr.hasNext()){
				IConceptCollection con = itr.next();
				logger.info(" "+con.getConceptCollectionName());
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----",e);
		}
		return "auth/workbench/project/addconceptcollections";
					}

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 2, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.POST)
	public String addProjectConceptCollection(HttpServletRequest req,
			@PathVariable("projectid") String projectid, Model model) 
					throws QuadrigaStorageException, QuadrigaAccessException {
		String msg = "";
		int flag=0;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IProjectConceptCollection> conceptCollectionList = null;
			try {
				//TODO: listProjectConceptCollection() is to be changed according to mapper
				conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			return "auth/workbench/project/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					projectConceptCollectionManager.addProjectConceptCollection(projectid,
							values[i], userId);
					if(!msg.equals("")){
						flag=1;
					}
				} catch (QuadrigaStorageException e) {
					throw new QuadrigaStorageException();
				}
			}
		}
		if(flag==0){
			model.addAttribute("success", 1);
		}else{
			model.addAttribute("success", 0);
		}
		List<IConceptCollection> conceptCollectionList = null;
		try {
			// TODO: getCollectionsOwnedbyUser() is to be changed according to mapper
			conceptCollectionList = conceptCollectionManager.getCollectionsOwnedbyUser(userId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/conceptcollections";
	}

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/deleteconceptcollections", method = RequestMethod.GET)
	public String deleteProjectConceptCollection(@PathVariable("projectid") String projectid, Model model) 
			throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		List<IProjectConceptCollection> projectConceptCollectionList = null;
		try {
			projectConceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}

		if(projectConceptCollectionList != null){
			logger.info(" "+ projectConceptCollectionList.size());
		}else{
			logger.info("projectConceptCollectionList   ---- null" );
		}
		model.addAttribute("projectConceptCollectionList", projectConceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/deleteconceptcollections";
	}

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 2, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/deleteconceptcollections", method = RequestMethod.POST)
	public String deleteProjectConceptCollection(HttpServletRequest req,@PathVariable("projectid") String projectid, Model model) 
			throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		String msg = "";
		int flag=0;

		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IProjectConceptCollection> conceptCollectionList = null;
			try {
				// TODO: listProjectConceptCollection() is to be changed according to mapper
				conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			return "auth/workbench/project/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					projectConceptCollectionManager.deleteProjectConceptCollection(projectid, userId, values[i]);
				} catch (QuadrigaStorageException e) {
					throw new QuadrigaStorageException();
				}
				if(!msg.equals("")){
					flag=1;
				}
			}
		}
		if(flag==0){
			model.addAttribute("deletesuccess", 1);
		}else{
			model.addAttribute("deletesuccess", 0);
		}
		List<IProjectConceptCollection> conceptCollectionList = null;
		try {
			// TODO: listProjectConceptCollection() is to be changed according to mapper
			conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}
		if(conceptCollectionList == null){
			logger.info("Dictionary list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/conceptcollections";
	}
}
