package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.Benefit;
import com.fmi.patokas.repository.BenefitRepository;
import com.fmi.patokas.service.BenefitService;
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

import com.fmi.patokas.domain.enumeration.BenefitType;
/**
 * Test class for the BenefitResource REST controller.
 *
 * @see BenefitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class BenefitResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BenefitType DEFAULT_BENEFIT_TYPE = BenefitType.PARKING;
    private static final BenefitType UPDATED_BENEFIT_TYPE = BenefitType.PUBLIC_TRNASPORT;

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private BenefitService benefitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBenefitMockMvc;

    private Benefit benefit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BenefitResource benefitResource = new BenefitResource(benefitService);
        this.restBenefitMockMvc = MockMvcBuilders.standaloneSetup(benefitResource)
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
    public static Benefit createEntity(EntityManager em) {
        Benefit benefit = new Benefit()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .benefitType(DEFAULT_BENEFIT_TYPE);
        return benefit;
    }

    @Before
    public void initTest() {
        benefit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBenefit() throws Exception {
        int databaseSizeBeforeCreate = benefitRepository.findAll().size();

        // Create the Benefit
        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isCreated());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate + 1);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBenefit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBenefit.getBenefitType()).isEqualTo(DEFAULT_BENEFIT_TYPE);
    }

    @Test
    @Transactional
    public void createBenefitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = benefitRepository.findAll().size();

        // Create the Benefit with an existing ID
        benefit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setStartDate(null);

        // Create the Benefit, which fails.

        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBenefits() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get all the benefitList
        restBenefitMockMvc.perform(get("/api/benefits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].benefitType").value(hasItem(DEFAULT_BENEFIT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get the benefit
        restBenefitMockMvc.perform(get("/api/benefits/{id}", benefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(benefit.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.benefitType").value(DEFAULT_BENEFIT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBenefit() throws Exception {
        // Get the benefit
        restBenefitMockMvc.perform(get("/api/benefits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBenefit() throws Exception {
        // Initialize the database
        benefitService.save(benefit);

        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Update the benefit
        Benefit updatedBenefit = benefitRepository.findOne(benefit.getId());
        // Disconnect from session so that the updates on updatedBenefit are not directly saved in db
        em.detach(updatedBenefit);
        updatedBenefit
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .benefitType(UPDATED_BENEFIT_TYPE);

        restBenefitMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBenefit)))
            .andExpect(status().isOk());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBenefit.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBenefit.getBenefitType()).isEqualTo(UPDATED_BENEFIT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Create the Benefit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBenefitMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isCreated());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBenefit() throws Exception {
        // Initialize the database
        benefitService.save(benefit);

        int databaseSizeBeforeDelete = benefitRepository.findAll().size();

        // Get the benefit
        restBenefitMockMvc.perform(delete("/api/benefits/{id}", benefit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benefit.class);
        Benefit benefit1 = new Benefit();
        benefit1.setId(1L);
        Benefit benefit2 = new Benefit();
        benefit2.setId(benefit1.getId());
        assertThat(benefit1).isEqualTo(benefit2);
        benefit2.setId(2L);
        assertThat(benefit1).isNotEqualTo(benefit2);
        benefit1.setId(null);
        assertThat(benefit1).isNotEqualTo(benefit2);
    }
}
