package edu.asu.spring.quadriga.service.dictionary.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.dao.dictionary.IDictionaryCollaboratorDAO;
import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.dao.dictionary.impl.DictionaryDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;

@Service
public class DictionaryCollaboratorManager extends CollaboratorManager<DictionaryCollaboratorDTO, DictionaryCollaboratorDTOPK, DictionaryDTO, DictionaryDAO> implements
		IDictionaryCollaboratorManager {
	
	@Autowired
	private IDictionaryCollaboratorDAO dictionaryCollaboratorDao;
	
	@Autowired
	private IDictionaryDAO dictionaryDao;
	
	@Override
    public DictionaryCollaboratorDTO createNewCollaboratorDTO() {
        return new DictionaryCollaboratorDTO();
    }

    @Override
    public DictionaryCollaboratorDTOPK createNewCollaboratorDTOPK(String id,
            String collabUser, String role) {
        return new DictionaryCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<DictionaryDTO> getDao() {
        return dictionaryDao;
    }

    @Override
    public ICollaboratorDAO<DictionaryCollaboratorDTO> getCollaboratorDao() {
        return dictionaryCollaboratorDao;
    }
}
