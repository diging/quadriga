package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IRetrieveWSCollabDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

@Repository
public class RetrieveWSCollabDAO extends BaseDAO<WorkspaceCollaboratorDTO> implements IRetrieveWSCollabDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceCollaboratorDTOMapper wsCollaboratorMapper;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	private static final Logger logger = LoggerFactory.getLogger(RetrieveWSCollabDAO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IQuadrigaRole> getCollaboratorDBRoleIdList(
			String collabRoles) {
		String[] roleList;
		List<IQuadrigaRole> collaboratorRole;
		IQuadrigaRole role;
		
		collaboratorRole = new ArrayList<IQuadrigaRole>();
		roleList = collabRoles.split(",");
		
		for(String dbRoleId : roleList)
		{
			role = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.WORKSPACE_ROLES, dbRoleId);
			collaboratorRole.add(role);
		}
		return collaboratorRole;
	}

    @Override
    public WorkspaceCollaboratorDTO getDTO(String id) {
        return getDTO(WorkspaceCollaboratorDTO.class, id);
    }
}
