package nohaidragos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProgramStudiu.
 */
@Entity
@Table(name = "program_studiu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProgramStudiu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program")
    private String program;

    @ManyToOne
    @JsonIgnoreProperties("programStudius")
    private Departament departament;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public ProgramStudiu program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Departament getDepartament() {
        return departament;
    }

    public ProgramStudiu departament(Departament departament) {
        this.departament = departament;
        return this;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramStudiu)) {
            return false;
        }
        return id != null && id.equals(((ProgramStudiu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProgramStudiu{" +
            "id=" + getId() +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
