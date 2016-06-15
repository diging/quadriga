package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class ModifyProjectFormManager 
{
		@Autowired
		IRetrieveProjectManager retrieveProjectManager;
		
		@Autowired
		private IQuadrigaRoleManager collaboratorRoleManager;
		
		/**
		 * Retrieve the projects associated with the given user
		 * @param userName
		 * @return Lit<ModifyProject>
		 * @throws QuadrigaStorageException
		 */
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
				project.setInternalid(iProject.getProjectId());
				project.setProjectName(iProject.getProjectName());
				project.setDescription(iProject.getDescription());
				project.setProjectOwner(iProject.getOwner().getUserName());
				modifyProjectList.add(project);
			}
			
			return modifyProjectList;
		}
		
		/**
		 * Retrieve the projects associated with the collaborator posessing the 
		 * specified role
		 * @param userName - collaborator user name
		 * @param role - collaborator role
		 * @return List<ModifyProject>
		 * @throws QuadrigaStorageException
		 */
		public List<ModifyProject> getProjectList(String userName,String role) throws QuadrigaStorageException
		{
			String collaboratorRole;
			List<IProject> projectList;
			List<IProject> collaboratorProjectList;
			List<ModifyProject> modifyProjectList;
			ModifyProject project;
			modifyProjectList = new ArrayList<ModifyProject>();
			
			//fetch the projects associated with the user
			projectList = retrieveProjectManager.getProjectList(userName);
			
			//fetch the database id for the role
			collaboratorRole = collaboratorRoleManager.getQuadrigaRoleById(IQuadrigaRoleManager.PROJECT_ROLES, role).getDBid();
			
			//fetch the project for which the user is Admin
			collaboratorProjectList = retrieveProjectManager.getProjectListByCollaboratorRole(userName,
					collaboratorRole);
			
			if(projectList !=null)
			{
				for(IProject iProject : projectList)
				{
					project = new ModifyProject();
					project.setInternalid(iProject.getProjectId());
					project.setProjectName(iProject.getProjectName());
					project.setDescription(iProject.getDescription());
					project.setProjectOwner(iProject.getOwner().getUserName());
					modifyProjectList.add(project);
				}
			}
			
			if(collaboratorProjectList !=null)
			{
				for(IProject iCollabProject : collaboratorProjectList)
				{
					project = new ModifyProject();
					project.setInternalid(iCollabProject.getProjectId());
					project.setProjectName(iCollabProject.getProjectName());
					project.setDescription(iCollabProject.getDescription());
					project.setProjectOwner(iCollabProject.getOwner().getUserName());
					if(!modifyProjectList.contains(project))
					{
						modifyProjectList.add(project);
					}
				}
			}
			return modifyProjectList;
		}
}
