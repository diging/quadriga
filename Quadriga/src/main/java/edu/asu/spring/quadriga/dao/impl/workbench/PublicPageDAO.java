package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;

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
	@Resource(name = "projectconstants")
    private Properties messages;

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
    }
