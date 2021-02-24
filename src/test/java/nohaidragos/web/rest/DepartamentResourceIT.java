package nohaidragos.web.rest;

import nohaidragos.NohaidragosApp;
import nohaidragos.domain.Departament;
import nohaidragos.repository.DepartamentRepository;
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
 * Integration tests for the {@Link DepartamentResource} REST controller.
 */
@SpringBootTest(classes = NohaidragosApp.class)
public class DepartamentResourceIT {

    private static final String DEFAULT_NUME_DEPARTAMENT = "AAAAAAAAAA";
    private static final String UPDATED_NUME_DEPARTAMENT = "BBBBBBBBBB";

    @Autowired
    private DepartamentRepository departamentRepository;

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

    private MockMvc restDepartamentMockMvc;

    private Departament departament;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartamentResource departamentResource = new DepartamentResource(departamentRepository);
        this.restDepartamentMockMvc = MockMvcBuilders.standaloneSetup(departamentResource)
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
    public static Departament createEntity(EntityManager em) {
        Departament departament = new Departament()
            .numeDepartament(DEFAULT_NUME_DEPARTAMENT);
        return departament;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departament createUpdatedEntity(EntityManager em) {
        Departament departament = new Departament()
            .numeDepartament(UPDATED_NUME_DEPARTAMENT);
        return departament;
    }

    @BeforeEach
    public void initTest() {
        departament = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartament() throws Exception {
        int databaseSizeBeforeCreate = departamentRepository.findAll().size();

        // Create the Departament
        restDepartamentMockMvc.perform(post("/api/departaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departament)))
            .andExpect(status().isCreated());

        // Validate the Departament in the database
        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeCreate + 1);
        Departament testDepartament = departamentList.get(departamentList.size() - 1);
        assertThat(testDepartament.getNumeDepartament()).isEqualTo(DEFAULT_NUME_DEPARTAMENT);
    }

    @Test
    @Transactional
    public void createDepartamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departamentRepository.findAll().size();

        // Create the Departament with an existing ID
        departament.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentMockMvc.perform(post("/api/departaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departament)))
            .andExpect(status().isBadRequest());

        // Validate the Departament in the database
        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeDepartamentIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentRepository.findAll().size();
        // set the field null
        departament.setNumeDepartament(null);

        // Create the Departament, which fails.

        restDepartamentMockMvc.perform(post("/api/departaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departament)))
            .andExpect(status().isBadRequest());

        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartaments() throws Exception {
        // Initialize the database
        departamentRepository.saveAndFlush(departament);

        // Get all the departamentList
        restDepartamentMockMvc.perform(get("/api/departaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departament.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeDepartament").value(hasItem(DEFAULT_NUME_DEPARTAMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getDepartament() throws Exception {
        // Initialize the database
        departamentRepository.saveAndFlush(departament);

        // Get the departament
        restDepartamentMockMvc.perform(get("/api/departaments/{id}", departament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departament.getId().intValue()))
            .andExpect(jsonPath("$.numeDepartament").value(DEFAULT_NUME_DEPARTAMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartament() throws Exception {
        // Get the departament
        restDepartamentMockMvc.perform(get("/api/departaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartament() throws Exception {
        // Initialize the database
        departamentRepository.saveAndFlush(departament);

        int databaseSizeBeforeUpdate = departamentRepository.findAll().size();

        // Update the departament
        Departament updatedDepartament = departamentRepository.findById(departament.getId()).get();
        // Disconnect from session so that the updates on updatedDepartament are not directly saved in db
        em.detach(updatedDepartament);
        updatedDepartament
            .numeDepartament(UPDATED_NUME_DEPARTAMENT);

        restDepartamentMockMvc.perform(put("/api/departaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartament)))
            .andExpect(status().isOk());

        // Validate the Departament in the database
        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeUpdate);
        Departament testDepartament = departamentList.get(departamentList.size() - 1);
        assertThat(testDepartament.getNumeDepartament()).isEqualTo(UPDATED_NUME_DEPARTAMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartament() throws Exception {
        int databaseSizeBeforeUpdate = departamentRepository.findAll().size();

        // Create the Departament

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentMockMvc.perform(put("/api/departaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departament)))
            .andExpect(status().isBadRequest());

        // Validate the Departament in the database
        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartament() throws Exception {
        // Initialize the database
        departamentRepository.saveAndFlush(departament);

        int databaseSizeBeforeDelete = departamentRepository.findAll().size();

        // Delete the departament
        restDepartamentMockMvc.perform(delete("/api/departaments/{id}", departament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departament> departamentList = departamentRepository.findAll();
        assertThat(departamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departament.class);
        Departament departament1 = new Departament();
        departament1.setId(1L);
        Departament departament2 = new Departament();
        departament2.setId(departament1.getId());
        assertThat(departament1).isEqualTo(departament2);
        departament2.setId(2L);
        assertThat(departament1).isNotEqualTo(departament2);
        departament1.setId(null);
        assertThat(departament1).isNotEqualTo(departament2);
    }
}
