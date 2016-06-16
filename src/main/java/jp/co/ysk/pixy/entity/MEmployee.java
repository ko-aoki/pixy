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
@Table(name = "m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MEmployee.findAll", query = "SELECT m FROM MEmployee m"),
    @NamedQuery(name = "MEmployee.findByEmployeeId", query = "SELECT m FROM MEmployee m WHERE m.employeeId = :employeeId"),
    @NamedQuery(name = "MEmployee.findByCalendarId", query = "SELECT m FROM MEmployee m WHERE m.calendarId = :calendarId"),
    @NamedQuery(name = "MEmployee.findByFamilyName", query = "SELECT m FROM MEmployee m WHERE m.familyName = :familyName"),
    @NamedQuery(name = "MEmployee.findByFirstName", query = "SELECT m FROM MEmployee m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MEmployee.findByCellphoneNumber", query = "SELECT m FROM MEmployee m WHERE m.cellphoneNumber = :cellphoneNumber"),
    @NamedQuery(name = "MEmployee.findByExtensionNumber", query = "SELECT m FROM MEmployee m WHERE m.extensionNumber = :extensionNumber"),
    @NamedQuery(name = "MEmployee.findByImageFilePath", query = "SELECT m FROM MEmployee m WHERE m.imageFilePath = :imageFilePath"),
    @NamedQuery(name = "MEmployee.findByUpdateDatetime", query = "SELECT m FROM MEmployee m WHERE m.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "MEmployee.findByUpdateId", query = "SELECT m FROM MEmployee m WHERE m.updateId = :updateId"),
    @NamedQuery(name = "MEmployee.findByVersion", query = "SELECT m FROM MEmployee m WHERE m.version = :version")})
public class MEmployee extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "calendar_id")
    private String calendarId;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "cellphone_number")
    private String cellphoneNumber;
    @Column(name = "extension_number")
    private String extensionNumber;
    @Column(name = "image_file_path")
    private String imageFilePath;

    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne
    private MDepartment departmentId;

    public MEmployee() {
    }

    public MEmployee(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
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

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public MDepartment getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(MDepartment departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MEmployee)) {
            return false;
        }
        MEmployee other = (MEmployee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.MEmployee[ employeeId=" + employeeId + " ]";
    }
    
}
