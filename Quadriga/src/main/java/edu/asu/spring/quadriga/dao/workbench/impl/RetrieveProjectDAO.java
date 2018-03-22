package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class RetrieveProjectDAO extends BaseDAO<ProjectDTO> implements IRetrieveProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(RetrieveProjectDAO.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectDTO> getProjectDTOList(String sUserName) throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    " from ProjectDTO project where project.projectowner.username =:username");
            query.setParameter("username", sUserName);
            projectDTOList = query.list();
        } catch (HibernateException e) {
            logger.info("getProjectList details method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }

    @Override
    public List<ProjectDTO> getAllProjectsDTO() throws QuadrigaStorageException{
        List<ProjectDTO> projectDTOList = null;
        try{
            Query query = sessionFactory.getCurrentSession().createQuery("from ProjectDTO");
            projectDTOList = query.list();
        }catch(HibernateException e){
            logger.info("getProjectDTOList method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }
    
    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getCollaboratorProjectDTOListOfUser(String sUserName) throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "Select distinct projectCollab.projectDTO from ProjectCollaboratorDTO projectCollab where projectCollab.quadrigaUserDTO.username =:username");
            query.setParameter("username", sUserName);
            projectDTOList = query.list();
        } catch (Exception e) {
            logger.info("getCollaboratorProjectList method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getProjectDTOListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO.workspaceowner.username = :username group by projWork.projectDTO.projectid");
            query.setParameter("username", sUserName);
            projectDTOList = query.list();
        } catch (Exception e) {
            logger.info("getCollaboratorProjectList method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getProjectDTOListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.collaboratorDTOPK.collaboratoruser = :collaboratoruser)");
            query.setParameter("collaboratoruser", sUserName);
            projectDTOList = query.list();

        } catch (Exception e) {
            logger.info("getProjectListAsWorkspaceCollaborator method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getProjectDTOListByCollaboratorRole(String sUserName, String collaboratorRole)
            throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.quadrigaUserDTO.username = :username )");
            query.setParameter("username", sUserName);
            projectDTOList = query.list();

        } catch (Exception e) {
            logger.info("getCollaboratorProjectList method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
        return projectDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDTO getProjectDTOByUnixName(String unixName) throws QuadrigaStorageException {

        Query query = sessionFactory.getCurrentSession().createQuery(
                " from ProjectDTO project where project.unixname = :unixname");
        query.setParameter("unixname", unixName);
        ProjectDTO projectDTO = (ProjectDTO) query.uniqueResult();

        return projectDTO;
    }

    @Override
    public ProjectDTO getDTO(String id) {
        return getDTO(ProjectDTO.class, id);
    }

    /**
     * 
     * This method fetches all the projects with the given accessibility
     * 
     * Uses Hibernate to get {@link ProjectDTO} of a {@link IProject} ID.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getAllProjectsDTOByAccessibility(String accessibility) throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from ProjectDTO project where project.accessibility = :accessibility");
            query.setParameter("accessibility", accessibility);
            projectDTOList = query.list();
            return projectDTOList;
        } catch (HibernateException e) {
            logger.info("getAllProjectsDTO By Accessibility method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * 
     * This method fetches all the projects that contain the given search term
     * and accessibility
     * 
     * Uses Hibernate to get {@link ProjectDTO} of a {@link IProject} ID.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDTO> getAllProjectsDTOBySearchTermAndAccessiblity(String searchTerm, String accessibility)
            throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = null;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from ProjectDTO project where project.accessibility=:accessibility AND "
                            + "(project.description like :searchTerm " + " OR "
                            + "project.projectname like :searchTerm )");
            query.setParameter("accessibility", accessibility);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            projectDTOList = query.list();
            return projectDTOList;
        } catch (HibernateException e) {
            logger.error("getAllProjectsDTO By SearchTerm And Accessiblity method :" + e.getMessage());
            throw new QuadrigaStorageException(e);
        }
    }
}
