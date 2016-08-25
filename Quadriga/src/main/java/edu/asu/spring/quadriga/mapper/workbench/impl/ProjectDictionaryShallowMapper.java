package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectDictionaryFactory;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

@Service
public class ProjectDictionaryShallowMapper implements
IProjectDictionaryShallowMapper {

	@Autowired
	private IRetrieveProjectDAO dbConnect;


	@Autowired
	private IProjectShallowMapper projectShallowMapper;	

	@Autowired
	private IDictionaryManager dictionaryManager;

	@Autowired
	private IProjectDictionaryFactory projectDictionaryFactory;

	@Override
	public List<IProjectDictionary> getProjectDictionaryList(IProject project,
			ProjectDTO projectDTO) throws QuadrigaStorageException {
		List<IProjectDictionary> projectDictionaryList = null;
		if(project != null && projectDTO != null){
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
					projectDictioanry.setProject(project);
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


	@Override
	public List<IProjectDictionary> getProjectDictionaryList(DictionaryDTO dictionaryDTO, IDictionary dictionary) throws QuadrigaStorageException{
		List<IProjectDictionary> projectDictionaryList = null;

		List<ProjectDictionaryDTO> projectDictionaryDTOList = dictionaryDTO.getProjectDictionaryDTOList();
		if(projectDictionaryDTOList != null){
			for(ProjectDictionaryDTO projectDictionaryDTO : projectDictionaryDTOList){
				if(projectDictionaryList == null){
					projectDictionaryList = new ArrayList<IProjectDictionary>();
				}
				ProjectDTO projectDTO = projectDictionaryDTO.getProject();
				IProject project = projectShallowMapper.getProjectDetails(projectDTO);
				IProjectDictionary projectDictionary = projectDictionaryFactory.createProjectDictionaryObject();
				projectDictionary.setProject(project);
				projectDictionary.setDictionary(dictionary);
				projectDictionary.setCreatedBy(projectDictionaryDTO.getCreatedby());
				projectDictionary.setCreatedDate(projectDictionaryDTO.getCreateddate());
				projectDictionary.setUpdatedBy(projectDictionaryDTO.getUpdatedby());
				projectDictionary.setUpdatedDate(projectDictionaryDTO.getUpdateddate());
				projectDictionaryList.add(projectDictionary);
				
			}
		}


		return projectDictionaryList;
	}
	
	

}
