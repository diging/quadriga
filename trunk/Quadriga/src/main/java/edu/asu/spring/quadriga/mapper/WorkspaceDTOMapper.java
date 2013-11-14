package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;

@Service
public class WorkspaceDTOMapper {

	public WorkspaceDTO getWorkspaceDTO(IWorkSpace iWorkSpace)
	{
		WorkspaceDTO workspaceDTO = new WorkspaceDTO();
		workspaceDTO.setWorkspacename(iWorkSpace.getName());
		workspaceDTO.setDescription(iWorkSpace.getDescription());
		workspaceDTO.setWorkspaceowner(new QuadrigaUserDTO(iWorkSpace.getOwner().getUserName()));
		workspaceDTO.setIsarchived(false);
		workspaceDTO.setIsdeactivated(false);
		workspaceDTO.setUpdatedby(iWorkSpace.getOwner().getUserName());
		workspaceDTO.setUpdateddate(new Date());
		workspaceDTO.setCreatedby(iWorkSpace.getOwner().getUserName());
		workspaceDTO.setCreateddate(new Date());
		return workspaceDTO;
	}
}
