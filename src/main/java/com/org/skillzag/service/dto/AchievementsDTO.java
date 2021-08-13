package com.org.skillzag.service.dto;

import com.org.skillzag.domain.enumeration.CertificationType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.org.skillzag.domain.Achievements} entity.
 */
public class AchievementsDTO implements Serializable {

    private Long id;

    private String certificationTitle;

    private String certificateDescription;

    private CertificationType certificationType;

    private Instant dateOfCompletion;

    private Long certificationScore;

    private String uploadCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificationTitle() {
        return certificationTitle;
    }

    public void setCertificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public CertificationType getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    public Instant getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(Instant dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public Long getCertificationScore() {
        return certificationScore;
    }

    public void setCertificationScore(Long certificationScore) {
        this.certificationScore = certificationScore;
    }

    public String getUploadCertificate() {
        return uploadCertificate;
    }

    public void setUploadCertificate(String uploadCertificate) {
        this.uploadCertificate = uploadCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AchievementsDTO)) {
            return false;
        }

        AchievementsDTO achievementsDTO = (AchievementsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, achievementsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AchievementsDTO{" +
            "id=" + getId() +
            ", certificationTitle='" + getCertificationTitle() + "'" +
            ", certificateDescription='" + getCertificateDescription() + "'" +
            ", certificationType='" + getCertificationType() + "'" +
            ", dateOfCompletion='" + getDateOfCompletion() + "'" +
            ", certificationScore=" + getCertificationScore() +
            ", uploadCertificate='" + getUploadCertificate() + "'" +
            "}";
    }
}
