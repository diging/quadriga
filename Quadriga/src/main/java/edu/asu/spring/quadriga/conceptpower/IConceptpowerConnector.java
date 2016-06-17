package edu.asu.spring.quadriga.conceptpower;

import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;

public interface IConceptpowerConnector {

    /**
     * Method to search for concepts by providing a word and a POS. This method assumes
     * that neither item nor pos are not null.
     * @param item The word to search for, e.g. "pony". Cannot be null.
     * @param pos The part of speech, e.g. "noun" or "verb". Cannot be null.
     * @return The reply from Conceptpower.
     */
    public abstract ConceptpowerReply search(String item, String pos);

    /**
     * Method to retrieve a concept by its id.
     * @param id Id of the concept (either whole URI or id). Cannot be null.
     * @return The concept for the provided id.
     */
    public abstract ConceptpowerReply getById(String id);

    ConceptpowerReply search(String searchTerm);

}