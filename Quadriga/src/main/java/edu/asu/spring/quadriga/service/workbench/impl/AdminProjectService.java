package edu.asu.spring.quadriga.service.workbench.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.AdminRequiredException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IAdminProjectService;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class AdminProjectService implements IAdminProjectService {

    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;

    @Autowired
    private IQuadrigaRoleManager rolemanager;

    @Autowired
    private IUserManager userManager;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.service.workbench.impl.IAdminProjectService#addAdmin(
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void addAdmin(String projectId, String newAdmin, String changedBy) throws QuadrigaStorageException, AdminRequiredException {
        String projAdminDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.PROJECT_ROLES,
                RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN);
        IUser changedByUser = userManager.getUser(changedBy);
        if (!changedByUser.getQuadrigaRoles().contains(
                rolemanager.getQuadrigaRoleById(IQuadrigaRoleManager.MAIN_ROLES, RoleNames.ROLE_QUADRIGA_ADMIN))) {
            throw new AdminRequiredException("This action can only be done by an administrator.");
        }
        projectCollaboratorManager.addCollaborator(changedBy, projAdminDBId, projectId, changedBy);
    }
}
