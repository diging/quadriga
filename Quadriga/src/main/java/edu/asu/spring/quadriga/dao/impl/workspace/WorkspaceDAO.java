package edu.asu.spring.quadriga.dao.impl.workspace;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;

@Repository
public class WorkspaceDAO extends BaseDAO implements IWorkspaceDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceDAO.class);
    

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.workspace.IWorkspaceDAO#getWorkspaceDTO(java.lang.String)
     */
    @Override
    public WorkspaceDTO getWorkspaceDTO(String workspaceId) {
        WorkspaceDTO workspaceDTO = null;
        try {
            workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
        } catch(HibernateException e) {
            logger.error("Retrieve workspace details method :",e);
            return null;
        }
        return workspaceDTO;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.workspace.IWorkspaceDAO#updateWorkspaceDTO(edu.asu.spring.quadriga.dto.WorkspaceDTO)
     */
    @Override
    public boolean updateWorkspaceDTO(WorkspaceDTO wsDto) {
        try {
            sessionFactory.getCurrentSession().update(wsDto);
        } catch (HibernateException e) {
            logger.error("Couldn't save WS object", e);
            return false;
        }
        
        return true;
    }
    
}
