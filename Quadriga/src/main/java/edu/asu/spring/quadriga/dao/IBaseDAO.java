package edu.asu.spring.quadriga.dao;

import java.util.List;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IBaseDAO<T> {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	/**
	 * Generate an unique identifier for the database field
	 * 
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract String generateUniqueID();

	/**
	 * This method returns the User DAO object for the given userName
	 * 
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 * @author Kiran Batna
	 */
	public abstract QuadrigaUserDTO getUserDTO(String userName);

	/**
	 * This methods splits the comma seperated string into a list
	 * 
	 * @param users
	 * @return ArrayList<String>
	 */
	public abstract List<String> getList(String commaSeparatedList);

	public abstract void updateDTO(T wsDto);

	public abstract void saveNewDTO(T dto);

	public abstract void updateObject(Object obj);

	public abstract void deleteDTO(T dto);

	public abstract T getDTO(String id);

	public abstract void saveOrUpdateDTO(T dto);

}
