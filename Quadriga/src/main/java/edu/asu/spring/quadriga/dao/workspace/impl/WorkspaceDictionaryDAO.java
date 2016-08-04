package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDictionaryDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryDeepMapper;

@Repository
public class WorkspaceDictionaryDAO extends BaseDAO<WorkspaceDictionaryDTO> implements IWorkspaceDictionaryDAO {

    @Autowired
    DictionaryDTOMapper dictionaryMapper;

    @Autowired
    IDictionaryDeepMapper dictionaryDeepMapper;

    @Autowired
    WorkspaceDTOMapper workspaceDTOMapper;

    @Autowired
    WorkspaceCollaboratorDTOMapper collaboratorDTOMapper;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWorkspaceDictionary(String workspaceId, String dictionaryId, String userId)
            throws QuadrigaStorageException {
        WorkspaceDTO workspace = null;
        DictionaryDTO dictionary = null;
        List<WorkspaceDictionaryDTO> workspaceDictionaryList = null;

        // check if the dictionary is already associated to the workspace
        try {
            workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);

            dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);

            WorkspaceDictionaryDTO workspaceDictionary = workspaceDTOMapper.getWorkspaceDictionary(workspace,
                    dictionary, userId);

            sessionFactory.getCurrentSession().save(workspaceDictionary);

            // add the workspace dictionary mapping to workspace object
            workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
            if (workspaceDictionaryList == null) {
                workspaceDictionaryList = new ArrayList<WorkspaceDictionaryDTO>();
            }
            workspaceDictionaryList.add(workspaceDictionary);
            workspace.setWorkspaceDictionaryDTOList(workspaceDictionaryList);
            sessionFactory.getCurrentSession().update(workspace);

            // add the workspace dictionary mapping to dictionary object
            workspaceDictionaryList = dictionary.getWsDictionaryDTOList();
            if (workspaceDictionaryList == null) {
                workspaceDictionaryList = new ArrayList<WorkspaceDictionaryDTO>();
            }
            workspaceDictionaryList.add(workspaceDictionary);
            dictionary.setWsDictionaryDTOList(workspaceDictionaryList);
            sessionFactory.getCurrentSession().update(dictionary);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new QuadrigaStorageException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkspaceDTO listWorkspaceDictionary(String workspaceId, String userId) throws QuadrigaStorageException {
        WorkspaceDTO workspaceDTO = null;

        try {
            workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);

        } catch (Exception ex) {
            throw new QuadrigaStorageException();
        }

        return workspaceDTO;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId)
            throws QuadrigaStorageException {
        
        List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "FROM DictionaryDTO dict WHERE dict.dictionaryid NOT IN("
                                    + "SELECT w.workspaceDictionaryDTOPK.dictionaryid FROM WorkspaceDictionaryDTO w WHERE w.workspaceDictionaryDTOPK.workspaceid = :workspaceid)");
            query.setParameter("workspaceid", workspaceId);
            List<DictionaryDTO> dictionaryDTOList = query.list();

            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                IDictionary dictionary = dictionaryDeepMapper.getDictionaryDetails(dictionaryDTO);
                dictionaryList.add(dictionary);
            }

        } catch (HibernateException ex) {
            throw new QuadrigaStorageException(ex);
        }
        return dictionaryList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkspaceDictionary(String workspaceId, String dictionaryId)
            throws QuadrigaStorageException {
        WorkspaceDTO workspace = null;
        List<WorkspaceDictionaryDTO> workspaceDictionaryList = new ArrayList<WorkspaceDictionaryDTO>();
        try {
            workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);

            WorkspaceDictionaryDTOPK workspaceDictionaryKey = new WorkspaceDictionaryDTOPK(workspaceId, dictionaryId);

            WorkspaceDictionaryDTO workspaceDictionary = (WorkspaceDictionaryDTO) sessionFactory.getCurrentSession()
                    .get(WorkspaceDictionaryDTO.class, workspaceDictionaryKey);

            workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();

            if (workspaceDictionaryList.contains(workspaceDictionary)) {
                workspaceDictionaryList.remove(workspaceDictionary);
            }

            workspace.setWorkspaceDictionaryDTOList(workspaceDictionaryList);

            sessionFactory.getCurrentSession().delete(workspaceDictionary);
            sessionFactory.getCurrentSession().update(workspace);
        } catch (HibernateException ex) {
            throw new QuadrigaStorageException(ex);
        }

    }

    @Override
    public List<IWorkSpace> getWorkspaceByDictId(String dictionaryId) throws QuadrigaStorageException {

        List<IWorkSpace> workspaces = new ArrayList<IWorkSpace>();

        DictionaryDTO dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,
                dictionaryId);

        List<WorkspaceDictionaryDTO> workspaceDTOList = dictionaryDTO.getWsDictionaryDTOList();

        for (WorkspaceDictionaryDTO workspaceDictionaryDTO : workspaceDTOList) {
            WorkspaceDTO workspaceDTO = workspaceDictionaryDTO.getWorkspaceDTO();

            if (workspaceDTO != null) {
                IWorkSpace workSpace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
                workspaces.add(workSpace);
            }
        }

        return workspaces;
    }

    @Override
    public WorkspaceDictionaryDTO getDTO(String id) {
        return getDTO(WorkspaceDictionaryDTO.class, id);
    }

}
