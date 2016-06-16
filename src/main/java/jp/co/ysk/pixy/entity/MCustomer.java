/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.ysk.pixy.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ko-aoki
 */
@Entity
@Table(name = "m_customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCustomer.findAll", query = "SELECT m FROM MCustomer m"),
    @NamedQuery(name = "MCustomer.findByCustomerId", query = "SELECT m FROM MCustomer m WHERE m.customerId = :customerId"),
    @NamedQuery(name = "MCustomer.findByFamilyName", query = "SELECT m FROM MCustomer m WHERE m.familyName = :familyName"),
    @NamedQuery(name = "MCustomer.findByFirstName", query = "SELECT m FROM MCustomer m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MCustomer.findByUpdateDatetime", query = "SELECT m FROM MCustomer m WHERE m.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "MCustomer.findByUpdateId", query = "SELECT m FROM MCustomer m WHERE m.updateId = :updateId"),
    @NamedQuery(name = "MCustomer.findByVersion", query = "SELECT m FROM MCustomer m WHERE m.version = :version")})
public class MCustomer extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "first_name")
    private String firstName;
    @JoinColumn(name = "business_connection_id", referencedColumnName = "business_connection_id")
    @ManyToOne
    private MBusinessConnection businessConnectionId;

    public MCustomer() {
    }

    public MCustomer(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public MBusinessConnection getBusinessConnectionId() {
        return businessConnectionId;
    }

    public void setBusinessConnectionId(MBusinessConnection businessConnectionId) {
        this.businessConnectionId = businessConnectionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCustomer)) {
            return false;
        }
        MCustomer other = (MCustomer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.MCustomer[ customerId=" + customerId + " ]";
    }
    
}
