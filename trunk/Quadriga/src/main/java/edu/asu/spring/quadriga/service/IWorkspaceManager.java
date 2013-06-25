package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * @description  : Interface class that places restraints on the WorkspaceManager
 *                 class to implement the required behaviors.
 * 
 * @author       : Kiran Kumar Batna
 *
 */
public interface IWorkspaceManager 
{

	public abstract String addNewWorkspace(IWorkSpace workSpace,int projectId)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(int projectid)
			throws QuadrigaStorageException;

}
