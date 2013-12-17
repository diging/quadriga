/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_dictionary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DictionaryDTO.findAll", query = "SELECT d FROM DictionaryDTO d"),
    @NamedQuery(name = "DictionaryDTO.findByDictionaryname", query = "SELECT d FROM DictionaryDTO d WHERE d.dictionaryname = :dictionaryname"),
    @NamedQuery(name = "DictionaryDTO.findById", query = "SELECT d FROM DictionaryDTO d WHERE d.id = :id"),
    @NamedQuery(name = "DictionaryDTO.findByAccessibility", query = "SELECT d FROM DictionaryDTO d WHERE d.accessibility = :accessibility"),
    @NamedQuery(name = "DictionaryDTO.findByUpdatedby", query = "SELECT d FROM DictionaryDTO d WHERE d.updatedby = :updatedby"),
    @NamedQuery(name = "DictionaryDTO.findByUpdateddate", query = "SELECT d FROM DictionaryDTO d WHERE d.updateddate = :updateddate"),
    @NamedQuery(name = "DictionaryDTO.findByCreatedby", query = "SELECT d FROM DictionaryDTO d WHERE d.createdby = :createdby"),
    @NamedQuery(name = "DictionaryDTO.findByCreateddate", query = "SELECT d FROM DictionaryDTO d WHERE d.createddate = :createddate")})
public class DictionaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "dictionaryname")
    private String dictionaryname;
    @Lob
    @Column(name = "description")
    private String description;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "accessibility")
    private Boolean accessibility;
    @Basic(optional = false)
    @Column(name = "updatedby")
    private String updatedby;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdby;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionaryDTO")
    private List<DictionaryItemsDTO> dictionaryItemsDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionaryDTO")
    private List<DictionaryCollaboratorDTO> dictionaryCollaboratorDTOList;
    @JoinColumn(name = "dictionaryowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO dictionaryowner;

    public DictionaryDTO() {
    }

    public DictionaryDTO(String id) {
        this.id = id;
    }

    public DictionaryDTO(String id, String dictionaryname, Boolean accessibility, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.id = id;
        this.dictionaryname = dictionaryname;
        this.accessibility = accessibility;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public String getDictionaryname() {
        return dictionaryname;
    }

    public void setDictionaryname(String dictionaryname) {
        this.dictionaryname = dictionaryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @XmlTransient
    public List<DictionaryItemsDTO> getDictionaryItemsDTOList() {
        return dictionaryItemsDTOList;
    }

    public void setDictionaryItemsDTOList(List<DictionaryItemsDTO> dictionaryItemsDTOList) {
        this.dictionaryItemsDTOList = dictionaryItemsDTOList;
    }

    @XmlTransient
    public List<DictionaryCollaboratorDTO> getDictionaryCollaboratorDTOList() {
        return dictionaryCollaboratorDTOList;
    }

    public void setDictionaryCollaboratorDTOList(List<DictionaryCollaboratorDTO> dictionaryCollaboratorDTOList) {
        this.dictionaryCollaboratorDTOList = dictionaryCollaboratorDTOList;
    }

    public QuadrigaUserDTO getDictionaryowner() {
        return dictionaryowner;
    }

    public void setDictionaryowner(QuadrigaUserDTO dictionaryowner) {
        this.dictionaryowner = dictionaryowner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryDTO)) {
            return false;
        }
        DictionaryDTO other = (DictionaryDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.DictionaryDTO[ id=" + id + " ]";
    }
    
}
