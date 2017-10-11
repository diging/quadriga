package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_network_annotations_node")
@NamedQueries({
    @NamedQuery(name = "NetworkNodeAnnotationsDTO.findAll", query = "SELECT n FROM NetworkNodeAnnotationsDTO n"),
    @NamedQuery(name = "NetworkNodeAnnotationsDTO.findByNodeId", query = "SELECT n FROM NetworkNodeAnnotationsDTO n WHERE n.nodeId = :nodeid"),
    @NamedQuery(name = "NetworkNodeAnnotationsDTO.findByNodeName", query = "SELECT n FROM NetworkNodeAnnotationsDTO n WHERE n.nodeName = :nodename"),
    })
public class NetworkNodeAnnotationsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "nodeannotationid")
    private String nodeAnnotationId;
	
	@Basic(optional = false)
    @Column(name = "nodeid")
    private String nodeId;
	
	@Basic(optional = false)
    @Column(name = "nodename")
    private String nodeName;
	
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdBy;
    
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Basic(optional = false)    
    @Column(name = "updatedby")
    private String updatedBy;
    
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDdate;
	
	@JoinColumn(name = "nodeannotationid", referencedColumnName = "annotationid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private NetworkAnnotationsDTO annotationNodes;

	public String getNodeAnnotationId() {
		return nodeAnnotationId;
	}

	public void setNodeAnnotationId(String nodeAnnotationId) {
		this.nodeAnnotationId = nodeAnnotationId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateDdate() {
		return updateDdate;
	}

	public void setUpdateDdate(Date updateDdate) {
		this.updateDdate = updateDdate;
	}

	public NetworkAnnotationsDTO getAnnotationNodes() {
		return annotationNodes;
	}

	public void setAnnotationNodes(NetworkAnnotationsDTO annotationNodes) {
		this.annotationNodes = annotationNodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((nodeAnnotationId == null) ? 0 : nodeAnnotationId.hashCode());
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
		NetworkNodeAnnotationsDTO other = (NetworkNodeAnnotationsDTO) obj;
		if (nodeAnnotationId == null) {
			if (other.nodeAnnotationId != null)
				return false;
		} else if (!nodeAnnotationId.equals(other.nodeAnnotationId))
			return false;
		return true;
	}

	
	
}
