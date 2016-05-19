package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;

/**
 * Class implements {@link IArchiveWSManager} 
 * for archiving and deactivating workspace associated with project.
 * @implements IArchiveWSManager
 * @author Julia Damerow, Kiran Kumar Batna
 */
@Service
public class ArchiveWSManager extends BaseWSManager implements IArchiveWSManager 
{

	/**
	 * This will archive the requested workspace.[archive = 1 is supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Julia Damerow, Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void archiveWorkspace(String workspaceIdList,String wsUser) {
		List<String> wsIds = Arrays.asList(workspaceIdList.split(","));
		for (String id : wsIds) {
    		WorkspaceDTO wsDto = workspaceDao.getDTO(id.trim());
    		wsDto.setIsarchived(true);
    		wsDto.setUpdatedby(wsUser);
    		wsDto.setUpdateddate(new Date());
    		workspaceDao.updateDTO(wsDto);
		}
	}
	
	/**
	 * This will activate the requested archived workspace.[archive = 0 will be supplied to database.]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Julia Damerow, Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void unArchiveWorkspace(String workspaceIdList,String wsUser) {
	    List<String> wsIds = Arrays.asList(workspaceIdList.split(","));
        for (String id : wsIds) {
            WorkspaceDTO wsDto = workspaceDao.getDTO(id.trim());
            wsDto.setIsarchived(false);
            wsDto.setUpdatedby(wsUser);
            wsDto.setUpdateddate(new Date());
            workspaceDao.updateDTO(wsDto);
        }
	}
	
	/**
	 * This will deactivate the requested workspace.[deactivate = 1 will be supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Julia Damerow, Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void deactivateWorkspace(String workspaceIdList,String wsUser) {
	    List<String> wsIds = Arrays.asList(workspaceIdList.split(","));
        for (String id : wsIds) {
            WorkspaceDTO wsDto = workspaceDao.getDTO(id.trim());
            wsDto.setIsdeactivated(true);
            wsDto.setUpdatedby(wsUser);
            wsDto.setUpdateddate(new Date());
            workspaceDao.updateDTO(wsDto);
        }
	}
	
	/**
	 * This will activate the requested deactivated workspace.
	 * [deactivate = 0 will be supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Julia Damerow, Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void activateWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
	    List<String> wsIds = Arrays.asList(workspaceIdList.split(","));
        for (String id : wsIds) {
            WorkspaceDTO wsDto = workspaceDao.getDTO(id.trim());
            wsDto.setIsdeactivated(false);
            wsDto.setUpdatedby(wsUser);
            wsDto.setUpdateddate(new Date());
            workspaceDao.updateDTO(wsDto);
        }
	}

}
