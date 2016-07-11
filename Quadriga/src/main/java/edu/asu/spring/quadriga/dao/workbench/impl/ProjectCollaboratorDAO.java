package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectCollaboratorDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

@Repository
@Transactional
public class ProjectCollaboratorDAO extends BaseDAO<ProjectCollaboratorDTO> implements
		IProjectCollaboratorDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectCollaboratorDTOMapper projectMapper;
	
	@Autowired
    private UserDTOMapper userMapper;
    
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectCollaboratorDAO.class);
	
	/**
     * {@inheritDoc}
     */
    @Override
    public List<IUser> getProjectNonCollaborators(String projectid) 
    {
        List<IUser> user = null;
        IUser nonCollaborator = null;
        
        ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
        
        user = new ArrayList<IUser>();
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT user FROM QuadrigaUserDTO user WHERE user.username NOT IN " +
                "(SELECT collaborator.collaboratorDTOPK.collaboratoruser FROM ProjectCollaboratorDTO collaborator " +
                "  WHERE collaborator.collaboratorDTOPK.projectid =:projectid)");
        query.setParameter("projectid", projectid);
        
        @SuppressWarnings("unchecked")
        List<QuadrigaUserDTO> collaborator = query.list();
        
        for(QuadrigaUserDTO tempCollab : collaborator)
        {
            if(!project.getProjectowner().getUsername().equals(tempCollab.getUsername()))
            {
                nonCollaborator = userMapper.getUser(tempCollab);
                user.add(nonCollaborator);
            }
        
        }
        return user;
    }

    @Override
    public ProjectCollaboratorDTO getDTO(String id) {
        return getDTO(ProjectCollaboratorDTO.class, id);
    }

	@Override
	public List<QuadrigaUserDTO> getUsersNotCollaborating(String dtoId) {
		 Query query = sessionFactory.getCurrentSession().createQuery("SELECT user FROM QuadrigaUserDTO user WHERE user.username NOT IN " +
	                "(SELECT collaborator.collaboratorDTOPK.collaboratoruser FROM ProjectCollaboratorDTO collaborator " +
	                "  WHERE collaborator.collaboratorDTOPK.projectid =:projectid)");
	     query.setParameter("projectid", dtoId);
	        
	     return query.list();
	}

}
