package edu.asu.spring.quadriga.web.sites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This Controller is mapped to handle public site page 
 * @author Charan Thej Aware
 * @author Madhu Meghana Talasila
 */

@Controller
public class SitesController {
	@Autowired 
	private IRetrieveProjectManager retrieveProjectManager;

	/**
	 * This method is used to access the public page -sites that enlists the public pages of all the projects 
	 * @param locale
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "sites", method = RequestMethod.GET)
	public String showQuadrigaPublicPages(Model model) throws QuadrigaStorageException {
		List<IProject> projectList = retrieveProjectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC.name());
		model.addAttribute("projectList", projectList);
		return "sites";
	}
	
	/**
	 * This method is used to access the public page -sites that enlists the public pages of all the projects 
	 * @param locale
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "sites/searchTerm", method = RequestMethod.POST)
	public String showPublicProjectsWithSearchTerm(@RequestParam("searchTerm") String searchTerm, Model model) throws QuadrigaStorageException {
		System.out.println(searchTerm);
		model.addAttribute("searchTerm", searchTerm);
		return "searchsites";
	}
}
