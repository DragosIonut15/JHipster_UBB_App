package nohaidragos.web.rest;

import nohaidragos.NohaidragosApp;
import nohaidragos.domain.CadruDidactic;
import nohaidragos.repository.CadruDidacticRepository;
import nohaidragos.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static nohaidragos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CadruDidacticResource} REST controller.
 */
@SpringBootTest(classes = NohaidragosApp.class)
public class CadruDidacticResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String DEFAULT_PRENUME = "AAAAAAAAAA";
    private static final String UPDATED_PRENUME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLU = "AAAAAAAAAA";
    private static final String UPDATED_TITLU = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_BIROU = 1;
    private static final Integer UPDATED_BIROU = 2;

    @Autowired
    private CadruDidacticRepository cadruDidacticRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCadruDidacticMockMvc;

    private CadruDidactic cadruDidactic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CadruDidacticResource cadruDidacticResource = new CadruDidacticResource(cadruDidacticRepository);
        this.restCadruDidacticMockMvc = MockMvcBuilders.standaloneSetup(cadruDidacticResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadruDidactic createEntity(EntityManager em) {
        CadruDidactic cadruDidactic = new CadruDidactic()
            .nume(DEFAULT_NUME)
            .prenume(DEFAULT_PRENUME)
            .titlu(DEFAULT_TITLU)
            .email(DEFAULT_EMAIL)
            .birou(DEFAULT_BIROU);
        return cadruDidactic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadruDidactic createUpdatedEntity(EntityManager em) {
        CadruDidactic cadruDidactic = new CadruDidactic()
            .nume(UPDATED_NUME)
            .prenume(UPDATED_PRENUME)
            .titlu(UPDATED_TITLU)
            .email(UPDATED_EMAIL)
            .birou(UPDATED_BIROU);
        return cadruDidactic;
    }

    @BeforeEach
    public void initTest() {
        cadruDidactic = createEntity(em);
    }

    @Test
    @Transactional
    public void createCadruDidactic() throws Exception {
        int databaseSizeBeforeCreate = cadruDidacticRepository.findAll().size();

        // Create the CadruDidactic
        restCadruDidacticMockMvc.perform(post("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isCreated());

        // Validate the CadruDidactic in the database
        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeCreate + 1);
        CadruDidactic testCadruDidactic = cadruDidacticList.get(cadruDidacticList.size() - 1);
        assertThat(testCadruDidactic.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testCadruDidactic.getPrenume()).isEqualTo(DEFAULT_PRENUME);
        assertThat(testCadruDidactic.getTitlu()).isEqualTo(DEFAULT_TITLU);
        assertThat(testCadruDidactic.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCadruDidactic.getBirou()).isEqualTo(DEFAULT_BIROU);
    }

    @Test
    @Transactional
    public void createCadruDidacticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cadruDidacticRepository.findAll().size();

        // Create the CadruDidactic with an existing ID
        cadruDidactic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadruDidacticMockMvc.perform(post("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isBadRequest());

        // Validate the CadruDidactic in the database
        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadruDidacticRepository.findAll().size();
        // set the field null
        cadruDidactic.setNume(null);

        // Create the CadruDidactic, which fails.

        restCadruDidacticMockMvc.perform(post("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isBadRequest());

        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadruDidacticRepository.findAll().size();
        // set the field null
        cadruDidactic.setPrenume(null);

        // Create the CadruDidactic, which fails.

        restCadruDidacticMockMvc.perform(post("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isBadRequest());

        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitluIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadruDidacticRepository.findAll().size();
        // set the field null
        cadruDidactic.setTitlu(null);

        // Create the CadruDidactic, which fails.

        restCadruDidacticMockMvc.perform(post("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isBadRequest());

        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCadruDidactics() throws Exception {
        // Initialize the database
        cadruDidacticRepository.saveAndFlush(cadruDidactic);

        // Get all the cadruDidacticList
        restCadruDidacticMockMvc.perform(get("/api/cadru-didactics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadruDidactic.getId().intValue())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME.toString())))
            .andExpect(jsonPath("$.[*].prenume").value(hasItem(DEFAULT_PRENUME.toString())))
            .andExpect(jsonPath("$.[*].titlu").value(hasItem(DEFAULT_TITLU.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].birou").value(hasItem(DEFAULT_BIROU)));
    }
    
    @Test
    @Transactional
    public void getCadruDidactic() throws Exception {
        // Initialize the database
        cadruDidacticRepository.saveAndFlush(cadruDidactic);

        // Get the cadruDidactic
        restCadruDidacticMockMvc.perform(get("/api/cadru-didactics/{id}", cadruDidactic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cadruDidactic.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()))
            .andExpect(jsonPath("$.prenume").value(DEFAULT_PRENUME.toString()))
            .andExpect(jsonPath("$.titlu").value(DEFAULT_TITLU.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.birou").value(DEFAULT_BIROU));
    }

    @Test
    @Transactional
    public void getNonExistingCadruDidactic() throws Exception {
        // Get the cadruDidactic
        restCadruDidacticMockMvc.perform(get("/api/cadru-didactics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCadruDidactic() throws Exception {
        // Initialize the database
        cadruDidacticRepository.saveAndFlush(cadruDidactic);

        int databaseSizeBeforeUpdate = cadruDidacticRepository.findAll().size();

        // Update the cadruDidactic
        CadruDidactic updatedCadruDidactic = cadruDidacticRepository.findById(cadruDidactic.getId()).get();
        // Disconnect from session so that the updates on updatedCadruDidactic are not directly saved in db
        em.detach(updatedCadruDidactic);
        updatedCadruDidactic
            .nume(UPDATED_NUME)
            .prenume(UPDATED_PRENUME)
            .titlu(UPDATED_TITLU)
            .email(UPDATED_EMAIL)
            .birou(UPDATED_BIROU);

        restCadruDidacticMockMvc.perform(put("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCadruDidactic)))
            .andExpect(status().isOk());

        // Validate the CadruDidactic in the database
        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeUpdate);
        CadruDidactic testCadruDidactic = cadruDidacticList.get(cadruDidacticList.size() - 1);
        assertThat(testCadruDidactic.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testCadruDidactic.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testCadruDidactic.getTitlu()).isEqualTo(UPDATED_TITLU);
        assertThat(testCadruDidactic.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCadruDidactic.getBirou()).isEqualTo(UPDATED_BIROU);
    }

    @Test
    @Transactional
    public void updateNonExistingCadruDidactic() throws Exception {
        int databaseSizeBeforeUpdate = cadruDidacticRepository.findAll().size();

        // Create the CadruDidactic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadruDidacticMockMvc.perform(put("/api/cadru-didactics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadruDidactic)))
            .andExpect(status().isBadRequest());

        // Validate the CadruDidactic in the database
        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCadruDidactic() throws Exception {
        // Initialize the database
        cadruDidacticRepository.saveAndFlush(cadruDidactic);

        int databaseSizeBeforeDelete = cadruDidacticRepository.findAll().size();

        // Delete the cadruDidactic
        restCadruDidacticMockMvc.perform(delete("/api/cadru-didactics/{id}", cadruDidactic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CadruDidactic> cadruDidacticList = cadruDidacticRepository.findAll();
        assertThat(cadruDidacticList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CadruDidactic.class);
        CadruDidactic cadruDidactic1 = new CadruDidactic();
        cadruDidactic1.setId(1L);
        CadruDidactic cadruDidactic2 = new CadruDidactic();
        cadruDidactic2.setId(cadruDidactic1.getId());
        assertThat(cadruDidactic1).isEqualTo(cadruDidactic2);
        cadruDidactic2.setId(2L);
        assertThat(cadruDidactic1).isNotEqualTo(cadruDidactic2);
        cadruDidactic1.setId(null);
        assertThat(cadruDidactic1).isNotEqualTo(cadruDidactic2);
    }
}
