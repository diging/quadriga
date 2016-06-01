package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;

@Service
public class ProjectConceptCollectionManager implements IProjectConceptCollectionManager {

    @Autowired
    private IProjectConceptCollectionDAO dbConnect;

    @Autowired
    private IProjectConceptCollectionShallowMapper projCCShallowMapper;

    @Autowired
    private IProjectDeepMapper projDeepMapper;

    @Autowired
    private IRetrieveProjectDAO projManager;

    @Autowired
    private IProjectDAO projectDao;

    /**
     * This method associates the concept collection with the project.
     * 
     * @param - projectId - project id
     * @param - conceptCollectionId - concept collection id
     * @param - userId - logged in user name.
     * @throws QuarigaStorageException
     */
    @Override
    @Transactional
    public void addProjectConceptCollection(String projectId, String conceptCollectionId, String userId)
            throws QuadrigaStorageException {
        dbConnect.addProjectConceptCollection(projectId, conceptCollectionId, userId);
    }

    /**
     * This method retrieves the concept collection associated with the project.
     * 
     * @param - projectId project id
     * @param - userId - logged in user name.
     * @throws QuadrigaStorageException
     * @return List<IConceptCollection> - list of concept collection associated
     *         with the project.
     */
    @Override
    @Transactional
    public List<IProjectConceptCollection> listProjectConceptCollection(String projectId)
            throws QuadrigaStorageException {

        ProjectDTO projectDTO = projManager.getDTO(projectId);
        IProject project = projDeepMapper.getProject(projectDTO);
        return projCCShallowMapper.getProjectConceptCollectionList(project,
                projectDTO.getProjectConceptCollectionDTOList());
    }

    /**
     * This method removes the association between the project and the concept
     * collection.
     * 
     * @param projectId
     *            - project id
     * @param userId
     *            - logged in user name.
     * @param conceptCollectionId
     *            - concept collection id.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void deleteProjectConceptCollection(String projectId, String userId, String conceptCollectionId)
            throws QuadrigaStorageException {
        dbConnect.deleteProjectConceptCollection(projectId, userId, conceptCollectionId);
    }
}
