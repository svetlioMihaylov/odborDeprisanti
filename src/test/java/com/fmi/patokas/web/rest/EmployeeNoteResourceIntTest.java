package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.EmployeeNote;
import com.fmi.patokas.repository.EmployeeNoteRepository;
import com.fmi.patokas.service.EmployeeNoteService;
import com.fmi.patokas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.fmi.patokas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fmi.patokas.domain.enumeration.EmployeeNoteType;
/**
 * Test class for the EmployeeNoteResource REST controller.
 *
 * @see EmployeeNoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class EmployeeNoteResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final EmployeeNoteType DEFAULT_EMPLOYEE_NOTE_TYPE = EmployeeNoteType.START_WORK;
    private static final EmployeeNoteType UPDATED_EMPLOYEE_NOTE_TYPE = EmployeeNoteType.END_WORK;

    @Autowired
    private EmployeeNoteRepository employeeNoteRepository;

    @Autowired
    private EmployeeNoteService employeeNoteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeNoteMockMvc;

    private EmployeeNote employeeNote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeNoteResource employeeNoteResource = new EmployeeNoteResource(employeeNoteService);
        this.restEmployeeNoteMockMvc = MockMvcBuilders.standaloneSetup(employeeNoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeNote createEntity(EntityManager em) {
        EmployeeNote employeeNote = new EmployeeNote()
            .title(DEFAULT_TITLE)
            .text(DEFAULT_TEXT)
            .date(DEFAULT_DATE)
            .employeeNoteType(DEFAULT_EMPLOYEE_NOTE_TYPE);
        return employeeNote;
    }

    @Before
    public void initTest() {
        employeeNote = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeNote() throws Exception {
        int databaseSizeBeforeCreate = employeeNoteRepository.findAll().size();

        // Create the EmployeeNote
        restEmployeeNoteMockMvc.perform(post("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeNote)))
            .andExpect(status().isCreated());

        // Validate the EmployeeNote in the database
        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeNote testEmployeeNote = employeeNoteList.get(employeeNoteList.size() - 1);
        assertThat(testEmployeeNote.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployeeNote.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testEmployeeNote.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmployeeNote.getEmployeeNoteType()).isEqualTo(DEFAULT_EMPLOYEE_NOTE_TYPE);
    }

    @Test
    @Transactional
    public void createEmployeeNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeNoteRepository.findAll().size();

        // Create the EmployeeNote with an existing ID
        employeeNote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeNoteMockMvc.perform(post("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeNote)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeNote in the database
        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeNoteRepository.findAll().size();
        // set the field null
        employeeNote.setTitle(null);

        // Create the EmployeeNote, which fails.

        restEmployeeNoteMockMvc.perform(post("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeNote)))
            .andExpect(status().isBadRequest());

        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeNoteRepository.findAll().size();
        // set the field null
        employeeNote.setText(null);

        // Create the EmployeeNote, which fails.

        restEmployeeNoteMockMvc.perform(post("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeNote)))
            .andExpect(status().isBadRequest());

        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeNotes() throws Exception {
        // Initialize the database
        employeeNoteRepository.saveAndFlush(employeeNote);

        // Get all the employeeNoteList
        restEmployeeNoteMockMvc.perform(get("/api/employee-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].employeeNoteType").value(hasItem(DEFAULT_EMPLOYEE_NOTE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeNote() throws Exception {
        // Initialize the database
        employeeNoteRepository.saveAndFlush(employeeNote);

        // Get the employeeNote
        restEmployeeNoteMockMvc.perform(get("/api/employee-notes/{id}", employeeNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeNote.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.employeeNoteType").value(DEFAULT_EMPLOYEE_NOTE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeNote() throws Exception {
        // Get the employeeNote
        restEmployeeNoteMockMvc.perform(get("/api/employee-notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeNote() throws Exception {
        // Initialize the database
        employeeNoteService.save(employeeNote);

        int databaseSizeBeforeUpdate = employeeNoteRepository.findAll().size();

        // Update the employeeNote
        EmployeeNote updatedEmployeeNote = employeeNoteRepository.findOne(employeeNote.getId());
        // Disconnect from session so that the updates on updatedEmployeeNote are not directly saved in db
        em.detach(updatedEmployeeNote);
        updatedEmployeeNote
            .title(UPDATED_TITLE)
            .text(UPDATED_TEXT)
            .date(UPDATED_DATE)
            .employeeNoteType(UPDATED_EMPLOYEE_NOTE_TYPE);

        restEmployeeNoteMockMvc.perform(put("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeNote)))
            .andExpect(status().isOk());

        // Validate the EmployeeNote in the database
        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeUpdate);
        EmployeeNote testEmployeeNote = employeeNoteList.get(employeeNoteList.size() - 1);
        assertThat(testEmployeeNote.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeNote.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmployeeNote.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmployeeNote.getEmployeeNoteType()).isEqualTo(UPDATED_EMPLOYEE_NOTE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeNote() throws Exception {
        int databaseSizeBeforeUpdate = employeeNoteRepository.findAll().size();

        // Create the EmployeeNote

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeNoteMockMvc.perform(put("/api/employee-notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeNote)))
            .andExpect(status().isCreated());

        // Validate the EmployeeNote in the database
        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeNote() throws Exception {
        // Initialize the database
        employeeNoteService.save(employeeNote);

        int databaseSizeBeforeDelete = employeeNoteRepository.findAll().size();

        // Get the employeeNote
        restEmployeeNoteMockMvc.perform(delete("/api/employee-notes/{id}", employeeNote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeNote> employeeNoteList = employeeNoteRepository.findAll();
        assertThat(employeeNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeNote.class);
        EmployeeNote employeeNote1 = new EmployeeNote();
        employeeNote1.setId(1L);
        EmployeeNote employeeNote2 = new EmployeeNote();
        employeeNote2.setId(employeeNote1.getId());
        assertThat(employeeNote1).isEqualTo(employeeNote2);
        employeeNote2.setId(2L);
        assertThat(employeeNote1).isNotEqualTo(employeeNote2);
        employeeNote1.setId(null);
        assertThat(employeeNote1).isNotEqualTo(employeeNote2);
    }
}
