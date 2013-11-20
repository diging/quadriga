package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class WorkspaceDTOMapper {

	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired
    private IUserManager userManager;
	
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
	
	public IWorkSpace getWorkSpace(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException
	{
		IWorkSpace workSpace = workspaceFactory.createWorkspaceObject();
		workSpace.setName(workspaceDTO.getWorkspacename());
		workSpace.setDescription(workspaceDTO.getDescription());
		workSpace.setId(workspaceDTO.getWorkspaceid());
		workSpace.setOwner(userManager.getUserDetails(workspaceDTO.getWorkspaceowner().getUsername()));
		return workSpace;
	}
	
	public List<IWorkSpace> getWorkSpaceList(List<WorkspaceDTO> workspaceDTOList) throws QuadrigaStorageException
	{
		Iterator<WorkspaceDTO> workspaceItr = workspaceDTOList.listIterator();
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		while(workspaceItr.hasNext())
		{
			WorkspaceDTO workspaceDTO = workspaceItr.next();
			workspaceList.add(getWorkSpace(workspaceDTO));
		}
		return workspaceList;
	}
}
