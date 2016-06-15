package edu.asu.spring.quadriga.dao.impl.textfile;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Data Access Object for performing Text File Operations.
 * 
 * @author Nischal Samji
 *
 */
@Repository
@Transactional
public class TextFileDAO extends BaseDAO<TextFileDTO> implements ITextFileDAO {

    @Resource(name = "projectconstants")
    private Properties messages;

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(TextFileDAO.class);

    @Override
    public List<TextFileDTO> getTextFileDTObyWsId(String wsId) throws QuadrigaStorageException {

        List<TextFileDTO> tfDTO = null;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from TextFileDTO txtFiles where txtFiles.workspaceId =:wsId");
            query.setParameter("wsId", wsId);
            tfDTO = (List<TextFileDTO>) query.list();
        } catch (HibernateException e) {
            logger.error("TextFile List associated with workspace id" + wsId, e);
            throw new QuadrigaStorageException(e);
        }
        return tfDTO;

    }

    @Override
    public TextFileDTO getTextFileByUri(String uri) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from TextFileDTO txtFiles where txtFiles.refId =:refId");
            query.setParameter("refId", uri);
            return (TextFileDTO) query.uniqueResult();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public TextFileDTO getDTO(String id) {
        return getDTO(TextFileDTO.class, id);
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("textfile_id.prefix");
    }

}
