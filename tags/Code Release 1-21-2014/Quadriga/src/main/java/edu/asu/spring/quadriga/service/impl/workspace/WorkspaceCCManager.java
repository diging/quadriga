package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceCC;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;

@Service
public class WorkspaceCCManager implements IWorkspaceCCManager {

	@Autowired
	private IDBConnectionWorkspaceCC dbConnect;
	
	@Override
	@Transactional
	public String addWorkspaceCC(String workspaceId, String CCId, String userId)
			throws QuadrigaStorageException {
		String msg=dbConnect.addWorkspaceCC(workspaceId, CCId, userId);
		return msg;
	}

	@Override
	@Transactional
	public List<IConceptCollection> listWorkspaceCC(String workspaceId,
			String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptConnectionList = dbConnect.listWorkspaceCC(workspaceId, userId);
		return conceptConnectionList;
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
