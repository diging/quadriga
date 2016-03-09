package edu.asu.spring.quadriga.web.publicwebsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@PropertySource(value = "classpath:/user.properties")
@Controller
/**
 * This controller class is responsible for providing views for project blog creation.
 *
 * @author Pawan Mahalle
 *
 */
public class PublicBlogController {
	@Autowired
	private IRetrieveProjectManager projectManager;

	private IProject getProjectDetails(String name) throws QuadrigaStorageException {
		return projectManager.getProjectDetailsByUnixName(name);
	}

	/**
	 * This method gives the the projectblog
	 * 
	 * @param projectUnixName
	 *            The project unix name
	 * @param model
	 *            Model
	 * @return view
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "sites/{projectUnixName}/projectblog", method = RequestMethod.GET)
	public String projectblog(@PathVariable("projectUnixName") String projectUnixName, Model model)
			throws QuadrigaStorageException {
		IProject project = getProjectDetails(projectUnixName);

		if (project == null) {
			return "forbidden";
		}

		// Creating Dummy Object
		List dummyList = new ArrayList();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", "Article: Apple’s iPhone Blunder");
		map.put("text",
				"<b> Can the United States <b> government compel Apple to help break into the phone of Syed Rizwan Farook, who, along with his wife Tafsheen Malil, gunned down fourteen innocent people last December at the Inland Regional Center in San Bernardino? That question has sparked fireworks in recent days. The dispute arises because Apple has equipped its new iPhones with encryption settings that erase the data contained on the phone whenever ten false password entries have been made. It was agreed on all sides that only Apple has the technology that might overcome the encryption device. [...]");
		map.put("date", "February 22, 2016");
		map.put("author", "Daniel T. Richards ");

		dummyList.add(map);

		/*
		 * for(int i=1; i<=10; i++) { HashMap <String, String> map = new
		 * HashMap<String, String>(); map.put("title", "Title "+i);
		 * map.put("text", "Text "+i); map.put("date", "Date "+i);
		 * map.put("author", "Author "+i);
		 * 
		 * dummyList.add(map); }
		 */
		model.addAttribute("blockentrylist", dummyList);

		return "sites/projectblog";
	}

	/**
	 * This method gives the the addprojectblog page
	 * 
	 * @param projectUnixName
	 *            The project unix name
	 * @param model
	 *            Model
	 * @return view
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "sites/{projectUnixName}/sites/addprojectblog", method = RequestMethod.GET)
	public String addprojectblog(@PathVariable("projectUnixName") String projectUnixName, Model model)
			throws QuadrigaStorageException {

		// Creating project object for validating project name and to be used in
		// future for saving the blog and
		IProject project = getProjectDetails(projectUnixName);

		if (project == null) {
			return "forbidden";
		}

		return "sites/addprojectblog";
	}
}
