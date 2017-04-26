package edu.asu.spring.quadriga.dao.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

/**
 * This class contains the common methods used in data access object classes.
 * 
 * @author Julia Damerow, kbatna
 */
public abstract class BaseDAO<T> implements IBaseDAO<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	@Resource(name = "projectconstants")
	protected Properties messages;

	private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.spring.quadriga.dao.impl.IBaseDAO#generateUniqueID()
	 */
	@Override
	public String generateUniqueID() {
		String id = null;
		while (true) {
			id = getIdPrefix() + generateId();
			T existingDto = getDTO(id);
			if (existingDto == null)
				break;
		}
		return id;
	}

	/**
	 * This methods generates a new 6 character long id. Note that this method
	 * does not assure that the id isn't in use yet.
	 * 
	 * Adapted from
	 * http://stackoverflow.com/questions/9543715/generating-human-readable
	 * -usable-short-but-unique-ids
	 * 
	 * @return 6 character id
	 */
	private String generateId() {
		char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			builder.append(chars[random.nextInt(62)]);
		}

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.asu.spring.quadriga.dao.impl.IBaseDAO#getUserDTO(java.lang.String)
	 */
	@Override
	public QuadrigaUserDTO getUserDTO(String userName) {
		return (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.spring.quadriga.dao.impl.IBaseDAO#getList(java.lang.String)
	 */
	@Override
	public List<String> getList(String commaSeparatedList) {
		return Arrays.asList(commaSeparatedList.split(","));
	}

	@Override
	public void updateDTO(T dto) {
		sessionFactory.getCurrentSession().update(dto);
	}

	@Override
	public void updateObject(Object obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	@Transactional
	public void saveNewDTO(T dto) {
		sessionFactory.getCurrentSession().save(dto);
	}

	@Override
	public void deleteDTO(T dto) {
		sessionFactory.getCurrentSession().delete(dto);
	}

	@Override
	public void saveOrUpdateDTO(T dto) {
		sessionFactory.getCurrentSession().saveOrUpdate(dto);
	}

	protected void deleteObject(Object object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	protected T getDTO(Class<T> clazz, String id) {
		try {
			return (T) sessionFactory.getCurrentSession().get(clazz, id);
		} catch (HibernateException e) {
			logger.error("Retrieve workspace details method :", e);
			return null;
		}
	}

	protected T getDTO(Class<T> clazz, Serializable primKey) {
		try {
			return (T) sessionFactory.getCurrentSession().get(clazz, primKey);
		} catch (HibernateException e) {
			logger.error("Retrieve workspace details method :", e);
			return null;
		}
	}

	public String getIdPrefix() {
		return messages.getProperty("notype_id.prefix");
	}

	public abstract T getDTO(String id);

}
