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
@Table(name = "tbl_network_annotations_node")
@XmlRootElement
public class NetworkNodeAnnotationsDTO implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "annotationid")
    private String annotationId;
	@Basic(optional = false)
    @Column(name = "nodeid")
    private String nodeId;
	@Basic(optional = false)
    @Column(name = "nodename")
    private String nodeName;
	
	@JoinColumn(name = "networkannotation", referencedColumnName = "annotationid")
    @ManyToOne(optional = false)
    private NetworkAnnotationsDTO nodes;
	
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

//	public NetworkAnnotationsDTO getNetworkannotations() {
//		return networkAnnotations;
//	}
//
//	public void setNetworkannotations(NetworkAnnotationsDTO networkannotations) {
//		this.networkAnnotations = networkannotations;
//	}
//
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
//		NetworkNodeAnnotationsDTO other = (NetworkNodeAnnotationsDTO) obj;
//		if (networkAnnotations == null) {
//			if (other.networkAnnotations != null)
//				return false;
//		} else if (!networkAnnotations.equals(other.networkAnnotations))
//			return false;
//		return true;
//	}
//	

}
