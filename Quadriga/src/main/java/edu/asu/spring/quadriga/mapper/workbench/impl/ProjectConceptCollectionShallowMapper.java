package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service
public class ProjectConceptCollectionShallowMapper implements
IProjectConceptCollectionShallowMapper {
	@Autowired
	private IRetrieveProjectDAO dbConnect;

	@Autowired
	private IConceptCollectionManager ccManager;

	@Autowired
	private IProjectConceptCollectionFactory projectCCFactory;
	
	@Autowired
	private IProjectShallowMapper projectShallowMapper;	

	

	@Override
	public List<IProjectConceptCollection> getProjectConceptCollectionList(
			IProject project, List<ProjectConceptCollectionDTO> projectCCDTOList)
					throws QuadrigaStorageException {
		List<IProjectConceptCollection> projectCCList = new ArrayList<IProjectConceptCollection>();
			
		for(ProjectConceptCollectionDTO projectCCDTO : projectCCDTOList){
			IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
			ccProxy.setConceptCollectionId(projectCCDTO.getConceptCollection().getConceptCollectionid());
			ccProxy.setConceptCollectionName(projectCCDTO.getConceptCollection().getCollectionname());
			ccProxy.setDescription(projectCCDTO.getConceptCollection().getDescription());
			ccProxy.setCreatedBy(projectCCDTO.getConceptCollection().getCreatedby());
			ccProxy.setCreatedDate(projectCCDTO.getConceptCollection().getCreateddate());
			ccProxy.setUpdatedBy(projectCCDTO.getConceptCollection().getUpdatedby());
			ccProxy.setUpdatedDate(projectCCDTO.getConceptCollection().getUpdateddate());
			IProjectConceptCollection projectCC = projectCCFactory.createProjectConceptCollectionObject();
			projectCC.setProject(project);
			projectCC.setConceptCollection(ccProxy);
			projectCC.setCreatedBy(projectCCDTO.getCreatedby());
			projectCC.setCreatedDate(projectCCDTO.getCreateddate());
			projectCC.setUpdatedBy(projectCCDTO.getUpdatedby());
			projectCC.setUpdatedDate(projectCCDTO.getUpdateddate());
			projectCCList.add(projectCC);
		}
		
		return projectCCList;
	}
	
	
	@Override
	public List<IProjectConceptCollection> getProjectConceptCollectionList(ConceptCollectionDTO ccDTO, IConceptCollection conceptCollection) throws QuadrigaStorageException{
		List<IProjectConceptCollection> projectDictionaryList = null;

		List<ProjectConceptCollectionDTO> projectConceptCollectionDTOList = ccDTO.getProjConceptCollectionDTOList();
		if(projectConceptCollectionDTOList != null){
			for(ProjectConceptCollectionDTO projectCCDTO : projectConceptCollectionDTOList){
				if(projectDictionaryList == null){
					projectDictionaryList = new ArrayList<IProjectConceptCollection>();
				}
				ProjectDTO projectDTO = projectCCDTO.getProjectDTO();
				IProject project = projectShallowMapper.getProjectDetails(projectDTO);
				IProjectConceptCollection projectDictionary = projectCCFactory.createProjectConceptCollectionObject();
				projectDictionary.setProject(project);
				projectDictionary.setConceptCollection(conceptCollection);
				projectDictionary.setCreatedBy(projectCCDTO.getCreatedby());
				projectDictionary.setCreatedDate(projectCCDTO.getCreateddate());
				projectDictionary.setUpdatedBy(projectCCDTO.getUpdatedby());
				projectDictionary.setUpdatedDate(projectCCDTO.getUpdateddate());
				projectDictionaryList.add(projectDictionary);
				
			}
		}


		return projectDictionaryList;
	}


}
