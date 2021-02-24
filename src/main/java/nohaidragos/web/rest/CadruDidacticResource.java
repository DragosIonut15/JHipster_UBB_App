package nohaidragos.web.rest;

import nohaidragos.domain.CadruDidactic;
import nohaidragos.repository.CadruDidacticRepository;
import nohaidragos.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link nohaidragos.domain.CadruDidactic}.
 */
@RestController
@RequestMapping("/api")
public class CadruDidacticResource {

    private final Logger log = LoggerFactory.getLogger(CadruDidacticResource.class);

    private static final String ENTITY_NAME = "cadruDidactic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CadruDidacticRepository cadruDidacticRepository;

    public CadruDidacticResource(CadruDidacticRepository cadruDidacticRepository) {
        this.cadruDidacticRepository = cadruDidacticRepository;
    }

    /**
     * {@code POST  /cadru-didactics} : Create a new cadruDidactic.
     *
     * @param cadruDidactic the cadruDidactic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cadruDidactic, or with status {@code 400 (Bad Request)} if the cadruDidactic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cadru-didactics")
    public ResponseEntity<CadruDidactic> createCadruDidactic(@Valid @RequestBody CadruDidactic cadruDidactic) throws URISyntaxException {
        log.debug("REST request to save CadruDidactic : {}", cadruDidactic);
        if (cadruDidactic.getId() != null) {
            throw new BadRequestAlertException("A new cadruDidactic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CadruDidactic result = cadruDidacticRepository.save(cadruDidactic);
        return ResponseEntity.created(new URI("/api/cadru-didactics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cadru-didactics} : Updates an existing cadruDidactic.
     *
     * @param cadruDidactic the cadruDidactic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadruDidactic,
     * or with status {@code 400 (Bad Request)} if the cadruDidactic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cadruDidactic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cadru-didactics")
    public ResponseEntity<CadruDidactic> updateCadruDidactic(@Valid @RequestBody CadruDidactic cadruDidactic) throws URISyntaxException {
        log.debug("REST request to update CadruDidactic : {}", cadruDidactic);
        if (cadruDidactic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CadruDidactic result = cadruDidacticRepository.save(cadruDidactic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cadruDidactic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cadru-didactics} : get all the cadruDidactics.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadruDidactics in body.
     */
    @GetMapping("/cadru-didactics")
    public ResponseEntity<List<CadruDidactic>> getAllCadruDidactics(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CadruDidactics");
        Page<CadruDidactic> page = cadruDidacticRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cadru-didactics/:id} : get the "id" cadruDidactic.
     *
     * @param id the id of the cadruDidactic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cadruDidactic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cadru-didactics/{id}")
    public ResponseEntity<CadruDidactic> getCadruDidactic(@PathVariable Long id) {
        log.debug("REST request to get CadruDidactic : {}", id);
        Optional<CadruDidactic> cadruDidactic = cadruDidacticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cadruDidactic);
    }

    /**
     * {@code DELETE  /cadru-didactics/:id} : delete the "id" cadruDidactic.
     *
     * @param id the id of the cadruDidactic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cadru-didactics/{id}")
    public ResponseEntity<Void> deleteCadruDidactic(@PathVariable Long id) {
        log.debug("REST request to delete CadruDidactic : {}", id);
        cadruDidacticRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
