package nohaidragos.web.rest;

import nohaidragos.domain.ProgramStudiu;
import nohaidragos.repository.ProgramStudiuRepository;
import nohaidragos.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link nohaidragos.domain.ProgramStudiu}.
 */
@RestController
@RequestMapping("/api")
public class ProgramStudiuResource {

    private final Logger log = LoggerFactory.getLogger(ProgramStudiuResource.class);

    private static final String ENTITY_NAME = "programStudiu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramStudiuRepository programStudiuRepository;

    public ProgramStudiuResource(ProgramStudiuRepository programStudiuRepository) {
        this.programStudiuRepository = programStudiuRepository;
    }

    /**
     * {@code POST  /program-studius} : Create a new programStudiu.
     *
     * @param programStudiu the programStudiu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programStudiu, or with status {@code 400 (Bad Request)} if the programStudiu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/program-studius")
    public ResponseEntity<ProgramStudiu> createProgramStudiu(@RequestBody ProgramStudiu programStudiu) throws URISyntaxException {
        log.debug("REST request to save ProgramStudiu : {}", programStudiu);
        if (programStudiu.getId() != null) {
            throw new BadRequestAlertException("A new programStudiu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramStudiu result = programStudiuRepository.save(programStudiu);
        return ResponseEntity.created(new URI("/api/program-studius/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /program-studius} : Updates an existing programStudiu.
     *
     * @param programStudiu the programStudiu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programStudiu,
     * or with status {@code 400 (Bad Request)} if the programStudiu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programStudiu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/program-studius")
    public ResponseEntity<ProgramStudiu> updateProgramStudiu(@RequestBody ProgramStudiu programStudiu) throws URISyntaxException {
        log.debug("REST request to update ProgramStudiu : {}", programStudiu);
        if (programStudiu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgramStudiu result = programStudiuRepository.save(programStudiu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programStudiu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /program-studius} : get all the programStudius.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programStudius in body.
     */
    @GetMapping("/program-studius")
    public List<ProgramStudiu> getAllProgramStudius() {
        log.debug("REST request to get all ProgramStudius");
        return programStudiuRepository.findAll();
    }

    /**
     * {@code GET  /program-studius/:id} : get the "id" programStudiu.
     *
     * @param id the id of the programStudiu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programStudiu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-studius/{id}")
    public ResponseEntity<ProgramStudiu> getProgramStudiu(@PathVariable Long id) {
        log.debug("REST request to get ProgramStudiu : {}", id);
        Optional<ProgramStudiu> programStudiu = programStudiuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programStudiu);
    }

    /**
     * {@code DELETE  /program-studius/:id} : delete the "id" programStudiu.
     *
     * @param id the id of the programStudiu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/program-studius/{id}")
    public ResponseEntity<Void> deleteProgramStudiu(@PathVariable Long id) {
        log.debug("REST request to delete ProgramStudiu : {}", id);
        programStudiuRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
