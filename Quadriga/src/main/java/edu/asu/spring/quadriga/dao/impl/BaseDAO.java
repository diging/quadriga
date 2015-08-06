package edu.asu.spring.quadriga.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class contains the common methods used in 
 * data access object classes.
 * @author kbatna
 */
public abstract class BaseDAO<T> implements IBaseDAO<T>  {

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);
	
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.IBaseDAO#generateUniqueID()
     */
	@Override
    public String generateUniqueID()
	{
			return UUID.randomUUID().toString();
	}
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.IBaseDAO#getUserDTO(java.lang.String)
     */
	@Override
    public QuadrigaUserDTO getUserDTO(String userName) throws QuadrigaStorageException
	{
		QuadrigaUserDTO quadrigaUser = null;
		try
		{
			quadrigaUser = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
		}
		catch(HibernateException e)
		{
			logger.error("retrieving Quadriga user DTO :",e);
        	throw new QuadrigaStorageException();
		}
		return quadrigaUser;
	}
	
	/* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.IBaseDAO#getList(java.lang.String)
     */
	@Override
    public List<String> getList(String commaSeparatedList)
	{
		return Arrays.asList(commaSeparatedList.split(","));
	}
	
	@Override
    public boolean updateDTO(T dto) {
        try {
            sessionFactory.getCurrentSession().update(dto);
        } catch (HibernateException e) {
            logger.error("Couldn't update dto.", e);
            return false;
        }
        
        return true;
    }
	
	@Override
    public boolean saveNewDTO(T dto) {
	    try {
	        sessionFactory.getCurrentSession().save(dto);
	    } catch (HibernateException e) {
            logger.error("Couldn't save dto.", e);
            return false;
        }
	    return true;
	}

	@Override
    public T getDTO(Class<T> clazz, String id) {
	    try {
	        return (T) sessionFactory.getCurrentSession().get(clazz, id);
	    } catch(HibernateException e) {
            logger.error("Retrieve workspace details method :",e);
            return null;
        }
	}
}
