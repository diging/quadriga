package edu.asu.spring.quadriga.web.publicwebsite;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;


@Controller
public class WebsiteProjectController {
	
	public static String projectid = "";
	
	@Autowired 
	IRetrieveProjectManager projectManager;
	
	@Autowired
	INetworkManager networkmanager;
	
	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(IRetrieveProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	@RequestMapping(value="sites/{ProjectUnixName}", method=RequestMethod.GET)
	public String showProject(@PathVariable("ProjectUnixName") String unixName,Model model) throws QuadrigaStorageException {
		
		
		
		IProject project = projectManager.getProjectDetailsByUnixName(unixName);
		projectid = project.getInternalid();
		if(project!=null){
			model.addAttribute("project", project);
			return "website";
		} 
		else
			return "forbidden";
		
		
	}
	
	@RequestMapping(value="sites/project/browsenetworks", method=RequestMethod.GET)
	public String browseNetworks(Model model) throws QuadrigaStorageException{
		List<String> Networks = networkmanager.getNetworksForProjectId(projectid);
		if(Networks!=null){
			model.addAttribute("networks", Networks);
			return "browseNetworks";
		}
		
		return "browseNetworks";
	}
}
