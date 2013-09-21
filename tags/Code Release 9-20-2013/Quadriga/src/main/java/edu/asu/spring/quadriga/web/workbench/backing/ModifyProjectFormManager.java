package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class ModifyProjectFormManager 
{
		@Autowired
		IRetrieveProjectManager retrieveProjectManager;
		
		public List<ModifyProject> getUserProjectList(String userName) throws QuadrigaStorageException
		{
			List<IProject> projectList;
			List<ModifyProject> modifyProjectList;
			ModifyProject project;
			modifyProjectList = new ArrayList<ModifyProject>();
			
			//fetch the projects associated with the user
			projectList = retrieveProjectManager.getProjectList(userName);
			
			for(IProject iProject : projectList)
			{
				project = new ModifyProject();
				project.setInternalid(iProject.getInternalid());
				project.setName(iProject.getName());
				project.setDescription(iProject.getDescription());
				modifyProjectList.add(project);
			}
			
			return modifyProjectList;
		}
}
