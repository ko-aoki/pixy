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
@Table(name = "m_department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MDepartment.findAll", query = "SELECT m FROM MDepartment m"),
    @NamedQuery(name = "MDepartment.findByDepartmentId", query = "SELECT m FROM MDepartment m WHERE m.departmentId = :departmentId"),
    @NamedQuery(name = "MDepartment.findByDepartmentName1", query = "SELECT m FROM MDepartment m WHERE m.departmentName1 = :departmentName1"),
    @NamedQuery(name = "MDepartment.findByDepartmentName2", query = "SELECT m FROM MDepartment m WHERE m.departmentName2 = :departmentName2"),
    @NamedQuery(name = "MDepartment.findByUpdateDatetime", query = "SELECT m FROM MDepartment m WHERE m.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "MDepartment.findByUpdateId", query = "SELECT m FROM MDepartment m WHERE m.updateId = :updateId"),
    @NamedQuery(name = "MDepartment.findByVersion", query = "SELECT m FROM MDepartment m WHERE m.version = :version")})
public class MDepartment extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "department_id")
    private String departmentId;
    @Column(name = "department_name1")
    private String departmentName1;
    @Column(name = "department_name2")
    private String departmentName2;

    @OneToMany(mappedBy = "departmentId")
    private Collection<MEmployee> mEmployeeCollection;

    public MDepartment() {
    }

    public MDepartment(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName1() {
        return departmentName1;
    }

    public void setDepartmentName1(String departmentName1) {
        this.departmentName1 = departmentName1;
    }

    public String getDepartmentName2() {
        return departmentName2;
    }

    public void setDepartmentName2(String departmentName2) {
        this.departmentName2 = departmentName2;
    }

    @XmlTransient
    public Collection<MEmployee> getMEmployeeCollection() {
        return mEmployeeCollection;
    }

    public void setMEmployeeCollection(Collection<MEmployee> mEmployeeCollection) {
        this.mEmployeeCollection = mEmployeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentId != null ? departmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MDepartment)) {
            return false;
        }
        MDepartment other = (MDepartment) object;
        if ((this.departmentId == null && other.departmentId != null) || (this.departmentId != null && !this.departmentId.equals(other.departmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.MDepartment[ departmentId=" + departmentId + " ]";
    }
    
}
