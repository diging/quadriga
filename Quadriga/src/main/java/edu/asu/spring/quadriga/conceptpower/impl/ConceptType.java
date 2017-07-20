package edu.asu.spring.quadriga.conceptpower.impl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import edu.asu.spring.quadriga.conceptpower.IConceptType;

@Entity
@Table(name = "tbl_conceptpower_concepttype")
public class ConceptType implements IConceptType {

    @Id private String uri;
    private String id;
    private String name;
    @Lob private String description;
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getUri()
     */
    @Override
    public String getUri() {
        return uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setUri(java.lang.String)
     */
    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConceptType#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
