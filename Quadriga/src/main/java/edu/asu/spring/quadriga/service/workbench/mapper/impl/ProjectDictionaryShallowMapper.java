package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectDictionaryFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceDictionaryFactory;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDictionaryShallowMapper;

@Service
public class ProjectDictionaryShallowMapper implements
		IProjectDictionaryShallowMapper {

	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IRetrieveDictionaryManager dictionaryManager;
	
	@Autowired
	private IProjectDictionaryFactory projectDictionaryFactory;
	
	@Override
	public List<IProjectDictionary> getProjectDictionaryList(IProject project,
			ProjectDTO projectDTO) throws QuadrigaStorageException {
		List<IProjectDictionary> projectDictionaryList = null;
		if(project != null){
			projectDictionaryList = new ArrayList<IProjectDictionary>();
			List<ProjectDictionaryDTO> projectDictionaryDTOList = projectDTO.getProjectDictionaryDTOList();
			if(projectDictionaryDTOList != null){
				for(ProjectDictionaryDTO projectDictionaryDTO : projectDictionaryDTOList){
					IDictionary dictionaryProxy = new DictionaryProxy(dictionaryManager);
					dictionaryProxy.setDictionaryId(projectDictionaryDTO.getDictionary().getDictionaryid());
					dictionaryProxy.setDictionaryName(projectDictionaryDTO.getDictionary().getDictionaryname());
					dictionaryProxy.setDescription(projectDictionaryDTO.getDictionary().getDescription());
					dictionaryProxy.setCreatedBy(projectDictionaryDTO.getDictionary().getCreatedby());
					dictionaryProxy.setCreatedDate(projectDictionaryDTO.getDictionary().getCreateddate());
					dictionaryProxy.setUpdatedBy(projectDictionaryDTO.getDictionary().getUpdatedby());
					dictionaryProxy.setUpdatedDate(projectDictionaryDTO.getDictionary().getUpdateddate());
					IProjectDictionary projectDictioanry = projectDictionaryFactory.createProjectDictionaryObject();
					projectDictioanry.setProejct(project);
					projectDictioanry.setDictionary(dictionaryProxy);
					projectDictioanry.setCreatedBy(projectDictionaryDTO.getCreatedby());
					projectDictioanry.setCreatedDate(projectDictionaryDTO.getCreateddate());
					projectDictioanry.setUpdatedBy(projectDictionaryDTO.getUpdatedby());
					projectDictioanry.setUpdatedDate(projectDictionaryDTO.getUpdateddate());
					projectDictionaryList.add(projectDictioanry);
				}
			}
		}
		return projectDictionaryList;
	
	}

}
