package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_network_annotations_edge")
@XmlRootElement
public class NetworkEdgeAnnotationsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "annotationid")
    private String annotationId;
	
	@Basic(optional = false)
    @Column(name = "sourceid")
    private String sourceId;
	@Basic(optional = false)
    @Column(name = "sourcename")
    private String sourceName;
	@Basic(optional = false)
    @Column(name = "targetid")
    private String targetId;
	@Basic(optional = false)
    @Column(name = "targetname")
    private String targetName;
	@Basic(optional = false)
    @Column(name = "targetnodetype")
    private String targetNodeType;
	
	@JoinColumn(name = "networkannotation", referencedColumnName = "annotationid")
    @ManyToOne(optional = false)
    private NetworkAnnotationsDTO edges;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetNodeType() {
		return targetNodeType;
	}

	public void setTargetNodeType(String targetNodeType) {
		this.targetNodeType = targetNodeType;
	}

//	public NetworkAnnotationsDTO getNetworkAnnotations() {
//		return networkAnnotations;
//	}
//
//	public void setNetworkAnnotations(NetworkAnnotationsDTO networkAnnotations) {
//		this.networkAnnotations = networkAnnotations;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime
//				* result
//				+ ((networkAnnotations == null) ? 0 : networkAnnotations
//						.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		NetworkEdgeAnnotationsDTO other = (NetworkEdgeAnnotationsDTO) obj;
//		if (networkAnnotations == null) {
//			if (other.networkAnnotations != null)
//				return false;
//		} else if (!networkAnnotations.equals(other.networkAnnotations))
//			return false;
//		return true;
//	}
	

}
