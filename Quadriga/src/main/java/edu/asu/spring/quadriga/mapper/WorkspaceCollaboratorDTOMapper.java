package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class WorkspaceCollaboratorDTOMapper extends DAOConnectionManager
{
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCollaboratorDTOMapper.class);
	
	public WorkspaceCollaboratorDTO getWorkspaceCollaborator(WorkspaceDTO workspace,String userName,String collaboratorRole) throws QuadrigaStorageException
	{
		WorkspaceCollaboratorDTO collaborator = null;
		WorkspaceCollaboratorDTOPK collaboratorKey = null;
		try
		{
			collaboratorKey = new WorkspaceCollaboratorDTOPK(workspace.getWorkspaceid(),userName,collaboratorRole);
			Date date = new Date();
			collaborator = new WorkspaceCollaboratorDTO();
			collaborator.setWorkspaceCollaboratorDTOPK(collaboratorKey);
			collaborator.setWorkspaceDTO(workspace);
			collaborator.setQuadrigaUserDTO(getUserDTO(userName));
			collaborator.setCreatedby(userName);
			collaborator.setCreateddate(date);
			collaborator.setUpdatedby(userName);
			collaborator.setUpdateddate(date);
		}
		catch(HibernateException ex)
		{
			logger.error("Retieving project collaborator :",ex);
			throw new QuadrigaStorageException();
		}
		return collaborator;
	}

}
