package edu.asu.spring.quadriga.dao.impl.workbench.passthroughproject;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PassThroughProjectDTOMapper;
import edu.asu.spring.quadriga.service.IUserManager;

@Repository
public class PassThroughProjectDAO extends BaseDAO<PassThroughProjectDTO> implements IPassThroughProjectDAO {

    @Autowired
    private PassThroughProjectDTOMapper projectMapper;

    @Autowired
    private IUserManager userManager;

    @Override
    public PassThroughProjectDTO getDTO(String id) {
        return getDTO(PassThroughProjectDTO.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String addPassThroughProject(String userid, IPassThroughProject project) throws QuadrigaStorageException {
        String projectId = generateUniqueID();

        IUser user = userManager.getUser(userid);

        Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
        query.setParameter("username", userid);

        List<QuadrigaUserDTO> quadrigaUsers = query.list();

        PassThroughProjectDTO projectDTO = projectMapper.getPassThroughProjectDTO(project, user);
        projectDTO.setProjectid(projectId);
        projectDTO.setProjectowner(quadrigaUsers.get(0));

        saveNewDTO(projectDTO);

        return projectId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PassThroughProjectDTO> getExternalProjects(String externalProjectid) throws QuadrigaStorageException {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("PassThroughProjectDTO.findByExternalProjectid");
        query.setParameter("externalProjectid", externalProjectid);

        List<PassThroughProjectDTO> projectDTOs = query.list();
        return projectDTOs;
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("project_id.prefix");
    }
}
