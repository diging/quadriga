package edu.asu.spring.quadriga.dao.impl.dictionary;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

@Repository
public class DictionaryCollaboratorDAO extends
        BaseDAO<DictionaryCollaboratorDTO> implements
        IDictionaryCollaboratorDAO {

    @Override
    public DictionaryCollaboratorDTO getDTO(String id) {
        return getDTO(DictionaryCollaboratorDTO.class, id);
    }

    @Override
    public List<QuadrigaUserDTO> getUsersNotCollaborating(String dtoId) {
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "SELECT quadUser FROM QuadrigaUserDTO quadUser where quadUser.username NOT IN "
                                + "(Select dictCollab.quadrigaUserDTO.username from DictionaryCollaboratorDTO dictCollab "
                                + "where dictCollab.collaboratorDTOPK.dictionaryid =:dictionaryid) AND quadUser.username "
                                + "NOT IN (Select dict.dictionaryowner.username from DictionaryDTO dict where dict.dictionaryid =:dictionaryid)");
        query.setParameter("dictionaryid", dtoId);
        return query.list();
    }
}
