package edu.asu.spring.quadriga.service.workspace.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceDictionaryFactory;
import edu.asu.spring.quadriga.domain.proxy.DictionaryProxy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDictionaryShallowMapper;

@Service
public class WorkspaceDictionaryShallowMapper implements
		IWorkspaceDictionaryShallowMapper {
	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IRetrieveDictionaryManager dictionaryManager;
	
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
					IDictionary dictionaryProxy = new DictionaryProxy(dictionaryManager);
					dictionaryProxy.setDictionaryId(wsDictionaryDTO.getDictionaryDTO().getDictionaryid());
					dictionaryProxy.setDictionaryName(wsDictionaryDTO.getDictionaryDTO().getDictionaryname());
					dictionaryProxy.setDescription(wsDictionaryDTO.getDictionaryDTO().getDescription());
					dictionaryProxy.setCreatedBy(wsDictionaryDTO.getDictionaryDTO().getCreatedby());
					dictionaryProxy.setCreatedDate(wsDictionaryDTO.getDictionaryDTO().getCreateddate());
					dictionaryProxy.setUpdatedBy(wsDictionaryDTO.getDictionaryDTO().getUpdatedby());
					dictionaryProxy.setUpdatedDate(wsDictionaryDTO.getDictionaryDTO().getUpdateddate());
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

}
