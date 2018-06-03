package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.EmployeePossition;
import com.fmi.patokas.repository.EmployeePossitionRepository;
import com.fmi.patokas.service.EmployeePossitionService;
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
import java.util.List;

import static com.fmi.patokas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeePossitionResource REST controller.
 *
 * @see EmployeePossitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class EmployeePossitionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_NAME_NATIVE = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_NAME_NATIVE = "BBBBBBBBBB";

    @Autowired
    private EmployeePossitionRepository employeePossitionRepository;

    @Autowired
    private EmployeePossitionService employeePossitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeePossitionMockMvc;

    private EmployeePossition employeePossition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeePossitionResource employeePossitionResource = new EmployeePossitionResource(employeePossitionService);
        this.restEmployeePossitionMockMvc = MockMvcBuilders.standaloneSetup(employeePossitionResource)
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
    public static EmployeePossition createEntity(EntityManager em) {
        EmployeePossition employeePossition = new EmployeePossition()
            .code(DEFAULT_CODE)
            .positionName(DEFAULT_POSITION_NAME)
            .positionNameNative(DEFAULT_POSITION_NAME_NATIVE);
        return employeePossition;
    }

    @Before
    public void initTest() {
        employeePossition = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeePossition() throws Exception {
        int databaseSizeBeforeCreate = employeePossitionRepository.findAll().size();

        // Create the EmployeePossition
        restEmployeePossitionMockMvc.perform(post("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isCreated());

        // Validate the EmployeePossition in the database
        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePossition testEmployeePossition = employeePossitionList.get(employeePossitionList.size() - 1);
        assertThat(testEmployeePossition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmployeePossition.getPositionName()).isEqualTo(DEFAULT_POSITION_NAME);
        assertThat(testEmployeePossition.getPositionNameNative()).isEqualTo(DEFAULT_POSITION_NAME_NATIVE);
    }

    @Test
    @Transactional
    public void createEmployeePossitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeePossitionRepository.findAll().size();

        // Create the EmployeePossition with an existing ID
        employeePossition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeePossitionMockMvc.perform(post("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePossition in the database
        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeePossitionRepository.findAll().size();
        // set the field null
        employeePossition.setCode(null);

        // Create the EmployeePossition, which fails.

        restEmployeePossitionMockMvc.perform(post("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isBadRequest());

        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeePossitionRepository.findAll().size();
        // set the field null
        employeePossition.setPositionName(null);

        // Create the EmployeePossition, which fails.

        restEmployeePossitionMockMvc.perform(post("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isBadRequest());

        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionNameNativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeePossitionRepository.findAll().size();
        // set the field null
        employeePossition.setPositionNameNative(null);

        // Create the EmployeePossition, which fails.

        restEmployeePossitionMockMvc.perform(post("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isBadRequest());

        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeePossitions() throws Exception {
        // Initialize the database
        employeePossitionRepository.saveAndFlush(employeePossition);

        // Get all the employeePossitionList
        restEmployeePossitionMockMvc.perform(get("/api/employee-possitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeePossition.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].positionName").value(hasItem(DEFAULT_POSITION_NAME.toString())))
            .andExpect(jsonPath("$.[*].positionNameNative").value(hasItem(DEFAULT_POSITION_NAME_NATIVE.toString())));
    }

    @Test
    @Transactional
    public void getEmployeePossition() throws Exception {
        // Initialize the database
        employeePossitionRepository.saveAndFlush(employeePossition);

        // Get the employeePossition
        restEmployeePossitionMockMvc.perform(get("/api/employee-possitions/{id}", employeePossition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeePossition.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.positionName").value(DEFAULT_POSITION_NAME.toString()))
            .andExpect(jsonPath("$.positionNameNative").value(DEFAULT_POSITION_NAME_NATIVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeePossition() throws Exception {
        // Get the employeePossition
        restEmployeePossitionMockMvc.perform(get("/api/employee-possitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeePossition() throws Exception {
        // Initialize the database
        employeePossitionService.save(employeePossition);

        int databaseSizeBeforeUpdate = employeePossitionRepository.findAll().size();

        // Update the employeePossition
        EmployeePossition updatedEmployeePossition = employeePossitionRepository.findOne(employeePossition.getId());
        // Disconnect from session so that the updates on updatedEmployeePossition are not directly saved in db
        em.detach(updatedEmployeePossition);
        updatedEmployeePossition
            .code(UPDATED_CODE)
            .positionName(UPDATED_POSITION_NAME)
            .positionNameNative(UPDATED_POSITION_NAME_NATIVE);

        restEmployeePossitionMockMvc.perform(put("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeePossition)))
            .andExpect(status().isOk());

        // Validate the EmployeePossition in the database
        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeUpdate);
        EmployeePossition testEmployeePossition = employeePossitionList.get(employeePossitionList.size() - 1);
        assertThat(testEmployeePossition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmployeePossition.getPositionName()).isEqualTo(UPDATED_POSITION_NAME);
        assertThat(testEmployeePossition.getPositionNameNative()).isEqualTo(UPDATED_POSITION_NAME_NATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeePossition() throws Exception {
        int databaseSizeBeforeUpdate = employeePossitionRepository.findAll().size();

        // Create the EmployeePossition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeePossitionMockMvc.perform(put("/api/employee-possitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePossition)))
            .andExpect(status().isCreated());

        // Validate the EmployeePossition in the database
        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeePossition() throws Exception {
        // Initialize the database
        employeePossitionService.save(employeePossition);

        int databaseSizeBeforeDelete = employeePossitionRepository.findAll().size();

        // Get the employeePossition
        restEmployeePossitionMockMvc.perform(delete("/api/employee-possitions/{id}", employeePossition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeePossition> employeePossitionList = employeePossitionRepository.findAll();
        assertThat(employeePossitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePossition.class);
        EmployeePossition employeePossition1 = new EmployeePossition();
        employeePossition1.setId(1L);
        EmployeePossition employeePossition2 = new EmployeePossition();
        employeePossition2.setId(employeePossition1.getId());
        assertThat(employeePossition1).isEqualTo(employeePossition2);
        employeePossition2.setId(2L);
        assertThat(employeePossition1).isNotEqualTo(employeePossition2);
        employeePossition1.setId(null);
        assertThat(employeePossition1).isNotEqualTo(employeePossition2);
    }
}
