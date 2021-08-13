package com.org.skillzag.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.org.skillzag.domain.Announcements} entity.
 */
public class AnnouncementsDTO implements Serializable {

    private Long id;

    private String announcementTitle;

    private String announcementDescription;

    private Instant calendar;

    private String emailID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public Instant getCalendar() {
        return calendar;
    }

    public void setCalendar(Instant calendar) {
        this.calendar = calendar;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnouncementsDTO)) {
            return false;
        }

        AnnouncementsDTO announcementsDTO = (AnnouncementsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, announcementsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnnouncementsDTO{" +
            "id=" + getId() +
            ", announcementTitle='" + getAnnouncementTitle() + "'" +
            ", announcementDescription='" + getAnnouncementDescription() + "'" +
            ", calendar='" + getCalendar() + "'" +
            ", emailID='" + getEmailID() + "'" +
            "}";
    }
}
