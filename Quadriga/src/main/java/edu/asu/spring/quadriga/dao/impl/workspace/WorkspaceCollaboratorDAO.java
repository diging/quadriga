package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;

@Service
public class WorkspaceCollaboratorDAO extends BaseDAO<WorkspaceCollaboratorDTO> implements IWorkspaceCollaboratorDAO {
 
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.workspace.IWorkspaceCollaboratorDAO#deleteWorkspaceCollaboratorDTO(edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO)
     */
    @Override
    public void deleteWorkspaceCollaboratorDTO(WorkspaceCollaboratorDTO wsCollabDto) {
        deleteDTO(wsCollabDto);
    }

    @Override
    public WorkspaceCollaboratorDTO getDTO(String id) {
        return getDTO(WorkspaceCollaboratorDTO.class, id);
    }

	@Override
	public List<QuadrigaUserDTO> getUsersNotCollaborating(String dtoId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from QuadrigaUserDTO user where user.username NOT IN (Select quadrigaUserDTO.username from WorkspaceCollaboratorDTO wrkCollab where wrkCollab.workspaceDTO.workspaceid =:workspaceid)");
		query.setParameter("workspaceid", dtoId);
		
		return query.list();
	}
}
