package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.EmployeePhoto;
import com.fmi.patokas.repository.EmployeePhotoRepository;
import com.fmi.patokas.service.EmployeePhotoService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fmi.patokas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeePhotoResource REST controller.
 *
 * @see EmployeePhotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class EmployeePhotoResourceIntTest {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private EmployeePhotoRepository employeePhotoRepository;

    @Autowired
    private EmployeePhotoService employeePhotoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeePhotoMockMvc;

    private EmployeePhoto employeePhoto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeePhotoResource employeePhotoResource = new EmployeePhotoResource(employeePhotoService);
        this.restEmployeePhotoMockMvc = MockMvcBuilders.standaloneSetup(employeePhotoResource)
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
    public static EmployeePhoto createEntity(EntityManager em) {
        EmployeePhoto employeePhoto = new EmployeePhoto()
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return employeePhoto;
    }

    @Before
    public void initTest() {
        employeePhoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeePhoto() throws Exception {
        int databaseSizeBeforeCreate = employeePhotoRepository.findAll().size();

        // Create the EmployeePhoto
        restEmployeePhotoMockMvc.perform(post("/api/employee-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePhoto)))
            .andExpect(status().isCreated());

        // Validate the EmployeePhoto in the database
        List<EmployeePhoto> employeePhotoList = employeePhotoRepository.findAll();
        assertThat(employeePhotoList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePhoto testEmployeePhoto = employeePhotoList.get(employeePhotoList.size() - 1);
        assertThat(testEmployeePhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmployeePhoto.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEmployeePhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeePhotoRepository.findAll().size();

        // Create the EmployeePhoto with an existing ID
        employeePhoto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeePhotoMockMvc.perform(post("/api/employee-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePhoto)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePhoto in the database
        List<EmployeePhoto> employeePhotoList = employeePhotoRepository.findAll();
        assertThat(employeePhotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeePhotos() throws Exception {
        // Initialize the database
        employeePhotoRepository.saveAndFlush(employeePhoto);

        // Get all the employeePhotoList
        restEmployeePhotoMockMvc.perform(get("/api/employee-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeePhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getEmployeePhoto() throws Exception {
        // Initialize the database
        employeePhotoRepository.saveAndFlush(employeePhoto);

        // Get the employeePhoto
        restEmployeePhotoMockMvc.perform(get("/api/employee-photos/{id}", employeePhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeePhoto.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeePhoto() throws Exception {
        // Get the employeePhoto
        restEmployeePhotoMockMvc.perform(get("/api/employee-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeePhoto() throws Exception {
        // Initialize the database
        employeePhotoService.save(employeePhoto);

        int databaseSizeBeforeUpdate = employeePhotoRepository.findAll().size();

        // Update the employeePhoto
        EmployeePhoto updatedEmployeePhoto = employeePhotoRepository.findOne(employeePhoto.getId());
        // Disconnect from session so that the updates on updatedEmployeePhoto are not directly saved in db
        em.detach(updatedEmployeePhoto);
        updatedEmployeePhoto
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restEmployeePhotoMockMvc.perform(put("/api/employee-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeePhoto)))
            .andExpect(status().isOk());

        // Validate the EmployeePhoto in the database
        List<EmployeePhoto> employeePhotoList = employeePhotoRepository.findAll();
        assertThat(employeePhotoList).hasSize(databaseSizeBeforeUpdate);
        EmployeePhoto testEmployeePhoto = employeePhotoList.get(employeePhotoList.size() - 1);
        assertThat(testEmployeePhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmployeePhoto.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeePhoto() throws Exception {
        int databaseSizeBeforeUpdate = employeePhotoRepository.findAll().size();

        // Create the EmployeePhoto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeePhotoMockMvc.perform(put("/api/employee-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePhoto)))
            .andExpect(status().isCreated());

        // Validate the EmployeePhoto in the database
        List<EmployeePhoto> employeePhotoList = employeePhotoRepository.findAll();
        assertThat(employeePhotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeePhoto() throws Exception {
        // Initialize the database
        employeePhotoService.save(employeePhoto);

        int databaseSizeBeforeDelete = employeePhotoRepository.findAll().size();

        // Get the employeePhoto
        restEmployeePhotoMockMvc.perform(delete("/api/employee-photos/{id}", employeePhoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeePhoto> employeePhotoList = employeePhotoRepository.findAll();
        assertThat(employeePhotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePhoto.class);
        EmployeePhoto employeePhoto1 = new EmployeePhoto();
        employeePhoto1.setId(1L);
        EmployeePhoto employeePhoto2 = new EmployeePhoto();
        employeePhoto2.setId(employeePhoto1.getId());
        assertThat(employeePhoto1).isEqualTo(employeePhoto2);
        employeePhoto2.setId(2L);
        assertThat(employeePhoto1).isNotEqualTo(employeePhoto2);
        employeePhoto1.setId(null);
        assertThat(employeePhoto1).isNotEqualTo(employeePhoto2);
    }
}
