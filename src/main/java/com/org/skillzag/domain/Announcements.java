package com.org.skillzag.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Announcements.
 */
@Entity
@Table(name = "announcements")
public class Announcements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "announcement_title")
    private String announcementTitle;

    @Column(name = "announcement_description")
    private String announcementDescription;

    @Column(name = "calendar")
    private Instant calendar;

    @Column(name = "email_id")
    private String emailID;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Announcements id(Long id) {
        this.id = id;
        return this;
    }

    public String getAnnouncementTitle() {
        return this.announcementTitle;
    }

    public Announcements announcementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
        return this;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementDescription() {
        return this.announcementDescription;
    }

    public Announcements announcementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
        return this;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public Instant getCalendar() {
        return this.calendar;
    }

    public Announcements calendar(Instant calendar) {
        this.calendar = calendar;
        return this;
    }

    public void setCalendar(Instant calendar) {
        this.calendar = calendar;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public Announcements emailID(String emailID) {
        this.emailID = emailID;
        return this;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Announcements)) {
            return false;
        }
        return id != null && id.equals(((Announcements) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Announcements{" +
            "id=" + getId() +
            ", announcementTitle='" + getAnnouncementTitle() + "'" +
            ", announcementDescription='" + getAnnouncementDescription() + "'" +
            ", calendar='" + getCalendar() + "'" +
            ", emailID='" + getEmailID() + "'" +
            "}";
    }
}
