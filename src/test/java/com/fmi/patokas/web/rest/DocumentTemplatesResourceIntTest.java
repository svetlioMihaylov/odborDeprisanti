package com.fmi.patokas.web.rest;

import com.fmi.patokas.PatokasApp;

import com.fmi.patokas.domain.DocumentTemplates;
import com.fmi.patokas.repository.DocumentTemplatesRepository;
import com.fmi.patokas.service.DocumentTemplatesService;
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
 * Test class for the DocumentTemplatesResource REST controller.
 *
 * @see DocumentTemplatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatokasApp.class)
public class DocumentTemplatesResourceIntTest {

    private static final String DEFAULT_TEMPLATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private DocumentTemplatesRepository documentTemplatesRepository;

    @Autowired
    private DocumentTemplatesService documentTemplatesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentTemplatesMockMvc;

    private DocumentTemplates documentTemplates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTemplatesResource documentTemplatesResource = new DocumentTemplatesResource(documentTemplatesService);
        this.restDocumentTemplatesMockMvc = MockMvcBuilders.standaloneSetup(documentTemplatesResource)
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
    public static DocumentTemplates createEntity(EntityManager em) {
        DocumentTemplates documentTemplates = new DocumentTemplates()
            .templateType(DEFAULT_TEMPLATE_TYPE)
            .fileLocation(DEFAULT_FILE_LOCATION)
            .content(DEFAULT_CONTENT);
        return documentTemplates;
    }

    @Before
    public void initTest() {
        documentTemplates = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTemplates() throws Exception {
        int databaseSizeBeforeCreate = documentTemplatesRepository.findAll().size();

        // Create the DocumentTemplates
        restDocumentTemplatesMockMvc.perform(post("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplates)))
            .andExpect(status().isCreated());

        // Validate the DocumentTemplates in the database
        List<DocumentTemplates> documentTemplatesList = documentTemplatesRepository.findAll();
        assertThat(documentTemplatesList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTemplates testDocumentTemplates = documentTemplatesList.get(documentTemplatesList.size() - 1);
        assertThat(testDocumentTemplates.getTemplateType()).isEqualTo(DEFAULT_TEMPLATE_TYPE);
        assertThat(testDocumentTemplates.getFileLocation()).isEqualTo(DEFAULT_FILE_LOCATION);
        assertThat(testDocumentTemplates.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createDocumentTemplatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTemplatesRepository.findAll().size();

        // Create the DocumentTemplates with an existing ID
        documentTemplates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTemplatesMockMvc.perform(post("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplates)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTemplates in the database
        List<DocumentTemplates> documentTemplatesList = documentTemplatesRepository.findAll();
        assertThat(documentTemplatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentTemplates() throws Exception {
        // Initialize the database
        documentTemplatesRepository.saveAndFlush(documentTemplates);

        // Get all the documentTemplatesList
        restDocumentTemplatesMockMvc.perform(get("/api/document-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTemplates.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateType").value(hasItem(DEFAULT_TEMPLATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileLocation").value(hasItem(DEFAULT_FILE_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getDocumentTemplates() throws Exception {
        // Initialize the database
        documentTemplatesRepository.saveAndFlush(documentTemplates);

        // Get the documentTemplates
        restDocumentTemplatesMockMvc.perform(get("/api/document-templates/{id}", documentTemplates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTemplates.getId().intValue()))
            .andExpect(jsonPath("$.templateType").value(DEFAULT_TEMPLATE_TYPE.toString()))
            .andExpect(jsonPath("$.fileLocation").value(DEFAULT_FILE_LOCATION.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTemplates() throws Exception {
        // Get the documentTemplates
        restDocumentTemplatesMockMvc.perform(get("/api/document-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTemplates() throws Exception {
        // Initialize the database
        documentTemplatesService.save(documentTemplates);

        int databaseSizeBeforeUpdate = documentTemplatesRepository.findAll().size();

        // Update the documentTemplates
        DocumentTemplates updatedDocumentTemplates = documentTemplatesRepository.findOne(documentTemplates.getId());
        // Disconnect from session so that the updates on updatedDocumentTemplates are not directly saved in db
        em.detach(updatedDocumentTemplates);
        updatedDocumentTemplates
            .templateType(UPDATED_TEMPLATE_TYPE)
            .fileLocation(UPDATED_FILE_LOCATION)
            .content(UPDATED_CONTENT);

        restDocumentTemplatesMockMvc.perform(put("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTemplates)))
            .andExpect(status().isOk());

        // Validate the DocumentTemplates in the database
        List<DocumentTemplates> documentTemplatesList = documentTemplatesRepository.findAll();
        assertThat(documentTemplatesList).hasSize(databaseSizeBeforeUpdate);
        DocumentTemplates testDocumentTemplates = documentTemplatesList.get(documentTemplatesList.size() - 1);
        assertThat(testDocumentTemplates.getTemplateType()).isEqualTo(UPDATED_TEMPLATE_TYPE);
        assertThat(testDocumentTemplates.getFileLocation()).isEqualTo(UPDATED_FILE_LOCATION);
        assertThat(testDocumentTemplates.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTemplates() throws Exception {
        int databaseSizeBeforeUpdate = documentTemplatesRepository.findAll().size();

        // Create the DocumentTemplates

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentTemplatesMockMvc.perform(put("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplates)))
            .andExpect(status().isCreated());

        // Validate the DocumentTemplates in the database
        List<DocumentTemplates> documentTemplatesList = documentTemplatesRepository.findAll();
        assertThat(documentTemplatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocumentTemplates() throws Exception {
        // Initialize the database
        documentTemplatesService.save(documentTemplates);

        int databaseSizeBeforeDelete = documentTemplatesRepository.findAll().size();

        // Get the documentTemplates
        restDocumentTemplatesMockMvc.perform(delete("/api/document-templates/{id}", documentTemplates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTemplates> documentTemplatesList = documentTemplatesRepository.findAll();
        assertThat(documentTemplatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTemplates.class);
        DocumentTemplates documentTemplates1 = new DocumentTemplates();
        documentTemplates1.setId(1L);
        DocumentTemplates documentTemplates2 = new DocumentTemplates();
        documentTemplates2.setId(documentTemplates1.getId());
        assertThat(documentTemplates1).isEqualTo(documentTemplates2);
        documentTemplates2.setId(2L);
        assertThat(documentTemplates1).isNotEqualTo(documentTemplates2);
        documentTemplates1.setId(null);
        assertThat(documentTemplates1).isNotEqualTo(documentTemplates2);
    }
}
