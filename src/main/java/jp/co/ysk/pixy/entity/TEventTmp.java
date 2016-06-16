/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.ysk.pixy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "t_event_tmp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TEventTmp.findAll", query = "SELECT t FROM TEventTmp t"),
    @NamedQuery(name = "TEventTmp.findByTEventTmpId", query = "SELECT t FROM TEventTmp t WHERE t.tEventTmpId = :tEventTmpId"),
    @NamedQuery(name = "TEventTmp.findByEventId", query = "SELECT t FROM TEventTmp t WHERE t.eventId = :eventId"),
    @NamedQuery(name = "TEventTmp.findByCalendarId", query = "SELECT t FROM TEventTmp t WHERE t.calendarId = :calendarId"),
    @NamedQuery(name = "TEventTmp.findBySummary", query = "SELECT t FROM TEventTmp t WHERE t.summary = :summary"),
    @NamedQuery(name = "TEventTmp.findByStartDatetime", query = "SELECT t FROM TEventTmp t WHERE t.startDatetime = :startDatetime"),
    @NamedQuery(name = "TEventTmp.findByEndDatetime", query = "SELECT t FROM TEventTmp t WHERE t.endDatetime = :endDatetime"),
    @NamedQuery(name = "TEventTmp.findByDescription", query = "SELECT t FROM TEventTmp t WHERE t.description = :description"),
    @NamedQuery(name = "TEventTmp.findByAdmissionDatetime", query = "SELECT t FROM TEventTmp t WHERE t.admissionDatetime = :admissionDatetime"),
    @NamedQuery(name = "TEventTmp.findByLeavingDatetime", query = "SELECT t FROM TEventTmp t WHERE t.leavingDatetime = :leavingDatetime"),
    @NamedQuery(name = "TEventTmp.findByUpdateDatetime", query = "SELECT t FROM TEventTmp t WHERE t.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "TEventTmp.findByUpdateId", query = "SELECT t FROM TEventTmp t WHERE t.updateId = :updateId"),
    @NamedQuery(name = "TEventTmp.findByVersion", query = "SELECT t FROM TEventTmp t WHERE t.version = :version")})
public class TEventTmp extends AuditEntity implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tEventTmpId")
    private Collection<TResource> tResourceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tEventTmpId")
    private Collection<TAttendees> tAttendeesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_event_tmp_id")
    private Long tEventTmpId;
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "calendar_id")
    private String calendarId;
    @Column(name = "summary")
    private String summary;
    @Column(name = "start_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDatetime;
    @Column(name = "end_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDatetime;
    @Column(name = "description")
    private String description;
    @Column(name = "admission_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date admissionDatetime;
    @Column(name = "leaving_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date leavingDatetime;

    public TEventTmp() {
    }

    public TEventTmp(Long tEventTmpId) {
        this.tEventTmpId = tEventTmpId;
    }

    public Long getTEventTmpId() {
        return tEventTmpId;
    }

    public void setTEventTmpId(Long tEventTmpId) {
        this.tEventTmpId = tEventTmpId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAdmissionDatetime() {
        return admissionDatetime;
    }

    public void setAdmissionDatetime(Date admissionDatetime) {
        this.admissionDatetime = admissionDatetime;
    }

    public Date getLeavingDatetime() {
        return leavingDatetime;
    }

    public void setLeavingDatetime(Date leavingDatetime) {
        this.leavingDatetime = leavingDatetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tEventTmpId != null ? tEventTmpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TEventTmp)) {
            return false;
        }
        TEventTmp other = (TEventTmp) object;
        if ((this.tEventTmpId == null && other.tEventTmpId != null) || (this.tEventTmpId != null && !this.tEventTmpId.equals(other.tEventTmpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.TEventTmp[ tEventTmpId=" + tEventTmpId + " ]";
    }

    @XmlTransient
    public Collection<TResource> getTResourceCollection() {
        return tResourceCollection;
    }

    public void setTResourceCollection(Collection<TResource> tResourceCollection) {
        this.tResourceCollection = tResourceCollection;
    }

    @XmlTransient
    public Collection<TAttendees> getTAttendeesCollection() {
        return tAttendeesCollection;
    }

    public void setTAttendeesCollection(Collection<TAttendees> tAttendeesCollection) {
        this.tAttendeesCollection = tAttendeesCollection;
    }
    
}
