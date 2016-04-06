package edu.asu.spring.quadriga.dao.workbench.passthroughproject;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectDAO extends IBaseDAO<PassThroughProjectDTO> {

    String addPassThroughProject(String userid, IPassThroughProject project) throws QuadrigaStorageException;

    List<PassThroughProjectDTO> getExternalProjects(String externalProjectid) throws QuadrigaStorageException;

}