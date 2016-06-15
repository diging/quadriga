package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;

@Repository
@Transactional
public class ProjectDAO extends BaseDAO<ProjectDTO> implements IProjectDAO {
    
    @Resource(name = "projectconstants")
    private Properties messages;

    @Override
    public ProjectDTO getProjectDTO(String id) {
       return getDTO(ProjectDTO.class, id);
    }

    @Override
    public ProjectDTO getDTO(String id) {
        return getProjectDTO(id);
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("project_id.prefix");
    }
    
}
