package nohaidragos.web.rest;

import nohaidragos.NohaidragosApp;
import nohaidragos.domain.ProgramStudiu;
import nohaidragos.repository.ProgramStudiuRepository;
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
 * Integration tests for the {@Link ProgramStudiuResource} REST controller.
 */
@SpringBootTest(classes = NohaidragosApp.class)
public class ProgramStudiuResourceIT {

    private static final String DEFAULT_PROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM = "BBBBBBBBBB";

    @Autowired
    private ProgramStudiuRepository programStudiuRepository;

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

    private MockMvc restProgramStudiuMockMvc;

    private ProgramStudiu programStudiu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgramStudiuResource programStudiuResource = new ProgramStudiuResource(programStudiuRepository);
        this.restProgramStudiuMockMvc = MockMvcBuilders.standaloneSetup(programStudiuResource)
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
    public static ProgramStudiu createEntity(EntityManager em) {
        ProgramStudiu programStudiu = new ProgramStudiu()
            .program(DEFAULT_PROGRAM);
        return programStudiu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramStudiu createUpdatedEntity(EntityManager em) {
        ProgramStudiu programStudiu = new ProgramStudiu()
            .program(UPDATED_PROGRAM);
        return programStudiu;
    }

    @BeforeEach
    public void initTest() {
        programStudiu = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgramStudiu() throws Exception {
        int databaseSizeBeforeCreate = programStudiuRepository.findAll().size();

        // Create the ProgramStudiu
        restProgramStudiuMockMvc.perform(post("/api/program-studius")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiu)))
            .andExpect(status().isCreated());

        // Validate the ProgramStudiu in the database
        List<ProgramStudiu> programStudiuList = programStudiuRepository.findAll();
        assertThat(programStudiuList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramStudiu testProgramStudiu = programStudiuList.get(programStudiuList.size() - 1);
        assertThat(testProgramStudiu.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createProgramStudiuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programStudiuRepository.findAll().size();

        // Create the ProgramStudiu with an existing ID
        programStudiu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramStudiuMockMvc.perform(post("/api/program-studius")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiu)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramStudiu in the database
        List<ProgramStudiu> programStudiuList = programStudiuRepository.findAll();
        assertThat(programStudiuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProgramStudius() throws Exception {
        // Initialize the database
        programStudiuRepository.saveAndFlush(programStudiu);

        // Get all the programStudiuList
        restProgramStudiuMockMvc.perform(get("/api/program-studius?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programStudiu.getId().intValue())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getProgramStudiu() throws Exception {
        // Initialize the database
        programStudiuRepository.saveAndFlush(programStudiu);

        // Get the programStudiu
        restProgramStudiuMockMvc.perform(get("/api/program-studius/{id}", programStudiu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programStudiu.getId().intValue()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgramStudiu() throws Exception {
        // Get the programStudiu
        restProgramStudiuMockMvc.perform(get("/api/program-studius/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramStudiu() throws Exception {
        // Initialize the database
        programStudiuRepository.saveAndFlush(programStudiu);

        int databaseSizeBeforeUpdate = programStudiuRepository.findAll().size();

        // Update the programStudiu
        ProgramStudiu updatedProgramStudiu = programStudiuRepository.findById(programStudiu.getId()).get();
        // Disconnect from session so that the updates on updatedProgramStudiu are not directly saved in db
        em.detach(updatedProgramStudiu);
        updatedProgramStudiu
            .program(UPDATED_PROGRAM);

        restProgramStudiuMockMvc.perform(put("/api/program-studius")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProgramStudiu)))
            .andExpect(status().isOk());

        // Validate the ProgramStudiu in the database
        List<ProgramStudiu> programStudiuList = programStudiuRepository.findAll();
        assertThat(programStudiuList).hasSize(databaseSizeBeforeUpdate);
        ProgramStudiu testProgramStudiu = programStudiuList.get(programStudiuList.size() - 1);
        assertThat(testProgramStudiu.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingProgramStudiu() throws Exception {
        int databaseSizeBeforeUpdate = programStudiuRepository.findAll().size();

        // Create the ProgramStudiu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramStudiuMockMvc.perform(put("/api/program-studius")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiu)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramStudiu in the database
        List<ProgramStudiu> programStudiuList = programStudiuRepository.findAll();
        assertThat(programStudiuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgramStudiu() throws Exception {
        // Initialize the database
        programStudiuRepository.saveAndFlush(programStudiu);

        int databaseSizeBeforeDelete = programStudiuRepository.findAll().size();

        // Delete the programStudiu
        restProgramStudiuMockMvc.perform(delete("/api/program-studius/{id}", programStudiu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramStudiu> programStudiuList = programStudiuRepository.findAll();
        assertThat(programStudiuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramStudiu.class);
        ProgramStudiu programStudiu1 = new ProgramStudiu();
        programStudiu1.setId(1L);
        ProgramStudiu programStudiu2 = new ProgramStudiu();
        programStudiu2.setId(programStudiu1.getId());
        assertThat(programStudiu1).isEqualTo(programStudiu2);
        programStudiu2.setId(2L);
        assertThat(programStudiu1).isNotEqualTo(programStudiu2);
        programStudiu1.setId(null);
        assertThat(programStudiu1).isNotEqualTo(programStudiu2);
    }
}
