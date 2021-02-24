package nohaidragos.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Departament.
 */
@Entity
@Table(name = "departament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Departament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nume_departament", nullable = false)
    private String numeDepartament;

    @OneToMany(mappedBy = "departament")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProgramStudiu> programStudius = new HashSet<>();

    @OneToMany(mappedBy = "departament")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CadruDidactic> cadruDidactics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeDepartament() {
        return numeDepartament;
    }

    public Departament numeDepartament(String numeDepartament) {
        this.numeDepartament = numeDepartament;
        return this;
    }

    public void setNumeDepartament(String numeDepartament) {
        this.numeDepartament = numeDepartament;
    }

    public Set<ProgramStudiu> getProgramStudius() {
        return programStudius;
    }

    public Departament programStudius(Set<ProgramStudiu> programStudius) {
        this.programStudius = programStudius;
        return this;
    }

    public Departament addProgramStudiu(ProgramStudiu programStudiu) {
        this.programStudius.add(programStudiu);
        programStudiu.setDepartament(this);
        return this;
    }

    public Departament removeProgramStudiu(ProgramStudiu programStudiu) {
        this.programStudius.remove(programStudiu);
        programStudiu.setDepartament(null);
        return this;
    }

    public void setProgramStudius(Set<ProgramStudiu> programStudius) {
        this.programStudius = programStudius;
    }

    public Set<CadruDidactic> getCadruDidactics() {
        return cadruDidactics;
    }

    public Departament cadruDidactics(Set<CadruDidactic> cadruDidactics) {
        this.cadruDidactics = cadruDidactics;
        return this;
    }

    public Departament addCadruDidactic(CadruDidactic cadruDidactic) {
        this.cadruDidactics.add(cadruDidactic);
        cadruDidactic.setDepartament(this);
        return this;
    }

    public Departament removeCadruDidactic(CadruDidactic cadruDidactic) {
        this.cadruDidactics.remove(cadruDidactic);
        cadruDidactic.setDepartament(null);
        return this;
    }

    public void setCadruDidactics(Set<CadruDidactic> cadruDidactics) {
        this.cadruDidactics = cadruDidactics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departament)) {
            return false;
        }
        return id != null && id.equals(((Departament) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Departament{" +
            "id=" + getId() +
            ", numeDepartament='" + getNumeDepartament() + "'" +
            "}";
    }
}
