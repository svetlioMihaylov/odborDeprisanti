package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.FinancialDetails;
import com.fmi.patokas.repository.FinancialDetailsRepository;
import com.fmi.patokas.service.FinancialDetailsService;
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
 * Test class for the FinancialDetailsResource REST controller.
 *
 * @see FinancialDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class FinancialDetailsResourceIntTest {

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIC = "AAAAAAAAAA";
    private static final String UPDATED_BIC = "BBBBBBBBBB";

    private static final Double DEFAULT_BASE_SALARY = 1D;
    private static final Double UPDATED_BASE_SALARY = 2D;

    private static final Double DEFAULT_GROSS_SALARY = 1D;
    private static final Double UPDATED_GROSS_SALARY = 2D;

    private static final Double DEFAULT_SIGN_ON_BONUS = 1D;
    private static final Double UPDATED_SIGN_ON_BONUS = 2D;

    private static final Double DEFAULT_ANNUAL_BONUS = 1D;
    private static final Double UPDATED_ANNUAL_BONUS = 2D;

    @Autowired
    private FinancialDetailsRepository financialDetailsRepository;

    @Autowired
    private FinancialDetailsService financialDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinancialDetailsMockMvc;

    private FinancialDetails financialDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinancialDetailsResource financialDetailsResource = new FinancialDetailsResource(financialDetailsService);
        this.restFinancialDetailsMockMvc = MockMvcBuilders.standaloneSetup(financialDetailsResource)
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
    public static FinancialDetails createEntity(EntityManager em) {
        FinancialDetails financialDetails = new FinancialDetails()
            .iban(DEFAULT_IBAN)
            .bankName(DEFAULT_BANK_NAME)
            .bic(DEFAULT_BIC)
            .baseSalary(DEFAULT_BASE_SALARY)
            .grossSalary(DEFAULT_GROSS_SALARY)
            .signOnBonus(DEFAULT_SIGN_ON_BONUS)
            .annualBonus(DEFAULT_ANNUAL_BONUS);
        return financialDetails;
    }

    @Before
    public void initTest() {
        financialDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinancialDetails() throws Exception {
        int databaseSizeBeforeCreate = financialDetailsRepository.findAll().size();

        // Create the FinancialDetails
        restFinancialDetailsMockMvc.perform(post("/api/financial-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialDetails)))
            .andExpect(status().isCreated());

        // Validate the FinancialDetails in the database
        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialDetails testFinancialDetails = financialDetailsList.get(financialDetailsList.size() - 1);
        assertThat(testFinancialDetails.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testFinancialDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testFinancialDetails.getBic()).isEqualTo(DEFAULT_BIC);
        assertThat(testFinancialDetails.getBaseSalary()).isEqualTo(DEFAULT_BASE_SALARY);
        assertThat(testFinancialDetails.getGrossSalary()).isEqualTo(DEFAULT_GROSS_SALARY);
        assertThat(testFinancialDetails.getSignOnBonus()).isEqualTo(DEFAULT_SIGN_ON_BONUS);
        assertThat(testFinancialDetails.getAnnualBonus()).isEqualTo(DEFAULT_ANNUAL_BONUS);
    }

    @Test
    @Transactional
    public void createFinancialDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financialDetailsRepository.findAll().size();

        // Create the FinancialDetails with an existing ID
        financialDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialDetailsMockMvc.perform(post("/api/financial-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialDetails)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialDetails in the database
        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIbanIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialDetailsRepository.findAll().size();
        // set the field null
        financialDetails.setIban(null);

        // Create the FinancialDetails, which fails.

        restFinancialDetailsMockMvc.perform(post("/api/financial-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialDetails)))
            .andExpect(status().isBadRequest());

        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFinancialDetails() throws Exception {
        // Initialize the database
        financialDetailsRepository.saveAndFlush(financialDetails);

        // Get all the financialDetailsList
        restFinancialDetailsMockMvc.perform(get("/api/financial-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bic").value(hasItem(DEFAULT_BIC.toString())))
            .andExpect(jsonPath("$.[*].baseSalary").value(hasItem(DEFAULT_BASE_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].grossSalary").value(hasItem(DEFAULT_GROSS_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].signOnBonus").value(hasItem(DEFAULT_SIGN_ON_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].annualBonus").value(hasItem(DEFAULT_ANNUAL_BONUS.doubleValue())));
    }

    @Test
    @Transactional
    public void getFinancialDetails() throws Exception {
        // Initialize the database
        financialDetailsRepository.saveAndFlush(financialDetails);

        // Get the financialDetails
        restFinancialDetailsMockMvc.perform(get("/api/financial-details/{id}", financialDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financialDetails.getId().intValue()))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bic").value(DEFAULT_BIC.toString()))
            .andExpect(jsonPath("$.baseSalary").value(DEFAULT_BASE_SALARY.doubleValue()))
            .andExpect(jsonPath("$.grossSalary").value(DEFAULT_GROSS_SALARY.doubleValue()))
            .andExpect(jsonPath("$.signOnBonus").value(DEFAULT_SIGN_ON_BONUS.doubleValue()))
            .andExpect(jsonPath("$.annualBonus").value(DEFAULT_ANNUAL_BONUS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFinancialDetails() throws Exception {
        // Get the financialDetails
        restFinancialDetailsMockMvc.perform(get("/api/financial-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinancialDetails() throws Exception {
        // Initialize the database
        financialDetailsService.save(financialDetails);

        int databaseSizeBeforeUpdate = financialDetailsRepository.findAll().size();

        // Update the financialDetails
        FinancialDetails updatedFinancialDetails = financialDetailsRepository.findOne(financialDetails.getId());
        // Disconnect from session so that the updates on updatedFinancialDetails are not directly saved in db
        em.detach(updatedFinancialDetails);
        updatedFinancialDetails
            .iban(UPDATED_IBAN)
            .bankName(UPDATED_BANK_NAME)
            .bic(UPDATED_BIC)
            .baseSalary(UPDATED_BASE_SALARY)
            .grossSalary(UPDATED_GROSS_SALARY)
            .signOnBonus(UPDATED_SIGN_ON_BONUS)
            .annualBonus(UPDATED_ANNUAL_BONUS);

        restFinancialDetailsMockMvc.perform(put("/api/financial-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinancialDetails)))
            .andExpect(status().isOk());

        // Validate the FinancialDetails in the database
        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeUpdate);
        FinancialDetails testFinancialDetails = financialDetailsList.get(financialDetailsList.size() - 1);
        assertThat(testFinancialDetails.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testFinancialDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testFinancialDetails.getBic()).isEqualTo(UPDATED_BIC);
        assertThat(testFinancialDetails.getBaseSalary()).isEqualTo(UPDATED_BASE_SALARY);
        assertThat(testFinancialDetails.getGrossSalary()).isEqualTo(UPDATED_GROSS_SALARY);
        assertThat(testFinancialDetails.getSignOnBonus()).isEqualTo(UPDATED_SIGN_ON_BONUS);
        assertThat(testFinancialDetails.getAnnualBonus()).isEqualTo(UPDATED_ANNUAL_BONUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFinancialDetails() throws Exception {
        int databaseSizeBeforeUpdate = financialDetailsRepository.findAll().size();

        // Create the FinancialDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinancialDetailsMockMvc.perform(put("/api/financial-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialDetails)))
            .andExpect(status().isCreated());

        // Validate the FinancialDetails in the database
        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinancialDetails() throws Exception {
        // Initialize the database
        financialDetailsService.save(financialDetails);

        int databaseSizeBeforeDelete = financialDetailsRepository.findAll().size();

        // Get the financialDetails
        restFinancialDetailsMockMvc.perform(delete("/api/financial-details/{id}", financialDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinancialDetails> financialDetailsList = financialDetailsRepository.findAll();
        assertThat(financialDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialDetails.class);
        FinancialDetails financialDetails1 = new FinancialDetails();
        financialDetails1.setId(1L);
        FinancialDetails financialDetails2 = new FinancialDetails();
        financialDetails2.setId(financialDetails1.getId());
        assertThat(financialDetails1).isEqualTo(financialDetails2);
        financialDetails2.setId(2L);
        assertThat(financialDetails1).isNotEqualTo(financialDetails2);
        financialDetails1.setId(null);
        assertThat(financialDetails1).isNotEqualTo(financialDetails2);
    }
}
