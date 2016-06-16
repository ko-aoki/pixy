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
@Table(name = "t_attendees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TAttendees.findAll", query = "SELECT t FROM TAttendees t"),
    @NamedQuery(name = "TAttendees.findById", query = "SELECT t FROM TAttendees t WHERE t.id = :id"),
    @NamedQuery(name = "TAttendees.findByEventId", query = "SELECT t FROM TAttendees t WHERE t.eventId = :eventId"),
    @NamedQuery(name = "TAttendees.findByEmail", query = "SELECT t FROM TAttendees t WHERE t.email = :email"),
    @NamedQuery(name = "TAttendees.findByUpdateDatetime", query = "SELECT t FROM TAttendees t WHERE t.updateDatetime = :updateDatetime"),
    @NamedQuery(name = "TAttendees.findByUpdateId", query = "SELECT t FROM TAttendees t WHERE t.updateId = :updateId"),
    @NamedQuery(name = "TAttendees.findByVersion", query = "SELECT t FROM TAttendees t WHERE t.version = :version")})
public class TAttendees extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "email")
    private String email;

    @JoinColumn(name = "t_event_tmp_id", referencedColumnName = "t_event_tmp_id")
    @ManyToOne(optional = false)
    private TEventTmp tEventTmpId;

    public TAttendees() {
    }

    public TAttendees(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TEventTmp getTEventTmpId() {
        return tEventTmpId;
    }

    public void setTEventTmpId(TEventTmp tEventTmpId) {
        this.tEventTmpId = tEventTmpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TAttendees)) {
            return false;
        }
        TAttendees other = (TAttendees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.TAttendees[ id=" + id + " ]";
    }
    
}
