package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.ExternalPerson;
import com.fmi.patokas.repository.ExternalPersonRepository;
import com.fmi.patokas.service.ExternalPersonService;
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
 * Test class for the ExternalPersonResource REST controller.
 *
 * @see ExternalPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class ExternalPersonResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME_NATIVE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_NATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME_NATIVE = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME_NATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_NATIVE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_NATIVE = "BBBBBBBBBB";

    @Autowired
    private ExternalPersonRepository externalPersonRepository;

    @Autowired
    private ExternalPersonService externalPersonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExternalPersonMockMvc;

    private ExternalPerson externalPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExternalPersonResource externalPersonResource = new ExternalPersonResource(externalPersonService);
        this.restExternalPersonMockMvc = MockMvcBuilders.standaloneSetup(externalPersonResource)
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
    public static ExternalPerson createEntity(EntityManager em) {
        ExternalPerson externalPerson = new ExternalPerson()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .firstNameNative(DEFAULT_FIRST_NAME_NATIVE)
            .middleNameNative(DEFAULT_MIDDLE_NAME_NATIVE)
            .lastNameNative(DEFAULT_LAST_NAME_NATIVE);
        return externalPerson;
    }

    @Before
    public void initTest() {
        externalPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalPerson() throws Exception {
        int databaseSizeBeforeCreate = externalPersonRepository.findAll().size();

        // Create the ExternalPerson
        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isCreated());

        // Validate the ExternalPerson in the database
        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalPerson testExternalPerson = externalPersonList.get(externalPersonList.size() - 1);
        assertThat(testExternalPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testExternalPerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testExternalPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testExternalPerson.getFirstNameNative()).isEqualTo(DEFAULT_FIRST_NAME_NATIVE);
        assertThat(testExternalPerson.getMiddleNameNative()).isEqualTo(DEFAULT_MIDDLE_NAME_NATIVE);
        assertThat(testExternalPerson.getLastNameNative()).isEqualTo(DEFAULT_LAST_NAME_NATIVE);
    }

    @Test
    @Transactional
    public void createExternalPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalPersonRepository.findAll().size();

        // Create the ExternalPerson with an existing ID
        externalPerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalPerson in the database
        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setFirstName(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiddleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setMiddleName(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setLastName(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameNativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setFirstNameNative(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiddleNameNativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setMiddleNameNative(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameNativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalPersonRepository.findAll().size();
        // set the field null
        externalPerson.setLastNameNative(null);

        // Create the ExternalPerson, which fails.

        restExternalPersonMockMvc.perform(post("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isBadRequest());

        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExternalPeople() throws Exception {
        // Initialize the database
        externalPersonRepository.saveAndFlush(externalPerson);

        // Get all the externalPersonList
        restExternalPersonMockMvc.perform(get("/api/external-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstNameNative").value(hasItem(DEFAULT_FIRST_NAME_NATIVE.toString())))
            .andExpect(jsonPath("$.[*].middleNameNative").value(hasItem(DEFAULT_MIDDLE_NAME_NATIVE.toString())))
            .andExpect(jsonPath("$.[*].lastNameNative").value(hasItem(DEFAULT_LAST_NAME_NATIVE.toString())));
    }

    @Test
    @Transactional
    public void getExternalPerson() throws Exception {
        // Initialize the database
        externalPersonRepository.saveAndFlush(externalPerson);

        // Get the externalPerson
        restExternalPersonMockMvc.perform(get("/api/external-people/{id}", externalPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalPerson.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstNameNative").value(DEFAULT_FIRST_NAME_NATIVE.toString()))
            .andExpect(jsonPath("$.middleNameNative").value(DEFAULT_MIDDLE_NAME_NATIVE.toString()))
            .andExpect(jsonPath("$.lastNameNative").value(DEFAULT_LAST_NAME_NATIVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExternalPerson() throws Exception {
        // Get the externalPerson
        restExternalPersonMockMvc.perform(get("/api/external-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalPerson() throws Exception {
        // Initialize the database
        externalPersonService.save(externalPerson);

        int databaseSizeBeforeUpdate = externalPersonRepository.findAll().size();

        // Update the externalPerson
        ExternalPerson updatedExternalPerson = externalPersonRepository.findOne(externalPerson.getId());
        // Disconnect from session so that the updates on updatedExternalPerson are not directly saved in db
        em.detach(updatedExternalPerson);
        updatedExternalPerson
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .firstNameNative(UPDATED_FIRST_NAME_NATIVE)
            .middleNameNative(UPDATED_MIDDLE_NAME_NATIVE)
            .lastNameNative(UPDATED_LAST_NAME_NATIVE);

        restExternalPersonMockMvc.perform(put("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExternalPerson)))
            .andExpect(status().isOk());

        // Validate the ExternalPerson in the database
        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeUpdate);
        ExternalPerson testExternalPerson = externalPersonList.get(externalPersonList.size() - 1);
        assertThat(testExternalPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testExternalPerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testExternalPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testExternalPerson.getFirstNameNative()).isEqualTo(UPDATED_FIRST_NAME_NATIVE);
        assertThat(testExternalPerson.getMiddleNameNative()).isEqualTo(UPDATED_MIDDLE_NAME_NATIVE);
        assertThat(testExternalPerson.getLastNameNative()).isEqualTo(UPDATED_LAST_NAME_NATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalPerson() throws Exception {
        int databaseSizeBeforeUpdate = externalPersonRepository.findAll().size();

        // Create the ExternalPerson

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExternalPersonMockMvc.perform(put("/api/external-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalPerson)))
            .andExpect(status().isCreated());

        // Validate the ExternalPerson in the database
        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExternalPerson() throws Exception {
        // Initialize the database
        externalPersonService.save(externalPerson);

        int databaseSizeBeforeDelete = externalPersonRepository.findAll().size();

        // Get the externalPerson
        restExternalPersonMockMvc.perform(delete("/api/external-people/{id}", externalPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExternalPerson> externalPersonList = externalPersonRepository.findAll();
        assertThat(externalPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalPerson.class);
        ExternalPerson externalPerson1 = new ExternalPerson();
        externalPerson1.setId(1L);
        ExternalPerson externalPerson2 = new ExternalPerson();
        externalPerson2.setId(externalPerson1.getId());
        assertThat(externalPerson1).isEqualTo(externalPerson2);
        externalPerson2.setId(2L);
        assertThat(externalPerson1).isNotEqualTo(externalPerson2);
        externalPerson1.setId(null);
        assertThat(externalPerson1).isNotEqualTo(externalPerson2);
    }
}
