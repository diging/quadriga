package edu.asu.spring.quadriga.service.workspace.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDictionaryDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.mapper.workspace.impl.WorkspaceDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

@Service
public class WorkspaceDictionaryManager implements IWorkspaceDictionaryManager {

    @Autowired
    private IWorkspaceDictionaryDAO dbConnect;

    @Autowired
    private IWorkspaceDAO wsDao;

    @Autowired
    private WorkspaceDictionaryShallowMapper wsDictShallowMapper;

    @Autowired
    private IWorkspaceDeepMapper wsDeepMapper;

    @Autowired
    private DataSource dataSource;

    /**
     * Assigns the data source
     * 
     * @param : dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Add dictionary to the workspace
     * 
     * @param workspaceId
     * @param dictionaryId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void addWorkspaceDictionary(String workspaceId, String dictionaryId, String userId)
            throws QuadrigaStorageException {
        dbConnect.addWorkspaceDictionary(workspaceId, dictionaryId, userId);
    }

    /**
     * List the dictionary in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IDictionary> getDictionaries(String workspaceId, String userId)
            throws QuadrigaStorageException {

        // FIXME: what's up with all of this?:
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        IWorkspace workspace = wsDeepMapper.mapWorkspaceDTO(wsDto);

        WorkspaceDTO workspaceDTO = dbConnect.listWorkspaceDictionary(workspaceId, userId);

        return wsDictShallowMapper.getDictionaries(workspace, workspaceDTO);
    }

    /**
     * List the dictionaries which are not associated to a workspace for a user
     * -UserId
     * 
     * @throws QuadrigaStorageException
     * 
     */
    @Override
    @Transactional
    public List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId) throws QuadrigaStorageException {
        return dbConnect.getNonAssociatedWorkspaceDictionaries(workspaceId);
    }

    /**
     * Delete the dictionary in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void deleteWorkspaceDictionary(String workspaceId, String dictioanaryId)
            throws QuadrigaStorageException {
        dbConnect.deleteWorkspaceDictionary(workspaceId, dictioanaryId);
    }

}
