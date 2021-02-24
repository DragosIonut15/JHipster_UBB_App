package nohaidragos.repository;

import nohaidragos.domain.ProgramStudiu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProgramStudiu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramStudiuRepository extends JpaRepository<ProgramStudiu, Long> {

}
