package edu.asu.spring.quadriga.dao.impl.dictionary;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.mapper.DictionaryCollaboratorDTOMapper;

@Repository
public class DictionaryCollaboratorDAO extends BaseDAO<DictionaryCollaboratorDTO> implements IDictionaryCollaboratorDAO {
	@Autowired
	private DictionaryCollaboratorDTOMapper collaboratorMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	@Override
    public DictionaryCollaboratorDTO getDTO(String id) {
        return getDTO(DictionaryCollaboratorDTO.class, id);
    }
	
}
