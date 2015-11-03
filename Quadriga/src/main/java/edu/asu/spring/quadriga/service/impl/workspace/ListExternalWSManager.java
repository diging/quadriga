package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workspace.IListExternalWsDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.mapper.IListExternalWSManager;

@Service
public class ListExternalWSManager implements IListExternalWSManager {

    @Autowired
    private IListExternalWsDAO dbConnect;

    public boolean isExternalWorkspaceExists(String externalWorkspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException {
        return dbConnect.isExternalWorkspaceExists(externalWorkspaceId);
    }

    @Override
    public void createExternalWorkspace(String externalWorkspaceId,String externalWorkspaceName, String workspaceId,String projectId,IUser user) {
        dbConnect.createExternalWorkspace(externalWorkspaceId, externalWorkspaceName, workspaceId,projectId,user);
    }

    @Override
    public String getInternalWorkspaceId(String externalWorkspaceId) {
        return dbConnect.getInternalWorkspaceId(externalWorkspaceId);
    }

}
