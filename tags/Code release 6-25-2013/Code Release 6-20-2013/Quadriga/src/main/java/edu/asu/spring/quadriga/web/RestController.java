package edu.asu.spring.quadriga.web;

import java.io.StringWriter;
import java.io.Writer;

import java.util.List;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.domain.rest.ProjectList;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class RestController {



	@Autowired 
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired 
	IUserManager usermanager;

	@Autowired
	IProject project;

	@Autowired
	IProjectManager projectManager;

	@Autowired 
	IDictionaryFactory dictionaryFactory;

	@Autowired 
	DictionaryItemsFactory dictionaryItemsFactory;

	


	@RequestMapping(value="api/projects/{userID}", method = RequestMethod.GET , produces = "application/xml")
	@ResponseBody
	public ModelAndView listProjects(@PathVariable("userID") String userId, ModelMap model){	
		List<IProject> projectList=null;
		//add
		ProjectList projectListTest = new ProjectList();
		try {
			projectList = projectManager.getProjectsOfUser(userId);
			projectListTest.SetProjectList(projectList);

		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("projects","object",projectListTest);
	}
}
