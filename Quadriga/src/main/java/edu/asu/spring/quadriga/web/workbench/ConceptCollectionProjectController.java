package edu.asu.spring.quadriga.web.workbench;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

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
		logger.info("Concept collection list is empty buddy");
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("Concept collection list is empty buddy");
		}
		Iterator<IConceptCollection> I = conceptCollectionList.iterator(); 
		while(I.hasNext()){
			IConceptCollection con = I.next();
			logger.info(" "+con.getName());
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/conceptcollections";
	}
	
	
	@RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.GET)
	public String addProjectConceptCollection(
			@PathVariable("projectid") String projectid, Model model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();

			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = conceptCollectionManager.getCollectionsOwnedbyUser(userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (conceptCollectionList == null) {
				logger.info("conceptCollectionList list is empty");
			}
			Iterator<IConceptCollection> I = conceptCollectionList.iterator(); 
			while(I.hasNext()){
				IConceptCollection con = I.next();
				logger.info(" "+con.getName());
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/project/addconceptcollections";
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.POST)
	public String addProjectConceptCollection(HttpServletRequest req,
			@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		String msg = "";
		int flag=0;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					msg=projectConceptCollectionManager.addProjectConceptCollection(projectid,
							values[i], userId);
					if(!msg.equals("")){
						flag=1;
					}
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			conceptCollectionList = conceptCollectionManager.getCollectionsOwnedbyUser(userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/conceptcollections";
	}

	
	@RequestMapping(value = "auth/workbench/{projectid}/deleteconceptcollections", method = RequestMethod.GET)
	public String deleteProjectDictionary(@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/deleteconceptcollections";
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/deleteconceptcollections", method = RequestMethod.POST)
	public String deleteProjectDictionary(HttpServletRequest req,@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		String msg = "";
		int flag=0;
		
		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					msg=projectConceptCollectionManager.deleteProjectConceptCollection(projectid, userId, values[i]);
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
