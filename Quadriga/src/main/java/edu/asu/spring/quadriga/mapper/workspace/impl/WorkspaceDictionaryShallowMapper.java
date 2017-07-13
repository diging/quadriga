package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.dictionary.IDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDictionaryShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;

@Service
public class WorkspaceDictionaryShallowMapper implements IWorkspaceDictionaryShallowMapper {
    
    @Autowired
    private IDictionaryShallowMapper dictionaryShallowMapper;

    @Autowired
    private IWorkspaceShallowMapper workspaceShallowMapper;

    @Override
    public List<IDictionary> getDictionaries(IWorkspace workspace, WorkspaceDTO workspaceDTO)
            throws QuadrigaStorageException {
        List<IDictionary> dictionaries = new ArrayList<IDictionary>();
        
        if (workspace != null) {
            List<WorkspaceDictionaryDTO> wsDictionaryDTOList = workspaceDTO.getWorkspaceDictionaryDTOList();
            if (wsDictionaryDTOList != null) {
                for (WorkspaceDictionaryDTO wsDictionaryDTO : wsDictionaryDTOList) {
                    IDictionary dictionaryProxy = dictionaryShallowMapper
                            .getDictionaryDetails(wsDictionaryDTO.getDictionaryDTO());
                    dictionaries.add(dictionaryProxy);
                }
            }
        }
        return dictionaries;
    }

    @Override
    public List<IWorkspace> getWorkspaces(IDictionary dictionary, DictionaryDTO dictionaryDTO)
            throws QuadrigaStorageException {
        List<IWorkspace> workspaces = new ArrayList<IWorkspace>();
        List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList = dictionaryDTO.getWsDictionaryDTOList();
        
        if (workspaceDictionaryDTOList != null) {
            for (WorkspaceDictionaryDTO workspaceDictionaryDTO : workspaceDictionaryDTOList) {
                IWorkspace workspace = workspaceShallowMapper.mapWorkspaceDTO(workspaceDictionaryDTO.getWorkspaceDTO());
                workspaces.add(workspace);
            }
        }

        return workspaces;
    }

}
