package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCCDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceCCShallowMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;

@Service
public class WorkspaceCCManager implements IWorkspaceCCManager {

	@Autowired
	private IWorkspaceCCDAO dbConnect;
	
	@Autowired
	private IWorkspaceCCShallowMapper wsCCShallowMapper;
	
	@Autowired
	private IWorkspaceDeepMapper wsDeepMapper;
	
	@Autowired
	private IWorkspaceDAO wsDAO;
	
	@Override
	@Transactional
	public void addWorkspaceCC(String workspaceId, String CCId, String userId)
			throws QuadrigaStorageException {
		dbConnect.addWorkspaceCC(workspaceId, CCId, userId);
	}
	
	@Override
	@Transactional
	public List<IWorkspaceConceptCollection> listWorkspaceCC(String workspaceId) throws QuadrigaStorageException {
		WorkspaceDTO workspaceDTO  = wsDAO.getWorkspaceDTO(workspaceId);
		IWorkSpace workspace = wsDeepMapper.getWorkSpaceDetails(workspaceId);
		List<IWorkspaceConceptCollection> wsCCList = wsCCShallowMapper.getWorkspaceCCList(workspace, workspaceDTO);
		return wsCCList;
	}
	
	@Override
	@Transactional
	public List<IConceptCollection> getNonAssociatedWorkspaceConcepts(String workspaceId,String userId) throws QuadrigaStorageException
	{
		List<IConceptCollection> conceptCollectionList = dbConnect.getNonAssociatedWorkspaceConcepts(workspaceId,userId);
		return conceptCollectionList;
	}

	@Override
	@Transactional
	public void deleteWorkspaceCC(String workspaceId, String userId,
			String CCId) throws QuadrigaStorageException {
		dbConnect.deleteWorkspaceCC(workspaceId, userId, CCId);
	}

}
