package edu.asu.spring.quadriga.mapper.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDictionaryShallowMapper {
    public abstract List<IDictionary> getDictionaries(IWorkspace workspace, WorkspaceDTO workspaceDTO)
            throws QuadrigaStorageException;

    List<IWorkspace> getWorkspaces(IDictionary dictionary, DictionaryDTO dictionaryDTO)
            throws QuadrigaStorageException;

}
