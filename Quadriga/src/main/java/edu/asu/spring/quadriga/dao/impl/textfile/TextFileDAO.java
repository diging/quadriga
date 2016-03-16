package edu.asu.spring.quadriga.dao.impl.textfile;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.impl.UserDAO;
import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class TextFileDAO extends BaseDAO<TextFileDTO> implements ITextFileDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(TextFileDAO.class);

    @Override
    public <List>TextFileDTO getTextFileDTObyWsId(String wsId) {
        List txtFileList = null;
        try{
            
        }
        catch(HibernateException hEx){
            
        }
        return null;
    }

    @Override
    public TextFileDTO getTextFileDTObyProjId(String projId) {
        // TODO To be implemented
        return null;
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
    @Transactional
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
            return null;
        }
        return tfDTO;
    }

}
