package edu.asu.spring.quadriga.web.workspace;

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
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;

@Controller
public class ConceptCollectionWorkspaceController {

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired
	private IWorkspaceCCManager workspaceCCManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionWorkspaceController.class);

	

	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/conceptcollections", method = RequestMethod.GET)
	public String listProjectConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.info("Concept collection list is empty buddy");
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
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
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}
	
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.GET)
	public String addProjectConceptCollection(
			@PathVariable("workspaceid") String workspaceId, Model model) {
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
			model.addAttribute("workspaceId", workspaceId);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/workspace/addconceptcollections";
	}
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.POST)
	public String addProjectConceptCollection(HttpServletRequest req,
			@PathVariable("workspaceid") String workspaceId, Model model) {
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
				conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			model.addAttribute("workspaceId", workspaceId);
			return "auth/workbench/workspace/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					msg=workspaceCCManager.addWorkspaceCC(workspaceId, values[i], userId);
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
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}

	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.GET)
	public String deleteProjectDictionary(@PathVariable("workspaceid") String workspaceId, Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		model.addAttribute("projectid", workspaceId);
		return "auth/workbench/workspace/deleteconceptcollections";
	}
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.POST)
	public String deleteProjectDictionary(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model) {
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
				conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			model.addAttribute("projectid", workspaceId);
			return "auth/workbench/workspace/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					msg=workspaceCCManager.deleteWorkspaceCC(workspaceId, userId, values[i]);
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
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("Dictionary list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		model.addAttribute("projectid", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}
}
