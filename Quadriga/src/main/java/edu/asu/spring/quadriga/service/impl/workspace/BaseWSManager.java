package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;

public class BaseWSManager {

    @Autowired IWorkspaceDAO workspaceDao;
    
    @Autowired IWorkspaceDeepMapper mapper;
    
    public IWorkSpace getWorkspace(String wsId) throws QuadrigaStorageException {
        return mapper.getWorkSpaceDetails(wsId);
    }
}
