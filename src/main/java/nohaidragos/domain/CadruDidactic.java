package nohaidragos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CadruDidactic.
 */
@Entity
@Table(name = "cadru_didactic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CadruDidactic implements Serializable {

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

    @NotNull
    @Column(name = "titlu", nullable = false)
    private String titlu;

    @Column(name = "email")
    private String email;

    @Column(name = "birou")
    private Integer birou;

    @ManyToOne
    @JsonIgnoreProperties("cadruDidactics")
    private Departament departament;

    @OneToMany(mappedBy = "cadruDidactic")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

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

    public CadruDidactic nume(String nume) {
        this.nume = nume;
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public CadruDidactic prenume(String prenume) {
        this.prenume = prenume;
        return this;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTitlu() {
        return titlu;
    }

    public CadruDidactic titlu(String titlu) {
        this.titlu = titlu;
        return this;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getEmail() {
        return email;
    }

    public CadruDidactic email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirou() {
        return birou;
    }

    public CadruDidactic birou(Integer birou) {
        this.birou = birou;
        return this;
    }

    public void setBirou(Integer birou) {
        this.birou = birou;
    }

    public Departament getDepartament() {
        return departament;
    }

    public CadruDidactic departament(Departament departament) {
        this.departament = departament;
        return this;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public CadruDidactic students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public CadruDidactic addStudent(Student student) {
        this.students.add(student);
        student.setCadruDidactic(this);
        return this;
    }

    public CadruDidactic removeStudent(Student student) {
        this.students.remove(student);
        student.setCadruDidactic(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CadruDidactic)) {
            return false;
        }
        return id != null && id.equals(((CadruDidactic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CadruDidactic{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            ", prenume='" + getPrenume() + "'" +
            ", titlu='" + getTitlu() + "'" +
            ", email='" + getEmail() + "'" +
            ", birou=" + getBirou() +
            "}";
    }
}
