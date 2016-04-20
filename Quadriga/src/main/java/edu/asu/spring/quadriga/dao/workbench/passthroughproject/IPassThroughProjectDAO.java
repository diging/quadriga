package edu.asu.spring.quadriga.dao.workbench.passthroughproject;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectDAO extends IBaseDAO<PassThroughProjectDTO> {

    void addPassThroughProject(PassThroughProjectDTO projectDTO) throws QuadrigaStorageException;

    List<PassThroughProjectDTO> getExternalProjects(String externalProjectid) throws QuadrigaStorageException;

}