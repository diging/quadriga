package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

//@Service
@Repository
public class PublicPageDAO extends BaseDAO<PublicPageDTO> implements IPublicPageDAO {

//    @Autowired
//    protected SessionFactory sessionFactory;
//
//    @Resource(name = "projectconstants")
//    protected Properties messages;
//
//    private static final Logger logger = LoggerFactory.getLogger(PublicPageDAO.class);
//
//	@Override
//	public PublicPageDTO getDTO(String id) {
//		return getDTO(PublicPageDTO.class, id);
//	}

    @Override
    public PublicPageDTO getPublicPageDTO(String id) {
       return getDTO(PublicPageDTO.class, id);
    }

    @Override
    public PublicPageDTO getDTO(String id) {
        return getPublicPageDTO(id);
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("publicpage_id.prefix");
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<PublicPageDTO> getPublicPageDTOListByProjectId(String projectId, Integer limit)
            throws QuadrigaStorageException {

        if (projectId == null || projectId.equals(""))
            return null;

        // Create a query to get all projects
        Query query = sessionFactory.getCurrentSession().getNamedQuery("PublicPageDTO.findByProjectId");

        // Set max limit of number of items when there is limit on number of
        // results to fetch
        if (limit != null) {
            query.setMaxResults(limit);
        }

        query.setParameter("projectId", projectId);

        @SuppressWarnings("unchecked")
        List<PublicPageDTO> publicpageDTOList = query.list();

        return publicpageDTOList;
    }
}
