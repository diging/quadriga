package edu.asu.spring.quadriga.dao;

import java.util.List;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

public interface ICollaboratorDAO<T> extends IBaseDAO<T> {

	public List<QuadrigaUserDTO> getUsersNotCollaborating(String dtoId);
}
