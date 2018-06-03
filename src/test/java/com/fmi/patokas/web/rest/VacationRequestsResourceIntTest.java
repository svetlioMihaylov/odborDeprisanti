package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.VacationRequests;
import com.fmi.patokas.repository.VacationRequestsRepository;
import com.fmi.patokas.service.VacationRequestsService;
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
 * Test class for the VacationRequestsResource REST controller.
 *
 * @see VacationRequestsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class VacationRequestsResourceIntTest {

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_COMPLEATED = false;
    private static final Boolean UPDATED_IS_COMPLEATED = true;

    @Autowired
    private VacationRequestsRepository vacationRequestsRepository;

    @Autowired
    private VacationRequestsService vacationRequestsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVacationRequestsMockMvc;

    private VacationRequests vacationRequests;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VacationRequestsResource vacationRequestsResource = new VacationRequestsResource(vacationRequestsService);
        this.restVacationRequestsMockMvc = MockMvcBuilders.standaloneSetup(vacationRequestsResource)
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
    public static VacationRequests createEntity(EntityManager em) {
        VacationRequests vacationRequests = new VacationRequests()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .duration(DEFAULT_DURATION)
            .isApproved(DEFAULT_IS_APPROVED)
            .isCompleated(DEFAULT_IS_COMPLEATED);
        return vacationRequests;
    }

    @Before
    public void initTest() {
        vacationRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createVacationRequests() throws Exception {
        int databaseSizeBeforeCreate = vacationRequestsRepository.findAll().size();

        // Create the VacationRequests
        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isCreated());

        // Validate the VacationRequests in the database
        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        VacationRequests testVacationRequests = vacationRequestsList.get(vacationRequestsList.size() - 1);
        assertThat(testVacationRequests.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVacationRequests.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testVacationRequests.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testVacationRequests.isIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testVacationRequests.isIsCompleated()).isEqualTo(DEFAULT_IS_COMPLEATED);
    }

    @Test
    @Transactional
    public void createVacationRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vacationRequestsRepository.findAll().size();

        // Create the VacationRequests with an existing ID
        vacationRequests.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        // Validate the VacationRequests in the database
        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacationRequestsRepository.findAll().size();
        // set the field null
        vacationRequests.setStartDate(null);

        // Create the VacationRequests, which fails.

        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacationRequestsRepository.findAll().size();
        // set the field null
        vacationRequests.setEndDate(null);

        // Create the VacationRequests, which fails.

        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacationRequestsRepository.findAll().size();
        // set the field null
        vacationRequests.setDuration(null);

        // Create the VacationRequests, which fails.

        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacationRequestsRepository.findAll().size();
        // set the field null
        vacationRequests.setIsApproved(null);

        // Create the VacationRequests, which fails.

        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCompleatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacationRequestsRepository.findAll().size();
        // set the field null
        vacationRequests.setIsCompleated(null);

        // Create the VacationRequests, which fails.

        restVacationRequestsMockMvc.perform(post("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isBadRequest());

        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVacationRequests() throws Exception {
        // Initialize the database
        vacationRequestsRepository.saveAndFlush(vacationRequests);

        // Get all the vacationRequestsList
        restVacationRequestsMockMvc.perform(get("/api/vacation-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vacationRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompleated").value(hasItem(DEFAULT_IS_COMPLEATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getVacationRequests() throws Exception {
        // Initialize the database
        vacationRequestsRepository.saveAndFlush(vacationRequests);

        // Get the vacationRequests
        restVacationRequestsMockMvc.perform(get("/api/vacation-requests/{id}", vacationRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vacationRequests.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isCompleated").value(DEFAULT_IS_COMPLEATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVacationRequests() throws Exception {
        // Get the vacationRequests
        restVacationRequestsMockMvc.perform(get("/api/vacation-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVacationRequests() throws Exception {
        // Initialize the database
        vacationRequestsService.save(vacationRequests);

        int databaseSizeBeforeUpdate = vacationRequestsRepository.findAll().size();

        // Update the vacationRequests
        VacationRequests updatedVacationRequests = vacationRequestsRepository.findOne(vacationRequests.getId());
        // Disconnect from session so that the updates on updatedVacationRequests are not directly saved in db
        em.detach(updatedVacationRequests);
        updatedVacationRequests
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .isApproved(UPDATED_IS_APPROVED)
            .isCompleated(UPDATED_IS_COMPLEATED);

        restVacationRequestsMockMvc.perform(put("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVacationRequests)))
            .andExpect(status().isOk());

        // Validate the VacationRequests in the database
        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeUpdate);
        VacationRequests testVacationRequests = vacationRequestsList.get(vacationRequestsList.size() - 1);
        assertThat(testVacationRequests.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVacationRequests.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testVacationRequests.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testVacationRequests.isIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testVacationRequests.isIsCompleated()).isEqualTo(UPDATED_IS_COMPLEATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVacationRequests() throws Exception {
        int databaseSizeBeforeUpdate = vacationRequestsRepository.findAll().size();

        // Create the VacationRequests

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVacationRequestsMockMvc.perform(put("/api/vacation-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacationRequests)))
            .andExpect(status().isCreated());

        // Validate the VacationRequests in the database
        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVacationRequests() throws Exception {
        // Initialize the database
        vacationRequestsService.save(vacationRequests);

        int databaseSizeBeforeDelete = vacationRequestsRepository.findAll().size();

        // Get the vacationRequests
        restVacationRequestsMockMvc.perform(delete("/api/vacation-requests/{id}", vacationRequests.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VacationRequests> vacationRequestsList = vacationRequestsRepository.findAll();
        assertThat(vacationRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VacationRequests.class);
        VacationRequests vacationRequests1 = new VacationRequests();
        vacationRequests1.setId(1L);
        VacationRequests vacationRequests2 = new VacationRequests();
        vacationRequests2.setId(vacationRequests1.getId());
        assertThat(vacationRequests1).isEqualTo(vacationRequests2);
        vacationRequests2.setId(2L);
        assertThat(vacationRequests1).isNotEqualTo(vacationRequests2);
        vacationRequests1.setId(null);
        assertThat(vacationRequests1).isNotEqualTo(vacationRequests2);
    }
}
