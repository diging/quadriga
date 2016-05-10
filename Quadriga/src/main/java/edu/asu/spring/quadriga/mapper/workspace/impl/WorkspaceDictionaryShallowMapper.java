package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceDictionaryFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryShallowMapper;

@Service
public class WorkspaceDictionaryShallowMapper implements IWorkspaceDictionaryShallowMapper {
	@Autowired
	private IRetrieveProjectDAO dbConnect;

	@Autowired
	private IDictionaryManager dictionaryManager;

	@Autowired
	private IDictionaryShallowMapper dictionaryShallowMapper;

	@Autowired
	private IWorkspaceShallowMapper workspaceShallowMapper;

	@Autowired
	private IWorkspaceDictionaryFactory wsDictionaryFactory;

	@Override
	public List<IWorkspaceDictionary> getWorkspaceDictionaryList(
			IWorkSpace workspace, WorkspaceDTO workspaceDTO)
					throws QuadrigaStorageException {
		List<IWorkspaceDictionary> workspaceDictionaryList = null;
		if(workspace != null){
			workspaceDictionaryList = new ArrayList<IWorkspaceDictionary>();
			List<WorkspaceDictionaryDTO> wsDictionaryDTOList = workspaceDTO.getWorkspaceDictionaryDTOList();
			if(wsDictionaryDTOList != null){
				for(WorkspaceDictionaryDTO wsDictionaryDTO : wsDictionaryDTOList){
					IDictionary dictionaryProxy = dictionaryShallowMapper.getDictionaryDetails(wsDictionaryDTO.getDictionaryDTO());
					IWorkspaceDictionary wsDictioanry = wsDictionaryFactory.createWorkspaceDictionaryObject();
					wsDictioanry.setWorkspace(workspace);
					wsDictioanry.setDictionary(dictionaryProxy);
					wsDictioanry.setCreatedBy(wsDictionaryDTO.getCreatedby());
					wsDictioanry.setCreatedDate(wsDictionaryDTO.getCreateddate());
					wsDictioanry.setUpdatedBy(wsDictionaryDTO.getUpdatedby());
					wsDictioanry.setUpdatedDate(wsDictionaryDTO.getUpdateddate());
					workspaceDictionaryList.add(wsDictioanry);
				}
			}
		}
		return workspaceDictionaryList;
	}


	@Override
	public List<IWorkspaceDictionary> getWorkspaceDictionaryList(
			IDictionary dictionary, DictionaryDTO  dictionaryDTO)
					throws QuadrigaStorageException {
		List<IWorkspaceDictionary> workspaceDictionaryList = null;
		List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList = dictionaryDTO.getWsDictionaryDTOList();
		if(workspaceDictionaryDTOList != null){
			for( WorkspaceDictionaryDTO workspaceDictionaryDTO : workspaceDictionaryDTOList){
				if(workspaceDictionaryList == null){
					workspaceDictionaryList = new ArrayList<IWorkspaceDictionary>();
				}

				IWorkSpace workspace = workspaceShallowMapper.mapWorkspaceDTO(workspaceDictionaryDTO.getWorkspaceDTO());
				IWorkspaceDictionary wsDictioanry = wsDictionaryFactory.createWorkspaceDictionaryObject();
				wsDictioanry.setWorkspace(workspace);
				wsDictioanry.setDictionary(dictionary);
				wsDictioanry.setCreatedBy(workspaceDictionaryDTO.getCreatedby());
				wsDictioanry.setCreatedDate(workspaceDictionaryDTO.getCreateddate());
				wsDictioanry.setUpdatedBy(workspaceDictionaryDTO.getUpdatedby());
				wsDictioanry.setUpdatedDate(workspaceDictionaryDTO.getUpdateddate());
				workspaceDictionaryList.add(wsDictioanry);

			}
		}

		return workspaceDictionaryList;
	}

}
