package edu.asu.spring.quadriga.web.publicwebsite;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Controller
public class ProjectStatsController {
	
	 @Autowired
	 private IRetrieveProjectManager projectManager;
	
	/**
	 * This method gives the visualization of  how often concepts appear in the networks
	 * @author Bharath Srikantan
	 * @param projectUnixName	The project unix name
	 * @param model				Model
	 * @return view
	 * @throws JAXBException
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@RequestMapping(value = "sites/{projectUnixName}/statistics", method = RequestMethod.GET)
	public String showProjectStatistics(@PathVariable("projectUnixName") String projectUnixName,
									   Model model)throws JAXBException, QuadrigaStorageException, JSONException {
		
		IProject project = projectManager.getProjectDetailsByUnixName(projectUnixName);

		if (project == null) {
			return "auth/accessissue";
		}
 
		String data = "[{\"conceptId\":\"ID-1\",\"description\":\"Desc1\",\"frequency\":\".01\"},{\"conceptId\":\"ID-2\",\"description\":\"Desc2\",\"frequency\":\".03\"},{\"conceptId\":\"ID-3\",\"description\":\"Desc3\",\"frequency\":\".01\"},{\"conceptId\":\"ID-4\",\"description\":\"Desc4\",\"frequency\":\".02\"},{\"conceptId\":\"ID-5\",\"description\":\"Desc5\",\"frequency\":\".04\"}]";
	
		model.addAttribute("jsonstring", data);
		model.addAttribute("networkid", "\"\"");
		model.addAttribute("project", project);

		return "sites/project/statistics";
	}
}
