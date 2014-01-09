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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_network_statements")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkStatementsDTO.findAll", query = "SELECT n FROM NetworkStatementsDTO n"),
    @NamedQuery(name = "NetworkStatementsDTO.findByRowId", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.rowid = :rowid"),
    @NamedQuery(name = "NetworkStatementsDTO.findByNetworkid", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.networkid = :networkid"),
    @NamedQuery(name = "NetworkStatementsDTO.findById", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.statementid = :statementid"),
    @NamedQuery(name = "NetworkStatementsDTO.findByStatementtype", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.statementtype = :statementtype"),
    @NamedQuery(name = "NetworkStatementsDTO.findByIstop", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.istop = :istop"),
    @NamedQuery(name = "NetworkStatementsDTO.findByIsarchived", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.isarchived = :isarchived"),
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
    @Column(name = "isarchived")
    private int isarchived;
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
    @ManyToOne(optional = false)
    private NetworksDTO networkDTO;

	public NetworkStatementsDTO() {
    }
	
	public NetworkStatementsDTO(String rowid)
	{
		this.rowid = rowid;
	}

    public NetworkStatementsDTO(String rowid,String networkid,String statementid, int istop, int isarchived, String statementtype, String updatedby, Date updateddate, String createdby, Date createddate) {
    	this.rowid = rowid;
    	this.networkid = networkid;
    	this.statementid = statementid;
        this.istop = istop;
        this.isarchived = isarchived;
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

    public int getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(int isarchived) {
        this.isarchived = isarchived;
    }
    
}
