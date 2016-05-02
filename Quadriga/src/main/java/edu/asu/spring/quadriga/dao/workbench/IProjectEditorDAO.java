package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;

public interface IProjectEditorDAO extends IBaseDAO<ProjectEditorDTO> {

    public abstract ProjectEditorDTO getProjectEditorDTO(ProjectEditorDTOPK id);

}