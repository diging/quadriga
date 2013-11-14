package edu.asu.spring.quadriga.dao.workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@Repository
public class ModifyWSManagerDAOImpl extends DAOConnectionManager implements IModifyWSManagerDAO {

	@Autowired
	WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	SessionFactory sessionFactory;
	

	private static final Logger logger = LoggerFactory.getLogger(ModifyWSManagerDAOImpl.class);
	
	/**
	 * This adds a workspace record into the database.
	 * @param  workSpace
	 * @return errmsg - blank when success and error message on failure.
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addWorkSpaceRequest(IWorkSpace workSpace,String projectId) throws QuadrigaStorageException
	{
		try
		{
			WorkspaceDTO workspaceDTO = workspaceDTOMapper.getWorkspaceDTO(workSpace);
			workspaceDTO.setWorkspaceid(generateUniqueID());
			
			List<ProjectWorkspaceDTO> projectWorkspaceList = workspaceDTO.getProjectWorkspaceDTOList();
			
			ProjectWorkspaceDTO projectWorkspaceDTO = new ProjectWorkspaceDTO();
			//projectWorkspaceDTO.setProjectWorkspaceDTOPK(new ProjectWorkspaceDTOPK(projectId, workspaceDTO.getWorkspaceid()));
			projectWorkspaceDTO.setWorkspaceDTO(workspaceDTO);
			projectWorkspaceDTO.setProjectDTO((ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId));
			projectWorkspaceDTO.setCreatedby(workspaceDTO.getCreatedby());
			projectWorkspaceDTO.setCreateddate(new Date());
			projectWorkspaceDTO.setUpdatedby(workspaceDTO.getUpdatedby());
			projectWorkspaceDTO.setUpdateddate(new Date());
			
			if(projectWorkspaceList == null)
			{
				projectWorkspaceList = new ArrayList<ProjectWorkspaceDTO>();
			}
			projectWorkspaceList.add(projectWorkspaceDTO);
			sessionFactory.getCurrentSession().save(workspaceDTO);
		}
		catch(Exception e)
		{
			logger.error("addWorkSpaceRequest method :",e);
        	throw new QuadrigaStorageException();
		}
	}

}
