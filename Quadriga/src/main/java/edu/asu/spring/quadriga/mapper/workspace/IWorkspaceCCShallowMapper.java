package edu.asu.spring.quadriga.mapper.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceCCShallowMapper {
	public abstract List<IWorkspaceConceptCollection> getWorkspaceCCList(IWorkSpace workspace,WorkspaceDTO workspaceDTO) throws QuadrigaStorageException;

	public abstract List<IWorkspaceConceptCollection> getWorkspaceConceptCollectionList(IConceptCollection conceptCollection, ConceptCollectionDTO  ccDTO) throws QuadrigaStorageException;

}
