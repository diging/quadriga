package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.proxy.ConceptCollectionProxy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
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
	private IRetrieveProjectDAO dbConnect;
	
	@Autowired
	private IConceptCollectionManager ccManager;
	
	@Autowired
	private IWorkspaceConceptCollectionFactory wsCCFactory;
	
	@Autowired
	private IWorkspaceShallowMapper workspaceShallowMapper;


	@Override
	public List<IWorkspaceConceptCollection> getWorkspaceCCList(
			IWorkSpace workspace,WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
		List<IWorkspaceConceptCollection> workspaceCCList = null;
		if(workspace != null){
			workspaceCCList = new ArrayList<IWorkspaceConceptCollection>();
			List<WorkspaceConceptcollectionDTO> wsCCDTOList = workspaceDTO.getWorkspaceConceptCollectionDTOList();
			if(wsCCDTOList != null){
				for(WorkspaceConceptcollectionDTO wsCCDTO : wsCCDTOList){
					IConceptCollection ccProxy = new ConceptCollectionProxy(ccManager);
					ccProxy.setConceptCollectionId(wsCCDTO.getConceptCollectionDTO().getConceptCollectionid());
					ccProxy.setConceptCollectionName(wsCCDTO.getConceptCollectionDTO().getCollectionname());
					ccProxy.setDescription(wsCCDTO.getConceptCollectionDTO().getDescription());
					ccProxy.setCreatedBy(wsCCDTO.getConceptCollectionDTO().getCreatedby());
					ccProxy.setCreatedDate(wsCCDTO.getConceptCollectionDTO().getCreateddate());
					ccProxy.setUpdatedBy(wsCCDTO.getConceptCollectionDTO().getUpdatedby());
					ccProxy.setUpdatedDate(wsCCDTO.getConceptCollectionDTO().getUpdateddate());
					IWorkspaceConceptCollection wsCC = wsCCFactory.createWorkspaceConceptCollectionObject();
					wsCC.setWorkspace(workspace);
					wsCC.setConceptCollection(ccProxy);
					wsCC.setCreatedBy(wsCCDTO.getCreatedby());
					wsCC.setCreatedDate(wsCCDTO.getCreateddate());
					wsCC.setUpdatedBy(wsCCDTO.getUpdatedby());
					wsCC.setUpdatedDate(wsCCDTO.getUpdateddate());
					workspaceCCList.add(wsCC);
				}
			}
		}
		return workspaceCCList;
	}
	
	
	@Override
	public List<IWorkspaceConceptCollection> getWorkspaceConceptCollectionList(
			IConceptCollection conceptCollection, ConceptCollectionDTO  ccDTO)
					throws QuadrigaStorageException {
		List<IWorkspaceConceptCollection> workspaceConceptCollectionList = null;
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList = ccDTO.getWsConceptCollectionDTOList();
		if(workspaceConceptCollectionDTOList != null){
			for( WorkspaceConceptcollectionDTO workspaceConceptCollectionDTO : workspaceConceptCollectionDTOList){
				if(workspaceConceptCollectionList == null){
					workspaceConceptCollectionList = new ArrayList<IWorkspaceConceptCollection>();
				}

				IWorkSpace workspace = workspaceShallowMapper.mapWorkspaceDTO(workspaceConceptCollectionDTO.getWorkspaceDTO());
				IWorkspaceConceptCollection wsCocneptCollection = wsCCFactory.createWorkspaceConceptCollectionObject();
				wsCocneptCollection.setWorkspace(workspace);
				wsCocneptCollection.setConceptCollection(conceptCollection);
				wsCocneptCollection.setCreatedBy(workspaceConceptCollectionDTO.getCreatedby());
				wsCocneptCollection.setCreatedDate(workspaceConceptCollectionDTO.getCreateddate());
				wsCocneptCollection.setUpdatedBy(workspaceConceptCollectionDTO.getUpdatedby());
				wsCocneptCollection.setUpdatedDate(workspaceConceptCollectionDTO.getUpdateddate());
				workspaceConceptCollectionList.add(wsCocneptCollection);

			}
		}

		return workspaceConceptCollectionList;
	}


}
