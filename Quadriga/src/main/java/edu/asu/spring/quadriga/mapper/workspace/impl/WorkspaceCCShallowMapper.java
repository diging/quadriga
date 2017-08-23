package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceCCShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service
public class WorkspaceCCShallowMapper implements IWorkspaceCCShallowMapper {
   
    @Autowired
    private IConceptCollectionManager ccManager;

    @Autowired
    private IWorkspaceShallowMapper workspaceShallowMapper;

    @Override
    public List<IConceptCollection> getConceptCollections(IWorkspace workspace, WorkspaceDTO workspaceDTO)
            throws QuadrigaStorageException {
        List<IConceptCollection> conceptCollections = new ArrayList<IConceptCollection>();
        if (workspace != null) {
            List<WorkspaceConceptcollectionDTO> wsCCDTOList = workspaceDTO.getWorkspaceConceptCollectionDTOList();
            if (wsCCDTOList != null) {
                for (WorkspaceConceptcollectionDTO wsCCDTO : wsCCDTOList) {
                    IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
                    ccProxy.setConceptCollectionId(wsCCDTO.getConceptCollectionDTO().getConceptCollectionid());
                    ccProxy.setConceptCollectionName(wsCCDTO.getConceptCollectionDTO().getCollectionname());
                    ccProxy.setDescription(wsCCDTO.getConceptCollectionDTO().getDescription());
                    ccProxy.setCreatedBy(wsCCDTO.getConceptCollectionDTO().getCreatedby());
                    ccProxy.setCreatedDate(wsCCDTO.getConceptCollectionDTO().getCreateddate());
                    ccProxy.setUpdatedBy(wsCCDTO.getConceptCollectionDTO().getUpdatedby());
                    ccProxy.setUpdatedDate(wsCCDTO.getConceptCollectionDTO().getUpdateddate());
                    conceptCollections.add(ccProxy);
                }
            }
        }
        return conceptCollections;
    }

    @Override
    public List<IWorkspace> getWorkspaces(IConceptCollection conceptCollection,
            ConceptCollectionDTO ccDTO) throws QuadrigaStorageException {
        List<IWorkspace> workspaces = new ArrayList<IWorkspace>();
        List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOs = ccDTO.getWsConceptCollectionDTOList();
        
        if (workspaceConceptCollectionDTOs != null) {
            for (WorkspaceConceptcollectionDTO workspaceConceptCollectionDTO : workspaceConceptCollectionDTOs) {
                IWorkspace workspace = workspaceShallowMapper
                        .mapWorkspaceDTO(workspaceConceptCollectionDTO.getWorkspaceDTO());
                workspaces.add(workspace);
            }
        }

        return workspaces;
    }

}
