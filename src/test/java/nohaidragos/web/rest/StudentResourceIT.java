package nohaidragos.web.rest;

import nohaidragos.NohaidragosApp;
import nohaidragos.domain.Student;
import nohaidragos.repository.StudentRepository;
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

import nohaidragos.domain.enumeration.LiniaDeStudiu;
import nohaidragos.domain.enumeration.FormaInvatamant;
/**
 * Integration tests for the {@Link StudentResource} REST controller.
 */
@SpringBootTest(classes = NohaidragosApp.class)
public class StudentResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String DEFAULT_PRENUME = "AAAAAAAAAA";
    private static final String UPDATED_PRENUME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMAR_MATRICOL = 1;
    private static final Integer UPDATED_NUMAR_MATRICOL = 2;

    private static final String DEFAULT_LUCRARE_LICENTA = "AAAAAAAAAA";
    private static final String UPDATED_LUCRARE_LICENTA = "BBBBBBBBBB";

    private static final LiniaDeStudiu DEFAULT_LINIA = LiniaDeStudiu.ROMANA;
    private static final LiniaDeStudiu UPDATED_LINIA = LiniaDeStudiu.ENGLEZA;

    private static final FormaInvatamant DEFAULT_FORMA = FormaInvatamant.ID;
    private static final FormaInvatamant UPDATED_FORMA = FormaInvatamant.IF;

    @Autowired
    private StudentRepository studentRepository;

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

    private MockMvc restStudentMockMvc;

    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentRepository);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .nume(DEFAULT_NUME)
            .prenume(DEFAULT_PRENUME)
            .numarMatricol(DEFAULT_NUMAR_MATRICOL)
            .lucrareLicenta(DEFAULT_LUCRARE_LICENTA)
            .linia(DEFAULT_LINIA)
            .forma(DEFAULT_FORMA);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .nume(UPDATED_NUME)
            .prenume(UPDATED_PRENUME)
            .numarMatricol(UPDATED_NUMAR_MATRICOL)
            .lucrareLicenta(UPDATED_LUCRARE_LICENTA)
            .linia(UPDATED_LINIA)
            .forma(UPDATED_FORMA);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testStudent.getPrenume()).isEqualTo(DEFAULT_PRENUME);
        assertThat(testStudent.getNumarMatricol()).isEqualTo(DEFAULT_NUMAR_MATRICOL);
        assertThat(testStudent.getLucrareLicenta()).isEqualTo(DEFAULT_LUCRARE_LICENTA);
        assertThat(testStudent.getLinia()).isEqualTo(DEFAULT_LINIA);
        assertThat(testStudent.getForma()).isEqualTo(DEFAULT_FORMA);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setNume(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setPrenume(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME.toString())))
            .andExpect(jsonPath("$.[*].prenume").value(hasItem(DEFAULT_PRENUME.toString())))
            .andExpect(jsonPath("$.[*].numarMatricol").value(hasItem(DEFAULT_NUMAR_MATRICOL)))
            .andExpect(jsonPath("$.[*].lucrareLicenta").value(hasItem(DEFAULT_LUCRARE_LICENTA.toString())))
            .andExpect(jsonPath("$.[*].linia").value(hasItem(DEFAULT_LINIA.toString())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA.toString())));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()))
            .andExpect(jsonPath("$.prenume").value(DEFAULT_PRENUME.toString()))
            .andExpect(jsonPath("$.numarMatricol").value(DEFAULT_NUMAR_MATRICOL))
            .andExpect(jsonPath("$.lucrareLicenta").value(DEFAULT_LUCRARE_LICENTA.toString()))
            .andExpect(jsonPath("$.linia").value(DEFAULT_LINIA.toString()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .nume(UPDATED_NUME)
            .prenume(UPDATED_PRENUME)
            .numarMatricol(UPDATED_NUMAR_MATRICOL)
            .lucrareLicenta(UPDATED_LUCRARE_LICENTA)
            .linia(UPDATED_LINIA)
            .forma(UPDATED_FORMA);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testStudent.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testStudent.getNumarMatricol()).isEqualTo(UPDATED_NUMAR_MATRICOL);
        assertThat(testStudent.getLucrareLicenta()).isEqualTo(UPDATED_LUCRARE_LICENTA);
        assertThat(testStudent.getLinia()).isEqualTo(UPDATED_LINIA);
        assertThat(testStudent.getForma()).isEqualTo(UPDATED_FORMA);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}
