package edu.asu.spring.quadriga.dao.workbench.settings.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.settings.IStatisticSettingsDAO;
import edu.asu.spring.quadriga.dto.StatisticsSettingsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to Statistics Settings module
 * 
 * @author Ajay Modi
 *
 */
@Repository
public class StatisticSettingsDAO extends BaseDAO<StatisticsSettingsDTO>
        implements IStatisticSettingsDAO {

    private static final Logger logger = LoggerFactory
            .getLogger(StatisticSettingsDAO.class);

    @Override
    public List<StatisticsSettingsDTO> getStatisticsSettings(String projectId)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "StatisticsSettingsDTO.findByProjectid");
            query.setParameter("projectid", projectId);
            return query.list();

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public StatisticsSettingsDTO getStatisticsSettings(String projectId,
            String name) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "StatisticsSettingsDTO.findByName");
            query.setParameter("projectid", projectId);
            query.setParameter("name", name);
            StatisticsSettingsDTO statsSettingsDTO = (StatisticsSettingsDTO) query
                    .uniqueResult();
            return statsSettingsDTO;

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This method save or update the statistics settings
     * 
     * @param : statsSettingsDTO
     * @return : Success - 0 Failure - 1
     */

    @Override
    public int saveOrUpdateStatisticsSettings(
            StatisticsSettingsDTO statsSettingsDTO)
            throws QuadrigaStorageException {

        try {
            sessionFactory.getCurrentSession().saveOrUpdate(statsSettingsDTO);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("saveOrUpdateStatisticsSettings method :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public StatisticsSettingsDTO getDTO(String id) {
        return getDTO(StatisticsSettingsDTO.class, id);
    }

}
