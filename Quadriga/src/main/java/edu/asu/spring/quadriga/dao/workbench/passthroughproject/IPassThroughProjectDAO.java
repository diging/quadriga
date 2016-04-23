package edu.asu.spring.quadriga.dao.workbench.passthroughproject;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectDAO extends IBaseDAO<PassThroughProjectDTO> {

    /**
     * This method adds a new pass through project entry in the database.
     * 
     * @param projectDTO
     * @throws QuadrigaStorageException
     */
    void addPassThroughProject(PassThroughProjectDTO projectDTO) throws QuadrigaStorageException;

    /**
     * This method returns a list of PassThroughProjectDTO objects for a given
     * external project id.
     * 
     * @param externalProjectid
     * @return
     * @throws QuadrigaStorageException
     */
    List<PassThroughProjectDTO> getExternalProjects(String externalProjectid) throws QuadrigaStorageException;

}