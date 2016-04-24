package edu.asu.spring.quadriga.dao.workbench.settings.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.settings.IStatisticDAO;
import edu.asu.spring.quadriga.dto.ProjectStatisticsDTO;
import edu.asu.spring.quadriga.dto.ProjectStatisticsDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to Statistics Settings module
 * 
 * @author Ajay Modi
 *
 */
@Repository
public class StatisticDAO extends BaseDAO<ProjectStatisticsDTO> implements
        IStatisticDAO {

    @Override
    public List<ProjectStatisticsDTO> getStatisticsSettings(String projectId)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "ProjectStatisticsDTO.findByProjectid");
            query.setParameter("projectid", projectId);
            return query.list();

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public void upsertStatisticsSettings(String projectId, String name,
            Boolean isChecked) throws QuadrigaStorageException {

        Query query = sessionFactory.getCurrentSession().getNamedQuery(
                "ProjectStatisticsDTO.findByName");
        query.setParameter("projectid", projectId);
        query.setParameter("name", name);
        ProjectStatisticsDTO projectStatisticsDTO = (ProjectStatisticsDTO) query
                .uniqueResult();

        if (projectStatisticsDTO == null) {
            projectStatisticsDTO = new ProjectStatisticsDTO();
            ProjectStatisticsDTOPK psDTOPk = new ProjectStatisticsDTOPK(
                    projectId, name);
            projectStatisticsDTO.setProjectStatisticsDTOPK(psDTOPk);
            projectStatisticsDTO.setIschecked(isChecked);
            saveNewDTO(projectStatisticsDTO);
        } else {
            projectStatisticsDTO.setIschecked(isChecked);
            updateDTO(projectStatisticsDTO);
        }
    }

    @Override
    public ProjectStatisticsDTO getDTO(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
