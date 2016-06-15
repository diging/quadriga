package edu.asu.spring.quadriga.dao.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectEditorDAO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;

@Service
public class ProjectEditorDAO extends BaseDAO<ProjectEditorDTO> implements IProjectEditorDAO {

    @Override
    public ProjectEditorDTO getProjectEditorDTO(ProjectEditorDTOPK primaryKey) {
        return getDTO(ProjectEditorDTO.class, primaryKey);
    }

    @Override
    public ProjectEditorDTO getDTO(String id) {
       return getDTO(ProjectEditorDTO.class, id);
    }
}
