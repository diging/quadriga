package edu.asu.spring.quadriga.conceptpower.db.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptType;
import edu.asu.spring.quadriga.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.spring.quadriga.conceptpower.impl.Concept;
import edu.asu.spring.quadriga.conceptpower.impl.ConceptType;

@Component
@Transactional
public class ConceptDatabaseConnection implements IConceptDatabaseConnection {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptDatabaseConnection#getConcept(java.lang.String)
     */
    @Override
    public IConcept getConcept(String uri) {
        Object objConcept = sessionFactory.getCurrentSession().get(Concept.class, uri);
        
        // let's check if concept uses a different main id
        if (objConcept == null) {
            Query query = sessionFactory.getCurrentSession().createQuery("SELECT c from Concept c WHERE :uri in elements(c.alternativeUris)");
            query.setParameter("uri", uri);
            List<?> results = query.list();
            if (results != null && !results.isEmpty()) {
                // there shouldn't be more than one, but if there is just take the first one
                objConcept = results.get(0);
            }
        }
        
        if (objConcept == null) {
            return null;
        }
        
        IConcept concept = (IConcept) objConcept;
        if (concept.getTypeId() != null) {
            Object objType = sessionFactory.getCurrentSession().get(ConceptType.class, concept.getTypeId());
            if (objType != null) {
                concept.setType((IConceptType) objType);
            }
        }
        
        return concept;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptDatabaseConnection#createOrUpdate(edu.asu.spring.quadriga.conceptpower.db.IConcept)
     */
    @Override
    public void createOrUpdate(IConcept concept) {
        Object objConcept = sessionFactory.getCurrentSession().get(Concept.class, concept.getUri());
        
        // if concept exists, let's update it
        if (objConcept == null || isDifferent(concept, (IConcept)objConcept)) {
            logger.debug((objConcept == null ? "Adding " : "Updating: ") + concept.getUri());
            if (objConcept != null) {
                sessionFactory.getCurrentSession().evict(objConcept);
            }
            sessionFactory.getCurrentSession().saveOrUpdate(concept);
        }
        
        // update type if there is one
        if (concept.getTypeId() != null && !concept.getTypeId().trim().isEmpty()) {
            IConceptType type = getType(concept.getTypeId());
            if (type == null || isDifferent(concept.getType(), type)) {
                if (type != null) {
                    sessionFactory.getCurrentSession().evict(type);
                }
                sessionFactory.getCurrentSession().saveOrUpdate(concept.getType());
            }
        }
        
        // delete concepts that are represented by this one
        List<String> alternativeUris = concept.getAlternativeUris();
        for (String altUri : alternativeUris) {
            if (!altUri.equals(concept.getUri())) {
                deleteConcept(altUri);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptDatabaseConnection#deleteConcept(java.lang.String)
     */
    @Override
    public void deleteConcept(String uri) {
        Object concept = sessionFactory.getCurrentSession().get(Concept.class, uri);
        if (concept != null) {
            sessionFactory.getCurrentSession().delete(concept);
        }
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptDatabaseConnection#getType(java.lang.String)
     */
    @Override
    public IConceptType getType(String uri) {
        Object objType = sessionFactory.getCurrentSession().get(ConceptType.class, uri);
        if (objType != null) {
            return (IConceptType) objType;
        }
        return null;
    }
    
    private boolean isDifferent(IConcept concept1, IConcept concept2) {
        if (!concept1.getAlternativeUris().equals(concept2.getAlternativeUris())) {
            return true;
        }
        if (!concept1.getConceptList().equals(concept2.getConceptList())) {
            return true;
        }
        if (!concept1.getCreatorId().equals(concept2.getCreatorId())) {
            return true;
        }
        if (!concept1.getDescription().equals(concept2.getDescription())) {
            return true;
        }
        if (!concept1.getEqualTo().equals(concept2.getEqualTo())) {
            return true;
        }
        if (!concept1.getId().equals(concept2.getId())) {
            return true;
        }
        if (!concept1.getPos().equals(concept2.getPos())) {
            return true;
        }
        if (!concept1.getTypeId().equals(concept2.getTypeId())) {
            return true;
        }
        if (!concept1.getUri().equals(concept2.getUri())) {
            return true;
        }
        if (!concept1.getWord().equals(concept2.getWord())) {
            return true;
        }
        if (!concept1.getWordnetIds().equals(concept2.getWordnetIds())) {
            return true;
        }
        return false;
    }
    
    private boolean isDifferent(IConceptType type1, IConceptType type2) {
        if (type1 == null && type2 == null) {
            return false;
        }
        if (type1 == null || type2 == null) {
            return true;
        }
        if (!type1.getId().equals(type2.getId())) {
            return true;
        }
        if (!type1.getDescription().equals(type2.getDescription())) {
            return true;
        }
        if (!type1.getName().equals(type2.getName())) {
            return true;
        }
        if (!type1.getUri().equals(type2.getUri())) {
            return true;
        }
        return false;
    }
}
