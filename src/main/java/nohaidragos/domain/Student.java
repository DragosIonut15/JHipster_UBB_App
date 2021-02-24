package nohaidragos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import nohaidragos.domain.enumeration.LiniaDeStudiu;

import nohaidragos.domain.enumeration.FormaInvatamant;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nume", nullable = false)
    private String nume;

    @NotNull
    @Column(name = "prenume", nullable = false)
    private String prenume;

    @Column(name = "numar_matricol")
    private Integer numarMatricol;

    @Column(name = "lucrare_licenta")
    private String lucrareLicenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "linia")
    private LiniaDeStudiu linia;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma")
    private FormaInvatamant forma;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private CadruDidactic cadruDidactic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public Student nume(String nume) {
        this.nume = nume;
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Student prenume(String prenume) {
        this.prenume = prenume;
        return this;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getNumarMatricol() {
        return numarMatricol;
    }

    public Student numarMatricol(Integer numarMatricol) {
        this.numarMatricol = numarMatricol;
        return this;
    }

    public void setNumarMatricol(Integer numarMatricol) {
        this.numarMatricol = numarMatricol;
    }

    public String getLucrareLicenta() {
        return lucrareLicenta;
    }

    public Student lucrareLicenta(String lucrareLicenta) {
        this.lucrareLicenta = lucrareLicenta;
        return this;
    }

    public void setLucrareLicenta(String lucrareLicenta) {
        this.lucrareLicenta = lucrareLicenta;
    }

    public LiniaDeStudiu getLinia() {
        return linia;
    }

    public Student linia(LiniaDeStudiu linia) {
        this.linia = linia;
        return this;
    }

    public void setLinia(LiniaDeStudiu linia) {
        this.linia = linia;
    }

    public FormaInvatamant getForma() {
        return forma;
    }

    public Student forma(FormaInvatamant forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(FormaInvatamant forma) {
        this.forma = forma;
    }

    public CadruDidactic getCadruDidactic() {
        return cadruDidactic;
    }

    public Student cadruDidactic(CadruDidactic cadruDidactic) {
        this.cadruDidactic = cadruDidactic;
        return this;
    }

    public void setCadruDidactic(CadruDidactic cadruDidactic) {
        this.cadruDidactic = cadruDidactic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            ", prenume='" + getPrenume() + "'" +
            ", numarMatricol=" + getNumarMatricol() +
            ", lucrareLicenta='" + getLucrareLicenta() + "'" +
            ", linia='" + getLinia() + "'" +
            ", forma='" + getForma() + "'" +
            "}";
    }
}
