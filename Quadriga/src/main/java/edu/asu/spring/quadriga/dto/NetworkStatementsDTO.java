/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *This class represents the column mappings for 
 *network statements table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_network_statements")
@NamedQueries({
    @NamedQuery(name = "NetworkStatementsDTO.findAll", query = "SELECT n FROM NetworkStatementsDTO n"),
    @NamedQuery(name = "NetworkStatementsDTO.findByRowId", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.rowid = :rowid"),
    @NamedQuery(name = "NetworkStatementsDTO.findByNetworkid", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.networkid = :networkid"),
    @NamedQuery(name = "NetworkStatementsDTO.findById", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.statementid = :statementid"),
    @NamedQuery(name = "NetworkStatementsDTO.findByStatementtype", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.statementtype = :statementtype"),
    @NamedQuery(name = "NetworkStatementsDTO.findByIstop", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.istop = :istop"),
    @NamedQuery(name = "NetworkStatementsDTO.findByVersion", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.version = :version"),
    })
public class NetworkStatementsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "rowid")
    private String rowid;
	@Basic(optional = false)
	@Column(name = "networkid")
	private String networkid;
	@Basic(optional = false)
	@Column(name = "statementid")
	private String statementid;
    @Basic(optional = false)
    @Column(name = "istop")
    private int istop;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
	@Basic(optional = false)
    @Column(name = "statementtype")
    private String statementtype;
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
    @JoinColumn(name = "networkid", referencedColumnName = "networkid",insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NetworksDTO networkDTO;

	public NetworkStatementsDTO() {
    }
	
    public NetworkStatementsDTO(String rowid,String networkid,String statementid, int istop, int version, String statementtype, String updatedby, Date updateddate, String createdby, Date createddate) {
    	this.rowid = rowid;
    	this.networkid = networkid;
    	this.statementid = statementid;
        this.istop = istop;
        this.version = version;
        this.statementtype = statementtype;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }
    
    public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getNetworkid() {
		return networkid;
	}

	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}

	public String getStatementid() {
		return statementid;
	}

	public void setStatementid(String statementid) {
		this.statementid = statementid;
	}

    public String getStatementtype() {
        return statementtype;
    }

    public void setStatementtype(String statementtype) {
        this.statementtype = statementtype;
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
    
    public int getIstop() {
        return istop;
    }

    public void setIstop(int istop) {
        this.istop = istop;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdby == null) ? 0 : createdby.hashCode());
		result = prime * result
				+ ((createddate == null) ? 0 : createddate.hashCode());
		result = prime * result + istop;
		result = prime * result
				+ ((networkDTO == null) ? 0 : networkDTO.hashCode());
		result = prime * result
				+ ((networkid == null) ? 0 : networkid.hashCode());
		result = prime * result + ((rowid == null) ? 0 : rowid.hashCode());
		result = prime * result
				+ ((statementid == null) ? 0 : statementid.hashCode());
		result = prime * result
				+ ((statementtype == null) ? 0 : statementtype.hashCode());
		result = prime * result
				+ ((updatedby == null) ? 0 : updatedby.hashCode());
		result = prime * result
				+ ((updateddate == null) ? 0 : updateddate.hashCode());
		result = prime * result + version;
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
		NetworkStatementsDTO other = (NetworkStatementsDTO) obj;
		if (createdby == null) {
			if (other.createdby != null)
				return false;
		} else if (!createdby.equals(other.createdby))
			return false;
		if (createddate == null) {
			if (other.createddate != null)
				return false;
		} else if (!createddate.equals(other.createddate))
			return false;
		if (istop != other.istop)
			return false;
		if (networkDTO == null) {
			if (other.networkDTO != null)
				return false;
		} else if (!networkDTO.equals(other.networkDTO))
			return false;
		if (networkid == null) {
			if (other.networkid != null)
				return false;
		} else if (!networkid.equals(other.networkid))
			return false;
		if (rowid == null) {
			if (other.rowid != null)
				return false;
		} else if (!rowid.equals(other.rowid))
			return false;
		if (statementid == null) {
			if (other.statementid != null)
				return false;
		} else if (!statementid.equals(other.statementid))
			return false;
		if (statementtype == null) {
			if (other.statementtype != null)
				return false;
		} else if (!statementtype.equals(other.statementtype))
			return false;
		if (updatedby == null) {
			if (other.updatedby != null)
				return false;
		} else if (!updatedby.equals(other.updatedby))
			return false;
		if (updateddate == null) {
			if (other.updateddate != null)
				return false;
		} else if (!updateddate.equals(other.updateddate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
    
    
}
