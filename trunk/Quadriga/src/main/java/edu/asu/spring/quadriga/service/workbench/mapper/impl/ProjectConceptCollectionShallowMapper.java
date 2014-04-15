package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectConceptCollectionShallowMapper;

@Service
public class ProjectConceptCollectionShallowMapper implements
IProjectConceptCollectionShallowMapper {
	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IConceptCollectionManager ccManager;

	@Autowired
	private IProjectConceptCollectionFactory projectCCFactory;

	@Override
	public List<IProjectConceptCollection> getProjectConceptCollectionList(
			IProject project, ProjectDTO projectDTO)
					throws QuadrigaStorageException {
		List<IProjectConceptCollection> projectCCList = null;
		if(project != null){
			projectCCList = new ArrayList<IProjectConceptCollection>();
			List<ProjectConceptCollectionDTO> projectCCDTOList = projectDTO.getProjectConceptCollectionDTOList();
			if(projectCCDTOList != null){
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
			}
		}
		return projectCCList;
	}


}
