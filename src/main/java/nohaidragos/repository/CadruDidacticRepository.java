package nohaidragos.repository;

import nohaidragos.domain.CadruDidactic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CadruDidactic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadruDidacticRepository extends JpaRepository<CadruDidactic, Long> {

}
