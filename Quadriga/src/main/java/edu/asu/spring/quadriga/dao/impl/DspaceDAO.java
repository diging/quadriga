package edu.asu.spring.quadriga.dao.impl;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.IDspaceDAO;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dto.DspaceKeysDTO;
import edu.asu.spring.quadriga.dto.DspaceKeysDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DspaceDTOMapper;

@Repository
public class DspaceDAO extends BaseDAO<DspaceKeysDTO> implements IDspaceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DspaceDTOMapper dspaceDTOMapper;

    private static final Logger logger = LoggerFactory
            .getLogger(DspaceDAO.class);

    /**
     * This method get the Dspace keys from the database for the user
     * 
     * @param username
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public IDspaceKeys getDspaceKeys(String username)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "DspaceKeysDTO.findByUsername");
            query.setParameter("username", username);
            DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
            if (dspaceKeysDTO != null) {
                return dspaceDTOMapper.getIDspaceKeys(dspaceKeysDTO);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("getDspaceKeys method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This method save or update the DSpace keys
     * 
     * @param : IDspaceKeys - dspace keys object
     * @param : username - logged in user
     * @return : Success - 0 Failure - 1
     */
    @Override
    public int saveOrUpdateDspaceKeys(IDspaceKeys dspaceKeys, String username)
            throws QuadrigaStorageException {
        try {
            DspaceKeysDTO dspaceKeysDTO = new DspaceKeysDTO(username,
                    dspaceKeys.getPublicKey(), dspaceKeys.getPrivateKey());
            sessionFactory.getCurrentSession().saveOrUpdate(dspaceKeysDTO);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("getDspaceKeys method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This method deletes the DSpace keys associated with the given user
     * 
     * @param : username - logged in user
     * @return : Success - 1 Failure - 0
     */
    @Override
    public int deleteDspaceKeys(String username)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "DspaceKeysDTO.findByUsername");
            query.setParameter("username", username);
            DspaceKeysDTO dspaceKeysDTO = (DspaceKeysDTO) query.uniqueResult();
            sessionFactory.getCurrentSession().delete(dspaceKeysDTO);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("deleteDspaceKeys method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Method to add bitstream to workspace
     * 
     * @param workspaceid
     *            ,communityid, collectionid,itemid, bitstreamid and username
     * @return integer representing the result of the save operation
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public int addBitstreamToWorkspace(String workspaceid, String bitstreamid,
            String itemHandle, String username) throws QuadrigaStorageException {
        try {
            WorkspaceDspaceDTO workspaceDspaceDTO = new WorkspaceDspaceDTO();
            workspaceDspaceDTO
                    .setWorkspaceDspaceDTOPK(new WorkspaceDspaceDTOPK(
                            workspaceid, bitstreamid, itemHandle));
            workspaceDspaceDTO.setCreatedby(username);
            workspaceDspaceDTO.setCreateddate(new Date());
            sessionFactory.getCurrentSession().saveOrUpdate(workspaceDspaceDTO);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("addBitstreamToWorkspace method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Method to delete bitstream from the workspace
     * 
     * @param workspaceid
     *            ,bitstreamid and username
     * @return void
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void deleteBitstreamFromWorkspace(String workspaceid,
            String bitstreamids, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "from WorkspaceDspaceDTO wsDspace where wsDspace.workspaceDspaceDTOPK.workspaceid =:workspaceid and wsDspace.workspaceDspaceDTOPK.bitstreamid =:bitstreamid");
            query.setParameter("workspaceid", workspaceid);
            query.setParameter("bitstreamid", bitstreamids);
            WorkspaceDspaceDTO workspaceDspaceDTO = (WorkspaceDspaceDTO) query
                    .uniqueResult();
            if (workspaceDspaceDTO != null) {
                sessionFactory.getCurrentSession().delete(workspaceDspaceDTO);
            } else {
                throw new QuadrigaAccessException(
                        "This action has been logged. Please don't try to hack into the system !!!");
            }
        } catch (Exception e) {
            logger.error("deleteBitstreamFromWorkspace method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Method to add Dspace keys to the database
     * 
     * @param dspace
     *            keys and username
     * @return Integer 0 or 1 representing the result
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public int addDspaceKeys(IDspaceKeys dspaceKeys, String username)
            throws QuadrigaStorageException {
        if (dspaceKeys == null || dspaceKeys.getPublicKey() == null
                || dspaceKeys.getPrivateKey() == null || username == null
                || dspaceKeys.getPublicKey().equals("")
                || dspaceKeys.getPrivateKey().equals("") || username.equals("")) {
            return FAILURE;
        }

        DspaceKeysDTO dspaceKeysDTO = new DspaceKeysDTO(
                new DspaceKeysDTOPK(username, dspaceKeys.getPublicKey(),
                        dspaceKeys.getPrivateKey()));
        try {
            sessionFactory.getCurrentSession().save(dspaceKeysDTO);
        } catch (Exception e) {
            logger.error("addDspaceKeys method :", e);
            throw new QuadrigaStorageException(e);
        }
        return SUCCESS;
    }

    @Override
    public DspaceKeysDTO getDTO(String id) {
        return getDTO(DspaceKeysDTO.class, id);
    }
}
