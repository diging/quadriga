package edu.asu.spring.quadriga.conceptpower.impl;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptType;

@Entity
@Table(name = "tbl_conceptpower_concept")
public class Concept implements IConcept {

    @Id @Index(name="uri_idx") private String uri;
    private String id;
    private String word;
    private String pos;
    @Lob private String description;
    private String conceptList;
    private String typeId;
    private boolean deleted;
    
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="tbl_conceptpower_alternativeuris", joinColumns=@JoinColumn(name="id"))
    @Column(name="alternativeUris")
    private List<String> alternativeUris;
    
    private String creatorId;
    
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="tbl_conceptpower_equalto", joinColumns=@JoinColumn(name="id"))
    @Column(name="equalto")
    private List<String> equalTo;
    
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="tbl_conceptpower_wordnetids", joinColumns=@JoinColumn(name="id"))
    @Column(name="wordnetids")
    private List<String> wordnetIds;
    
    @Transient private IConceptType type;
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getUri()
     */
    @Override
    public String getUri() {
        return uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setUri(java.lang.String)
     */
    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getWord()
     */
    @Override
    public String getWord() {
        return word;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setWord(java.lang.String)
     */
    @Override
    public void setWord(String word) {
        this.word = word;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getPos()
     */
    @Override
    public String getPos() {
        return pos;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setPos(java.lang.String)
     */
    @Override
    public void setPos(String pos) {
        this.pos = pos;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getConceptList()
     */
    @Override
    public String getConceptList() {
        return conceptList;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setConceptList(java.lang.String)
     */
    @Override
    public void setConceptList(String conceptList) {
        this.conceptList = conceptList;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getTypeId()
     */
    @Override
    public String getTypeId() {
        return typeId;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setTypeId(java.lang.String)
     */
    @Override
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#isDeleted()
     */
    @Override
    public boolean isDeleted() {
        return deleted;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setDeleted(boolean)
     */
    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getAlternativeUris()
     */
    @Override
    public List<String> getAlternativeUris() {
        return alternativeUris;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setAlternativeUris(java.util.List)
     */
    @Override
    public void setAlternativeUris(List<String> alternativeUris) {
        this.alternativeUris = alternativeUris;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getCreatorId()
     */
    @Override
    public String getCreatorId() {
        return creatorId;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setCreatorId(java.lang.String)
     */
    @Override
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getEqualTo()
     */
    @Override
    public List<String> getEqualTo() {
        return equalTo;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setEqualTo(java.util.List)
     */
    @Override
    public void setEqualTo(List<String> equalTo) {
        this.equalTo = equalTo;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#getWordnetIds()
     */
    @Override
    public List<String> getWordnetIds() {
        return wordnetIds;
    }
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.db.impl.IConcept#setWordnetIds(java.util.List)
     */
    @Override
    public void setWordnetIds(List<String> wordnetIds) {
        this.wordnetIds = wordnetIds;
    }
    @Override
    public IConceptType getType() {
        return type;
    }
    @Override
    public void setType(IConceptType type) {
        this.type = type;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Concept other = (Concept) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }
}
