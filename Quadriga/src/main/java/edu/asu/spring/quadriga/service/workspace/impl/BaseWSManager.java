package edu.asu.spring.quadriga.service.workspace.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;

public class BaseWSManager {

    @Autowired 
    protected IWorkspaceDAO workspaceDao;
    
    @Autowired 
    protected IWorkspaceDeepMapper mapper;
    
}
