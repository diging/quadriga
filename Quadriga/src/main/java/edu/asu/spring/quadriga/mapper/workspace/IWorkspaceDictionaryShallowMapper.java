package edu.asu.spring.quadriga.mapper.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDictionaryShallowMapper {
	public abstract List<IWorkspaceDictionary> getWorkspaceDictionaryList(IWorkSpace workspace,WorkspaceDTO workspaceDTO) throws QuadrigaStorageException;

	List<IWorkspaceDictionary> getWorkspaceDictionaryList(
			IDictionary dictionary, DictionaryDTO dictionaryDTO)
			throws QuadrigaStorageException;


}
