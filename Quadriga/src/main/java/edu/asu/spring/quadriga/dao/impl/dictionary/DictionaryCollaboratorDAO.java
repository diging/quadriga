package edu.asu.spring.quadriga.dao.impl.dictionary;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;

@Repository
public class DictionaryCollaboratorDAO extends BaseDAO<DictionaryCollaboratorDTO> implements IDictionaryCollaboratorDAO {
	
    @Override
    public DictionaryCollaboratorDTO getDTO(String id) {
        return getDTO(DictionaryCollaboratorDTO.class, id);
    }
	
}
