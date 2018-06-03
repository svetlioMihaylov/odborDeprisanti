package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.IDCard;
import com.fmi.patokas.repository.IDCardRepository;
import com.fmi.patokas.service.IDCardService;
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
 * Test class for the IDCardResource REST controller.
 *
 * @see IDCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class IDCardResourceIntTest {

    private static final String DEFAULT_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_ISSUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ISSUE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_EXPIRY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_EXPIRY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ISSUED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUED_BY = "BBBBBBBBBB";

    @Autowired
    private IDCardRepository iDCardRepository;

    @Autowired
    private IDCardService iDCardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIDCardMockMvc;

    private IDCard iDCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IDCardResource iDCardResource = new IDCardResource(iDCardService);
        this.restIDCardMockMvc = MockMvcBuilders.standaloneSetup(iDCardResource)
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
    public static IDCard createEntity(EntityManager em) {
        IDCard iDCard = new IDCard()
            .idNumber(DEFAULT_ID_NUMBER)
            .dateOfIssue(DEFAULT_DATE_OF_ISSUE)
            .dateOfExpiry(DEFAULT_DATE_OF_EXPIRY)
            .issuedBy(DEFAULT_ISSUED_BY);
        return iDCard;
    }

    @Before
    public void initTest() {
        iDCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createIDCard() throws Exception {
        int databaseSizeBeforeCreate = iDCardRepository.findAll().size();

        // Create the IDCard
        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isCreated());

        // Validate the IDCard in the database
        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeCreate + 1);
        IDCard testIDCard = iDCardList.get(iDCardList.size() - 1);
        assertThat(testIDCard.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testIDCard.getDateOfIssue()).isEqualTo(DEFAULT_DATE_OF_ISSUE);
        assertThat(testIDCard.getDateOfExpiry()).isEqualTo(DEFAULT_DATE_OF_EXPIRY);
        assertThat(testIDCard.getIssuedBy()).isEqualTo(DEFAULT_ISSUED_BY);
    }

    @Test
    @Transactional
    public void createIDCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iDCardRepository.findAll().size();

        // Create the IDCard with an existing ID
        iDCard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isBadRequest());

        // Validate the IDCard in the database
        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = iDCardRepository.findAll().size();
        // set the field null
        iDCard.setIdNumber(null);

        // Create the IDCard, which fails.

        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isBadRequest());

        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfIssueIsRequired() throws Exception {
        int databaseSizeBeforeTest = iDCardRepository.findAll().size();
        // set the field null
        iDCard.setDateOfIssue(null);

        // Create the IDCard, which fails.

        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isBadRequest());

        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfExpiryIsRequired() throws Exception {
        int databaseSizeBeforeTest = iDCardRepository.findAll().size();
        // set the field null
        iDCard.setDateOfExpiry(null);

        // Create the IDCard, which fails.

        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isBadRequest());

        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = iDCardRepository.findAll().size();
        // set the field null
        iDCard.setIssuedBy(null);

        // Create the IDCard, which fails.

        restIDCardMockMvc.perform(post("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isBadRequest());

        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIDCards() throws Exception {
        // Initialize the database
        iDCardRepository.saveAndFlush(iDCard);

        // Get all the iDCardList
        restIDCardMockMvc.perform(get("/api/id-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iDCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfIssue").value(hasItem(DEFAULT_DATE_OF_ISSUE.toString())))
            .andExpect(jsonPath("$.[*].dateOfExpiry").value(hasItem(DEFAULT_DATE_OF_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].issuedBy").value(hasItem(DEFAULT_ISSUED_BY.toString())));
    }

    @Test
    @Transactional
    public void getIDCard() throws Exception {
        // Initialize the database
        iDCardRepository.saveAndFlush(iDCard);

        // Get the iDCard
        restIDCardMockMvc.perform(get("/api/id-cards/{id}", iDCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iDCard.getId().intValue()))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfIssue").value(DEFAULT_DATE_OF_ISSUE.toString()))
            .andExpect(jsonPath("$.dateOfExpiry").value(DEFAULT_DATE_OF_EXPIRY.toString()))
            .andExpect(jsonPath("$.issuedBy").value(DEFAULT_ISSUED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIDCard() throws Exception {
        // Get the iDCard
        restIDCardMockMvc.perform(get("/api/id-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIDCard() throws Exception {
        // Initialize the database
        iDCardService.save(iDCard);

        int databaseSizeBeforeUpdate = iDCardRepository.findAll().size();

        // Update the iDCard
        IDCard updatedIDCard = iDCardRepository.findOne(iDCard.getId());
        // Disconnect from session so that the updates on updatedIDCard are not directly saved in db
        em.detach(updatedIDCard);
        updatedIDCard
            .idNumber(UPDATED_ID_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .dateOfExpiry(UPDATED_DATE_OF_EXPIRY)
            .issuedBy(UPDATED_ISSUED_BY);

        restIDCardMockMvc.perform(put("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIDCard)))
            .andExpect(status().isOk());

        // Validate the IDCard in the database
        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeUpdate);
        IDCard testIDCard = iDCardList.get(iDCardList.size() - 1);
        assertThat(testIDCard.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testIDCard.getDateOfIssue()).isEqualTo(UPDATED_DATE_OF_ISSUE);
        assertThat(testIDCard.getDateOfExpiry()).isEqualTo(UPDATED_DATE_OF_EXPIRY);
        assertThat(testIDCard.getIssuedBy()).isEqualTo(UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingIDCard() throws Exception {
        int databaseSizeBeforeUpdate = iDCardRepository.findAll().size();

        // Create the IDCard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIDCardMockMvc.perform(put("/api/id-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iDCard)))
            .andExpect(status().isCreated());

        // Validate the IDCard in the database
        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIDCard() throws Exception {
        // Initialize the database
        iDCardService.save(iDCard);

        int databaseSizeBeforeDelete = iDCardRepository.findAll().size();

        // Get the iDCard
        restIDCardMockMvc.perform(delete("/api/id-cards/{id}", iDCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IDCard> iDCardList = iDCardRepository.findAll();
        assertThat(iDCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IDCard.class);
        IDCard iDCard1 = new IDCard();
        iDCard1.setId(1L);
        IDCard iDCard2 = new IDCard();
        iDCard2.setId(iDCard1.getId());
        assertThat(iDCard1).isEqualTo(iDCard2);
        iDCard2.setId(2L);
        assertThat(iDCard1).isNotEqualTo(iDCard2);
        iDCard1.setId(null);
        assertThat(iDCard1).isNotEqualTo(iDCard2);
    }
}
