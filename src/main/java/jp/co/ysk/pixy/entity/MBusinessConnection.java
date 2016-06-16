/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.ysk.pixy.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ko-aoki
 */
@Entity
@Table(name = "m_business_connection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MBusinessConnection.findAll", query = "SELECT m FROM MBusinessConnection m"),
    @NamedQuery(name = "MBusinessConnection.findByBusinessConnectionId", query = "SELECT m FROM MBusinessConnection m WHERE m.businessConnectionId = :businessConnectionId"),
    @NamedQuery(name = "MBusinessConnection.findByBusinessConnectionName", query = "SELECT m FROM MBusinessConnection m WHERE m.businessConnectionName = :businessConnectionName"),
    @NamedQuery(name = "MBusinessConnection.findByUpdateDatetime", query = "SELECT m FROM MBusinessConnection m WHERE m.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "MBusinessConnection.findByUpdateId", query = "SELECT m FROM MBusinessConnection m WHERE m.updateId = :updateId"),
    @NamedQuery(name = "MBusinessConnection.findByVersion", query = "SELECT m FROM MBusinessConnection m WHERE m.version = :version")})
public class MBusinessConnection extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "business_connection_id")
    private String businessConnectionId;
    @Column(name = "business_connection_name")
    private String businessConnectionName;

    @OneToMany(mappedBy = "businessConnectionId")
    private Collection<MCustomer> mCustomerCollection;

    public MBusinessConnection() {
    }

    public MBusinessConnection(String businessConnectionId) {
        this.businessConnectionId = businessConnectionId;
    }

    public String getBusinessConnectionId() {
        return businessConnectionId;
    }

    public void setBusinessConnectionId(String businessConnectionId) {
        this.businessConnectionId = businessConnectionId;
    }

    public String getBusinessConnectionName() {
        return businessConnectionName;
    }

    public void setBusinessConnectionName(String businessConnectionName) {
        this.businessConnectionName = businessConnectionName;
    }

    @XmlTransient
    public Collection<MCustomer> getMCustomerCollection() {
        return mCustomerCollection;
    }

    public void setMCustomerCollection(Collection<MCustomer> mCustomerCollection) {
        this.mCustomerCollection = mCustomerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessConnectionId != null ? businessConnectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBusinessConnection)) {
            return false;
        }
        MBusinessConnection other = (MBusinessConnection) object;
        if ((this.businessConnectionId == null && other.businessConnectionId != null) || (this.businessConnectionId != null && !this.businessConnectionId.equals(other.businessConnectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.MBusinessConnection[ businessConnectionId=" + businessConnectionId + " ]";
    }
    
}
