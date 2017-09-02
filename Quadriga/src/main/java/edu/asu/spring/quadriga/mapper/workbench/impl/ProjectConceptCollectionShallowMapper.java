package edu.asu.spring.quadriga.mapper.workbench.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service
public class ProjectConceptCollectionShallowMapper implements IProjectConceptCollectionShallowMapper {
   
    @Autowired
    private IConceptCollectionManager ccManager;

    @Autowired
    private IProjectShallowMapper projectShallowMapper;

    @Override
    public List<IConceptCollection> getConceptCollections(IProject project,
            List<ProjectConceptCollectionDTO> projectCCDTOList) throws QuadrigaStorageException {
        List<IConceptCollection> ccList = new ArrayList<IConceptCollection>();

        for (ProjectConceptCollectionDTO projectCCDTO : projectCCDTOList) {
            IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
            ccProxy.setConceptCollectionId(projectCCDTO.getConceptCollection().getConceptCollectionid());
            ccProxy.setConceptCollectionName(projectCCDTO.getConceptCollection().getCollectionname());
            ccProxy.setDescription(projectCCDTO.getConceptCollection().getDescription());
            ccProxy.setCreatedBy(projectCCDTO.getConceptCollection().getCreatedby());
            ccProxy.setCreatedDate(projectCCDTO.getConceptCollection().getCreateddate());
            ccProxy.setUpdatedBy(projectCCDTO.getConceptCollection().getUpdatedby());
            ccProxy.setUpdatedDate(projectCCDTO.getConceptCollection().getUpdateddate());
            ccList.add(ccProxy);
        }

        return ccList;
    }

    @Override
    public List<IProject> getProjectConceptCollectionList(ConceptCollectionDTO ccDTO,
            IConceptCollection conceptCollection) throws QuadrigaStorageException {
        List<IProject> projectList = new ArrayList<IProject>();

        List<ProjectConceptCollectionDTO> projectConceptCollectionDTOList = ccDTO.getProjConceptCollectionDTOList();
        if (projectConceptCollectionDTOList == null) {
            return projectList;
        }
        
        for (ProjectConceptCollectionDTO projectCCDTO : projectConceptCollectionDTOList) {
            IProject project = projectShallowMapper.getProjectDetails(projectCCDTO.getProjectDTO());
            projectList.add(project);
        }

        return projectList;
    }

}
