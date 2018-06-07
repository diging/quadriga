package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.exceptions.AdminRequiredException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IAdminProjectService {

    void addAdmin(String projectId, String newAdmin, String changedBy)
            throws QuadrigaStorageException, AdminRequiredException;

}