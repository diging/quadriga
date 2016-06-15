package edu.asu.spring.quadriga.dao.workbench.passthroughproject;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectDAO extends IBaseDAO<PassThroughProjectDTO> {

    /**
     * This method returns the PassThroughProjectDTO objects for a given
     * external project id and the corresponding client.
     * 
     * @param externalProjectid The id a project has in a client application.
     * @param client The client application.
     * @return
     * @throws QuadrigaStorageException
     */
    PassThroughProjectDTO getExternalProject(String externalProjectid, String client) throws QuadrigaStorageException;

}