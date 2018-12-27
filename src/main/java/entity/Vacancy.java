package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "vacancy", schema = "job")
public class Vacancy implements Serializable {
    private static final long serialVersionUID = -1256343939961956174L;

    private Integer vacancyId;
    private String companyName;
    private String vacancyTitle;
    private String link;
    private Date updated;
    private Boolean isNew;

    public Vacancy(String companyName, String title, String link, Date updated, Boolean isNew) {
        this.companyName = companyName;
        this.vacancyTitle = title;
        this.link = link;
        this.updated = updated;
        this.isNew = isNew;
    }

    public Vacancy() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancyId", unique = true, nullable = false)
    public Integer getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Integer vacancyId) {
        this.vacancyId = vacancyId;
    }

    @Column(name = "companyName", nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "vacancyTitle", nullable = false)
    public String getVacancyTitle() {
        return vacancyTitle;
    }

    public void setVacancyTitle(String vacancyTitle) {
        this.vacancyTitle = vacancyTitle;
    }

    @Column(name = "link", nullable = false)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "updated", nullable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Basic
    @Column(name = "isNew", nullable = false)
    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean active) {
        isNew = active;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", companyName='" + companyName + '\'' +
                ", vacancyTitle='" + vacancyTitle + '\'' +
                ", link='" + link + '\'' +
                ", updated=" + updated +
                ", isNew=" + isNew +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return companyName.equals(vacancy.companyName) &&
                vacancyTitle.equals(vacancy.vacancyTitle) &&
                link.equals(vacancy.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, vacancyTitle, link);
    }
}
