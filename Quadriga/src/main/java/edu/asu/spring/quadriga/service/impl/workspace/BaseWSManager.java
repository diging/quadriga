package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;

public class BaseWSManager {

    @Autowired 
    protected IWorkspaceDAO workspaceDao;
    
    @Autowired 
    protected IWorkspaceDeepMapper mapper;
    
}
