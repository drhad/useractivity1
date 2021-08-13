package com.org.skillzag.domain;

import com.org.skillzag.domain.enumeration.CertificationType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Achievements.
 */
@Entity
@Table(name = "achievements")
public class Achievements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "certification_title")
    private String certificationTitle;

    @Column(name = "certificate_description")
    private String certificateDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "certification_type")
    private CertificationType certificationType;

    @Column(name = "date_of_completion")
    private Instant dateOfCompletion;

    @Column(name = "certification_score")
    private Long certificationScore;

    @Column(name = "upload_certificate")
    private String uploadCertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Achievements id(Long id) {
        this.id = id;
        return this;
    }

    public String getCertificationTitle() {
        return this.certificationTitle;
    }

    public Achievements certificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
        return this;
    }

    public void setCertificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
    }

    public String getCertificateDescription() {
        return this.certificateDescription;
    }

    public Achievements certificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
        return this;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public CertificationType getCertificationType() {
        return this.certificationType;
    }

    public Achievements certificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
        return this;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    public Instant getDateOfCompletion() {
        return this.dateOfCompletion;
    }

    public Achievements dateOfCompletion(Instant dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
        return this;
    }

    public void setDateOfCompletion(Instant dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public Long getCertificationScore() {
        return this.certificationScore;
    }

    public Achievements certificationScore(Long certificationScore) {
        this.certificationScore = certificationScore;
        return this;
    }

    public void setCertificationScore(Long certificationScore) {
        this.certificationScore = certificationScore;
    }

    public String getUploadCertificate() {
        return this.uploadCertificate;
    }

    public Achievements uploadCertificate(String uploadCertificate) {
        this.uploadCertificate = uploadCertificate;
        return this;
    }

    public void setUploadCertificate(String uploadCertificate) {
        this.uploadCertificate = uploadCertificate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Achievements)) {
            return false;
        }
        return id != null && id.equals(((Achievements) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Achievements{" +
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
