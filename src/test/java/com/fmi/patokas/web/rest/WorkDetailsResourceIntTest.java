package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.WorkDetails;
import com.fmi.patokas.repository.WorkDetailsRepository;
import com.fmi.patokas.service.WorkDetailsService;
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

/**
 * Test class for the WorkDetailsResource REST controller.
 *
 * @see WorkDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class WorkDetailsResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_OF_PROBATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_OF_PROBATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_SALARY_REEVALUATION_DATE = 1D;
    private static final Double UPDATED_SALARY_REEVALUATION_DATE = 2D;

    private static final Double DEFAULT_CONTRACT_NUM = 1D;
    private static final Double UPDATED_CONTRACT_NUM = 2D;

    private static final Double DEFAULT_RESIGNATION_REQUEST_REF_NUM = 1D;
    private static final Double UPDATED_RESIGNATION_REQUEST_REF_NUM = 2D;

    private static final Double DEFAULT_RESIGNATION_ORDER_REF_NUM = 1D;
    private static final Double UPDATED_RESIGNATION_ORDER_REF_NUM = 2D;

    private static final Integer DEFAULT_YEAR_VACATION = 1;
    private static final Integer UPDATED_YEAR_VACATION = 2;

    @Autowired
    private WorkDetailsRepository workDetailsRepository;

    @Autowired
    private WorkDetailsService workDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkDetailsMockMvc;

    private WorkDetails workDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkDetailsResource workDetailsResource = new WorkDetailsResource(workDetailsService);
        this.restWorkDetailsMockMvc = MockMvcBuilders.standaloneSetup(workDetailsResource)
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
    public static WorkDetails createEntity(EntityManager em) {
        WorkDetails workDetails = new WorkDetails()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .endOfProbationDate(DEFAULT_END_OF_PROBATION_DATE)
            .salaryReevaluationDate(DEFAULT_SALARY_REEVALUATION_DATE)
            .contractNum(DEFAULT_CONTRACT_NUM)
            .resignationRequestRefNum(DEFAULT_RESIGNATION_REQUEST_REF_NUM)
            .resignationOrderRefNum(DEFAULT_RESIGNATION_ORDER_REF_NUM)
            .yearVacation(DEFAULT_YEAR_VACATION);
        return workDetails;
    }

    @Before
    public void initTest() {
        workDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkDetails() throws Exception {
        int databaseSizeBeforeCreate = workDetailsRepository.findAll().size();

        // Create the WorkDetails
        restWorkDetailsMockMvc.perform(post("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDetails)))
            .andExpect(status().isCreated());

        // Validate the WorkDetails in the database
        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        WorkDetails testWorkDetails = workDetailsList.get(workDetailsList.size() - 1);
        assertThat(testWorkDetails.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkDetails.getEndOfProbationDate()).isEqualTo(DEFAULT_END_OF_PROBATION_DATE);
        assertThat(testWorkDetails.getSalaryReevaluationDate()).isEqualTo(DEFAULT_SALARY_REEVALUATION_DATE);
        assertThat(testWorkDetails.getContractNum()).isEqualTo(DEFAULT_CONTRACT_NUM);
        assertThat(testWorkDetails.getResignationRequestRefNum()).isEqualTo(DEFAULT_RESIGNATION_REQUEST_REF_NUM);
        assertThat(testWorkDetails.getResignationOrderRefNum()).isEqualTo(DEFAULT_RESIGNATION_ORDER_REF_NUM);
        assertThat(testWorkDetails.getYearVacation()).isEqualTo(DEFAULT_YEAR_VACATION);
    }

    @Test
    @Transactional
    public void createWorkDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workDetailsRepository.findAll().size();

        // Create the WorkDetails with an existing ID
        workDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkDetailsMockMvc.perform(post("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDetails)))
            .andExpect(status().isBadRequest());

        // Validate the WorkDetails in the database
        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workDetailsRepository.findAll().size();
        // set the field null
        workDetails.setStartDate(null);

        // Create the WorkDetails, which fails.

        restWorkDetailsMockMvc.perform(post("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDetails)))
            .andExpect(status().isBadRequest());

        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndOfProbationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workDetailsRepository.findAll().size();
        // set the field null
        workDetails.setEndOfProbationDate(null);

        // Create the WorkDetails, which fails.

        restWorkDetailsMockMvc.perform(post("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDetails)))
            .andExpect(status().isBadRequest());

        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkDetails() throws Exception {
        // Initialize the database
        workDetailsRepository.saveAndFlush(workDetails);

        // Get all the workDetailsList
        restWorkDetailsMockMvc.perform(get("/api/work-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].endOfProbationDate").value(hasItem(DEFAULT_END_OF_PROBATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].salaryReevaluationDate").value(hasItem(DEFAULT_SALARY_REEVALUATION_DATE.doubleValue())))
            .andExpect(jsonPath("$.[*].contractNum").value(hasItem(DEFAULT_CONTRACT_NUM.doubleValue())))
            .andExpect(jsonPath("$.[*].resignationRequestRefNum").value(hasItem(DEFAULT_RESIGNATION_REQUEST_REF_NUM.doubleValue())))
            .andExpect(jsonPath("$.[*].resignationOrderRefNum").value(hasItem(DEFAULT_RESIGNATION_ORDER_REF_NUM.doubleValue())))
            .andExpect(jsonPath("$.[*].yearVacation").value(hasItem(DEFAULT_YEAR_VACATION)));
    }

    @Test
    @Transactional
    public void getWorkDetails() throws Exception {
        // Initialize the database
        workDetailsRepository.saveAndFlush(workDetails);

        // Get the workDetails
        restWorkDetailsMockMvc.perform(get("/api/work-details/{id}", workDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workDetails.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.endOfProbationDate").value(DEFAULT_END_OF_PROBATION_DATE.toString()))
            .andExpect(jsonPath("$.salaryReevaluationDate").value(DEFAULT_SALARY_REEVALUATION_DATE.doubleValue()))
            .andExpect(jsonPath("$.contractNum").value(DEFAULT_CONTRACT_NUM.doubleValue()))
            .andExpect(jsonPath("$.resignationRequestRefNum").value(DEFAULT_RESIGNATION_REQUEST_REF_NUM.doubleValue()))
            .andExpect(jsonPath("$.resignationOrderRefNum").value(DEFAULT_RESIGNATION_ORDER_REF_NUM.doubleValue()))
            .andExpect(jsonPath("$.yearVacation").value(DEFAULT_YEAR_VACATION));
    }

    @Test
    @Transactional
    public void getNonExistingWorkDetails() throws Exception {
        // Get the workDetails
        restWorkDetailsMockMvc.perform(get("/api/work-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkDetails() throws Exception {
        // Initialize the database
        workDetailsService.save(workDetails);

        int databaseSizeBeforeUpdate = workDetailsRepository.findAll().size();

        // Update the workDetails
        WorkDetails updatedWorkDetails = workDetailsRepository.findOne(workDetails.getId());
        // Disconnect from session so that the updates on updatedWorkDetails are not directly saved in db
        em.detach(updatedWorkDetails);
        updatedWorkDetails
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .endOfProbationDate(UPDATED_END_OF_PROBATION_DATE)
            .salaryReevaluationDate(UPDATED_SALARY_REEVALUATION_DATE)
            .contractNum(UPDATED_CONTRACT_NUM)
            .resignationRequestRefNum(UPDATED_RESIGNATION_REQUEST_REF_NUM)
            .resignationOrderRefNum(UPDATED_RESIGNATION_ORDER_REF_NUM)
            .yearVacation(UPDATED_YEAR_VACATION);

        restWorkDetailsMockMvc.perform(put("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkDetails)))
            .andExpect(status().isOk());

        // Validate the WorkDetails in the database
        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeUpdate);
        WorkDetails testWorkDetails = workDetailsList.get(workDetailsList.size() - 1);
        assertThat(testWorkDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkDetails.getEndOfProbationDate()).isEqualTo(UPDATED_END_OF_PROBATION_DATE);
        assertThat(testWorkDetails.getSalaryReevaluationDate()).isEqualTo(UPDATED_SALARY_REEVALUATION_DATE);
        assertThat(testWorkDetails.getContractNum()).isEqualTo(UPDATED_CONTRACT_NUM);
        assertThat(testWorkDetails.getResignationRequestRefNum()).isEqualTo(UPDATED_RESIGNATION_REQUEST_REF_NUM);
        assertThat(testWorkDetails.getResignationOrderRefNum()).isEqualTo(UPDATED_RESIGNATION_ORDER_REF_NUM);
        assertThat(testWorkDetails.getYearVacation()).isEqualTo(UPDATED_YEAR_VACATION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkDetails() throws Exception {
        int databaseSizeBeforeUpdate = workDetailsRepository.findAll().size();

        // Create the WorkDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkDetailsMockMvc.perform(put("/api/work-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDetails)))
            .andExpect(status().isCreated());

        // Validate the WorkDetails in the database
        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkDetails() throws Exception {
        // Initialize the database
        workDetailsService.save(workDetails);

        int databaseSizeBeforeDelete = workDetailsRepository.findAll().size();

        // Get the workDetails
        restWorkDetailsMockMvc.perform(delete("/api/work-details/{id}", workDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkDetails> workDetailsList = workDetailsRepository.findAll();
        assertThat(workDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDetails.class);
        WorkDetails workDetails1 = new WorkDetails();
        workDetails1.setId(1L);
        WorkDetails workDetails2 = new WorkDetails();
        workDetails2.setId(workDetails1.getId());
        assertThat(workDetails1).isEqualTo(workDetails2);
        workDetails2.setId(2L);
        assertThat(workDetails1).isNotEqualTo(workDetails2);
        workDetails1.setId(null);
        assertThat(workDetails1).isNotEqualTo(workDetails2);
    }
}
