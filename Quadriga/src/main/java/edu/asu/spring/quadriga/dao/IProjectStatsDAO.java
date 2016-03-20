package edu.asu.spring.quadriga.dao;


import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectStatsDAO {

    /**
     * Retrieve the count of workspace for a given user associated to
     * given project
     * 
     * @param projectid
     * @param user
     * @return - the count of workspace for a given user associated to
     *           given project.
     * @throws QuadrigaStorageException
     */
    public abstract int getCountofWorkspace(String projectid, String username)
            throws QuadrigaStorageException;
    
    public abstract int getCountofNetwork(String workspaceid, String username)
            throws QuadrigaStorageException;

}
