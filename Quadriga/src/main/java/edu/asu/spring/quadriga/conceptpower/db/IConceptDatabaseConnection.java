package edu.asu.spring.quadriga.conceptpower.db;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptType;

public interface IConceptDatabaseConnection {

    IConcept getConcept(String uri);

    void createOrUpdate(IConcept concept);

    void deleteConcept(String uri);

    IConceptType getType(String uri);

}