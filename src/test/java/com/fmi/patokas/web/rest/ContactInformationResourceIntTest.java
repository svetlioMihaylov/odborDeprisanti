package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.ContactInformation;
import com.fmi.patokas.repository.ContactInformationRepository;
import com.fmi.patokas.service.ContactInformationService;
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
 * Test class for the ContactInformationResource REST controller.
 *
 * @see ContactInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class ContactInformationResourceIntTest {

    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONAL_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_PERSONAL_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ContactInformationRepository contactInformationRepository;

    @Autowired
    private ContactInformationService contactInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactInformationMockMvc;

    private ContactInformation contactInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactInformationResource contactInformationResource = new ContactInformationResource(contactInformationService);
        this.restContactInformationMockMvc = MockMvcBuilders.standaloneSetup(contactInformationResource)
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
    public static ContactInformation createEntity(EntityManager em) {
        ContactInformation contactInformation = new ContactInformation()
            .permanentAddress(DEFAULT_PERMANENT_ADDRESS)
            .currentAddress(DEFAULT_CURRENT_ADDRESS)
            .personalMail(DEFAULT_PERSONAL_MAIL)
            .phone(DEFAULT_PHONE);
        return contactInformation;
    }

    @Before
    public void initTest() {
        contactInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactInformation() throws Exception {
        int databaseSizeBeforeCreate = contactInformationRepository.findAll().size();

        // Create the ContactInformation
        restContactInformationMockMvc.perform(post("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isCreated());

        // Validate the ContactInformation in the database
        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ContactInformation testContactInformation = contactInformationList.get(contactInformationList.size() - 1);
        assertThat(testContactInformation.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testContactInformation.getCurrentAddress()).isEqualTo(DEFAULT_CURRENT_ADDRESS);
        assertThat(testContactInformation.getPersonalMail()).isEqualTo(DEFAULT_PERSONAL_MAIL);
        assertThat(testContactInformation.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createContactInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactInformationRepository.findAll().size();

        // Create the ContactInformation with an existing ID
        contactInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactInformationMockMvc.perform(post("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isBadRequest());

        // Validate the ContactInformation in the database
        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPermanentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInformationRepository.findAll().size();
        // set the field null
        contactInformation.setPermanentAddress(null);

        // Create the ContactInformation, which fails.

        restContactInformationMockMvc.perform(post("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isBadRequest());

        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInformationRepository.findAll().size();
        // set the field null
        contactInformation.setCurrentAddress(null);

        // Create the ContactInformation, which fails.

        restContactInformationMockMvc.perform(post("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isBadRequest());

        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInformationRepository.findAll().size();
        // set the field null
        contactInformation.setPhone(null);

        // Create the ContactInformation, which fails.

        restContactInformationMockMvc.perform(post("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isBadRequest());

        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactInformations() throws Exception {
        // Initialize the database
        contactInformationRepository.saveAndFlush(contactInformation);

        // Get all the contactInformationList
        restContactInformationMockMvc.perform(get("/api/contact-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].currentAddress").value(hasItem(DEFAULT_CURRENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].personalMail").value(hasItem(DEFAULT_PERSONAL_MAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getContactInformation() throws Exception {
        // Initialize the database
        contactInformationRepository.saveAndFlush(contactInformation);

        // Get the contactInformation
        restContactInformationMockMvc.perform(get("/api/contact-informations/{id}", contactInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactInformation.getId().intValue()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.currentAddress").value(DEFAULT_CURRENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.personalMail").value(DEFAULT_PERSONAL_MAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactInformation() throws Exception {
        // Get the contactInformation
        restContactInformationMockMvc.perform(get("/api/contact-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactInformation() throws Exception {
        // Initialize the database
        contactInformationService.save(contactInformation);

        int databaseSizeBeforeUpdate = contactInformationRepository.findAll().size();

        // Update the contactInformation
        ContactInformation updatedContactInformation = contactInformationRepository.findOne(contactInformation.getId());
        // Disconnect from session so that the updates on updatedContactInformation are not directly saved in db
        em.detach(updatedContactInformation);
        updatedContactInformation
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .currentAddress(UPDATED_CURRENT_ADDRESS)
            .personalMail(UPDATED_PERSONAL_MAIL)
            .phone(UPDATED_PHONE);

        restContactInformationMockMvc.perform(put("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactInformation)))
            .andExpect(status().isOk());

        // Validate the ContactInformation in the database
        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeUpdate);
        ContactInformation testContactInformation = contactInformationList.get(contactInformationList.size() - 1);
        assertThat(testContactInformation.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testContactInformation.getCurrentAddress()).isEqualTo(UPDATED_CURRENT_ADDRESS);
        assertThat(testContactInformation.getPersonalMail()).isEqualTo(UPDATED_PERSONAL_MAIL);
        assertThat(testContactInformation.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingContactInformation() throws Exception {
        int databaseSizeBeforeUpdate = contactInformationRepository.findAll().size();

        // Create the ContactInformation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactInformationMockMvc.perform(put("/api/contact-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInformation)))
            .andExpect(status().isCreated());

        // Validate the ContactInformation in the database
        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactInformation() throws Exception {
        // Initialize the database
        contactInformationService.save(contactInformation);

        int databaseSizeBeforeDelete = contactInformationRepository.findAll().size();

        // Get the contactInformation
        restContactInformationMockMvc.perform(delete("/api/contact-informations/{id}", contactInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactInformation> contactInformationList = contactInformationRepository.findAll();
        assertThat(contactInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInformation.class);
        ContactInformation contactInformation1 = new ContactInformation();
        contactInformation1.setId(1L);
        ContactInformation contactInformation2 = new ContactInformation();
        contactInformation2.setId(contactInformation1.getId());
        assertThat(contactInformation1).isEqualTo(contactInformation2);
        contactInformation2.setId(2L);
        assertThat(contactInformation1).isNotEqualTo(contactInformation2);
        contactInformation1.setId(null);
        assertThat(contactInformation1).isNotEqualTo(contactInformation2);
    }
}
