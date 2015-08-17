package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.dao.impl.dictionary.DictionaryDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;

@Service
public class DictionaryCollaboratorManager extends CollaboratorManager<DictionaryCollaboratorDTO, DictionaryCollaboratorDTOPK, DictionaryDTO, DictionaryDAO> implements
		IDictionaryCollaboratorManager {
	
	@Autowired
	private IDBConnectionDictionaryCollaboratorManager dbConnect;
	
	@Autowired
	private IDictionaryDAO dictionaryDao;
	
	@Override
    public DictionaryCollaboratorDTO createNewDTO() {
        return new DictionaryCollaboratorDTO();
    }

    @Override
    public DictionaryCollaboratorDTOPK createNewDTOPK(String id,
            String collabUser, String role) {
        return new DictionaryCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<DictionaryDTO> getDao() {
        return dictionaryDao;
    }
	
	

}
