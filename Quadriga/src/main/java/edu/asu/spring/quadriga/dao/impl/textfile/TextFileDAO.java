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
 * @author Nischal Samji
 * 
 *         Data Access Object for performing Text File Operations.
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
    public List<TextFileDTO> getTextFileDTObyWsId(String wsId) {

        List<TextFileDTO> tfDTO = null;
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("from TextFileDTO txtFiles where txtFiles.workspaceId =:wsId");
            query.setParameter("wsId", wsId);
            tfDTO = (List<TextFileDTO>) query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tfDTO;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.dao.textfile.ITextFileDAO#saveTextFileDTO(edu.asu
     * .spring.quadriga.dto.TextFileDTO) Method to save text file properties in
     * the db.
     * 
     */

    @Override
    public boolean saveTextFileDTO(TextFileDTO txtFileDTO) throws QuadrigaStorageException {
        try {
            sessionFactory.getCurrentSession().save(txtFileDTO);
            return true;
        } catch (Exception e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.dao.impl.BaseDAO#getDTO(java.lang.String)
     * Returns a Text DTO for db operations
     */
    @Override
    public TextFileDTO getDTO(String id) {
        return getDTO(TextFileDTO.class, id);
    }

    @Override
    public TextFileDTO getTextFileDTO(String textId) {
        TextFileDTO tfDTO = null;

        try {
            tfDTO = (TextFileDTO) sessionFactory.getCurrentSession().get(TextFileDTO.class, textId);
        } catch (HibernateException e) {
            logger.error("Retrieve Text File details method :", e);
        }
        return tfDTO;
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("textfile_id.prefix");
    }

}
