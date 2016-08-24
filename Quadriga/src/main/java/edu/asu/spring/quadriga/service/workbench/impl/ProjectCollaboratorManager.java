package edu.asu.spring.quadriga.service.workbench.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workbench.impl.ProjectDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;

@Service
@Transactional
public class ProjectCollaboratorManager
        extends CollaboratorManager<ProjectCollaboratorDTO, ProjectCollaboratorDTOPK, ProjectDTO, ProjectDAO>
        implements IProjectCollaboratorManager {

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private IProjectDeepMapper projectDeepMapper;

    @Autowired
    private IProjectCollaboratorDAO projectCollabDAO;

    @Autowired
    private IProjectDAO projectDao;

    /**
     * This method retrieves the collaborators associated with the project
     * 
     * @param projectId
     *            - project id
     * @return List<ICollaborator> - list of collaborators associated with the
     *         project.
     * @throws QuadrigaStorageException
     */
    @Override
    public List<IProjectCollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException {
        List<IProjectCollaborator> projectCollaboratorList = null;
        // retrieve the collaborators associated with project

        ProjectDTO projectDto = projectDao.getDTO(projectId);
        IProject project = projectDeepMapper.getProject(projectDto);
        if (project != null) {
            projectCollaboratorList = project.getProjectCollaborators();
        }

        // map the collaborators to UI XML values
        if (projectCollaboratorList != null) {
            for (IProjectCollaborator projectCollaborator : projectCollaboratorList) {
                if (projectCollaborator.getCollaborator() != null
                        && projectCollaborator.getCollaborator().getCollaboratorRoles() != null) {
                    for (IQuadrigaRole collaboratorRole : projectCollaborator.getCollaborator()
                            .getCollaboratorRoles()) {
                        roleManager.fillQuadrigaRole(IQuadrigaRoleManager.PROJECT_ROLES, collaboratorRole);
                    }
                }
            }
        }
        return projectCollaboratorList;
    }

    @Override
    public ProjectCollaboratorDTO createNewCollaboratorDTO() {
        return new ProjectCollaboratorDTO();
    }

    @Override
    public ProjectCollaboratorDTOPK createNewCollaboratorDTOPK(String id, String collabUser, String role) {
        return new ProjectCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<ProjectDTO> getDao() {
        return projectDao;
    }

    @Override
    public ICollaboratorDAO<ProjectCollaboratorDTO> getCollaboratorDao() {
        return projectCollabDAO;
    }
}