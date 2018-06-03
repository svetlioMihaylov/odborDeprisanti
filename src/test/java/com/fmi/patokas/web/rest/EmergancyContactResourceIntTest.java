package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.EmergancyContact;
import com.fmi.patokas.repository.EmergancyContactRepository;
import com.fmi.patokas.service.EmergancyContactService;
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
 * Test class for the EmergancyContactResource REST controller.
 *
 * @see EmergancyContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class EmergancyContactResourceIntTest {

    private static final String DEFAULT_EMERGENCY_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_CONTACT_PHONE = "BBBBBBBBBB";

    @Autowired
    private EmergancyContactRepository emergancyContactRepository;

    @Autowired
    private EmergancyContactService emergancyContactService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmergancyContactMockMvc;

    private EmergancyContact emergancyContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmergancyContactResource emergancyContactResource = new EmergancyContactResource(emergancyContactService);
        this.restEmergancyContactMockMvc = MockMvcBuilders.standaloneSetup(emergancyContactResource)
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
    public static EmergancyContact createEntity(EntityManager em) {
        EmergancyContact emergancyContact = new EmergancyContact()
            .emergencyContactName(DEFAULT_EMERGENCY_CONTACT_NAME)
            .emergencyContactPhone(DEFAULT_EMERGENCY_CONTACT_PHONE);
        return emergancyContact;
    }

    @Before
    public void initTest() {
        emergancyContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergancyContact() throws Exception {
        int databaseSizeBeforeCreate = emergancyContactRepository.findAll().size();

        // Create the EmergancyContact
        restEmergancyContactMockMvc.perform(post("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergancyContact)))
            .andExpect(status().isCreated());

        // Validate the EmergancyContact in the database
        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeCreate + 1);
        EmergancyContact testEmergancyContact = emergancyContactList.get(emergancyContactList.size() - 1);
        assertThat(testEmergancyContact.getEmergencyContactName()).isEqualTo(DEFAULT_EMERGENCY_CONTACT_NAME);
        assertThat(testEmergancyContact.getEmergencyContactPhone()).isEqualTo(DEFAULT_EMERGENCY_CONTACT_PHONE);
    }

    @Test
    @Transactional
    public void createEmergancyContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergancyContactRepository.findAll().size();

        // Create the EmergancyContact with an existing ID
        emergancyContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergancyContactMockMvc.perform(post("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergancyContact)))
            .andExpect(status().isBadRequest());

        // Validate the EmergancyContact in the database
        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmergencyContactNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergancyContactRepository.findAll().size();
        // set the field null
        emergancyContact.setEmergencyContactName(null);

        // Create the EmergancyContact, which fails.

        restEmergancyContactMockMvc.perform(post("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergancyContact)))
            .andExpect(status().isBadRequest());

        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmergencyContactPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergancyContactRepository.findAll().size();
        // set the field null
        emergancyContact.setEmergencyContactPhone(null);

        // Create the EmergancyContact, which fails.

        restEmergancyContactMockMvc.perform(post("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergancyContact)))
            .andExpect(status().isBadRequest());

        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmergancyContacts() throws Exception {
        // Initialize the database
        emergancyContactRepository.saveAndFlush(emergancyContact);

        // Get all the emergancyContactList
        restEmergancyContactMockMvc.perform(get("/api/emergancy-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergancyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].emergencyContactName").value(hasItem(DEFAULT_EMERGENCY_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].emergencyContactPhone").value(hasItem(DEFAULT_EMERGENCY_CONTACT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getEmergancyContact() throws Exception {
        // Initialize the database
        emergancyContactRepository.saveAndFlush(emergancyContact);

        // Get the emergancyContact
        restEmergancyContactMockMvc.perform(get("/api/emergancy-contacts/{id}", emergancyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emergancyContact.getId().intValue()))
            .andExpect(jsonPath("$.emergencyContactName").value(DEFAULT_EMERGENCY_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.emergencyContactPhone").value(DEFAULT_EMERGENCY_CONTACT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmergancyContact() throws Exception {
        // Get the emergancyContact
        restEmergancyContactMockMvc.perform(get("/api/emergancy-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergancyContact() throws Exception {
        // Initialize the database
        emergancyContactService.save(emergancyContact);

        int databaseSizeBeforeUpdate = emergancyContactRepository.findAll().size();

        // Update the emergancyContact
        EmergancyContact updatedEmergancyContact = emergancyContactRepository.findOne(emergancyContact.getId());
        // Disconnect from session so that the updates on updatedEmergancyContact are not directly saved in db
        em.detach(updatedEmergancyContact);
        updatedEmergancyContact
            .emergencyContactName(UPDATED_EMERGENCY_CONTACT_NAME)
            .emergencyContactPhone(UPDATED_EMERGENCY_CONTACT_PHONE);

        restEmergancyContactMockMvc.perform(put("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmergancyContact)))
            .andExpect(status().isOk());

        // Validate the EmergancyContact in the database
        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeUpdate);
        EmergancyContact testEmergancyContact = emergancyContactList.get(emergancyContactList.size() - 1);
        assertThat(testEmergancyContact.getEmergencyContactName()).isEqualTo(UPDATED_EMERGENCY_CONTACT_NAME);
        assertThat(testEmergancyContact.getEmergencyContactPhone()).isEqualTo(UPDATED_EMERGENCY_CONTACT_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergancyContact() throws Exception {
        int databaseSizeBeforeUpdate = emergancyContactRepository.findAll().size();

        // Create the EmergancyContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmergancyContactMockMvc.perform(put("/api/emergancy-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergancyContact)))
            .andExpect(status().isCreated());

        // Validate the EmergancyContact in the database
        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmergancyContact() throws Exception {
        // Initialize the database
        emergancyContactService.save(emergancyContact);

        int databaseSizeBeforeDelete = emergancyContactRepository.findAll().size();

        // Get the emergancyContact
        restEmergancyContactMockMvc.perform(delete("/api/emergancy-contacts/{id}", emergancyContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmergancyContact> emergancyContactList = emergancyContactRepository.findAll();
        assertThat(emergancyContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergancyContact.class);
        EmergancyContact emergancyContact1 = new EmergancyContact();
        emergancyContact1.setId(1L);
        EmergancyContact emergancyContact2 = new EmergancyContact();
        emergancyContact2.setId(emergancyContact1.getId());
        assertThat(emergancyContact1).isEqualTo(emergancyContact2);
        emergancyContact2.setId(2L);
        assertThat(emergancyContact1).isNotEqualTo(emergancyContact2);
        emergancyContact1.setId(null);
        assertThat(emergancyContact1).isNotEqualTo(emergancyContact2);
    }
}
