package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.Cause;
import com.db.hackathon.repository.CauseRepository;
import com.db.hackathon.service.CauseService;
import com.db.hackathon.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.db.hackathon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CauseResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class CauseResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_UNLOCK_LEVEL = 1;
    private static final Integer UPDATED_UNLOCK_LEVEL = 2;

    private static final Long DEFAULT_TARGET = 1L;
    private static final Long UPDATED_TARGET = 2L;

    private static final Long DEFAULT_CURRENCY_AMOUNT = 1L;
    private static final Long UPDATED_CURRENCY_AMOUNT = 2L;

    @Autowired
    private CauseRepository causeRepository;

    @Autowired
    private CauseService causeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCauseMockMvc;

    private Cause cause;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CauseResource causeResource = new CauseResource(causeService);
        this.restCauseMockMvc = MockMvcBuilders.standaloneSetup(causeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cause createEntity() {
        Cause cause = new Cause()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .unlockLevel(DEFAULT_UNLOCK_LEVEL)
            .target(DEFAULT_TARGET)
            .currencyAmount(DEFAULT_CURRENCY_AMOUNT);
        return cause;
    }

    @BeforeEach
    public void initTest() {
        causeRepository.deleteAll();
        cause = createEntity();
    }

    @Test
    public void createCause() throws Exception {
        int databaseSizeBeforeCreate = causeRepository.findAll().size();

        // Create the Cause
        restCauseMockMvc.perform(post("/api/causes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cause)))
            .andExpect(status().isCreated());

        // Validate the Cause in the database
        List<Cause> causeList = causeRepository.findAll();
        assertThat(causeList).hasSize(databaseSizeBeforeCreate + 1);
        Cause testCause = causeList.get(causeList.size() - 1);
        assertThat(testCause.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCause.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCause.getUnlockLevel()).isEqualTo(DEFAULT_UNLOCK_LEVEL);
        assertThat(testCause.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testCause.getCurrencyAmount()).isEqualTo(DEFAULT_CURRENCY_AMOUNT);
    }

    @Test
    public void createCauseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = causeRepository.findAll().size();

        // Create the Cause with an existing ID
        cause.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCauseMockMvc.perform(post("/api/causes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cause)))
            .andExpect(status().isBadRequest());

        // Validate the Cause in the database
        List<Cause> causeList = causeRepository.findAll();
        assertThat(causeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCauses() throws Exception {
        // Initialize the database
        causeRepository.save(cause);

        // Get all the causeList
        restCauseMockMvc.perform(get("/api/causes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cause.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].unlockLevel").value(hasItem(DEFAULT_UNLOCK_LEVEL)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.intValue())))
            .andExpect(jsonPath("$.[*].currencyAmount").value(hasItem(DEFAULT_CURRENCY_AMOUNT.intValue())));
    }
    
    @Test
    public void getCause() throws Exception {
        // Initialize the database
        causeRepository.save(cause);

        // Get the cause
        restCauseMockMvc.perform(get("/api/causes/{id}", cause.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cause.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.unlockLevel").value(DEFAULT_UNLOCK_LEVEL))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET.intValue()))
            .andExpect(jsonPath("$.currencyAmount").value(DEFAULT_CURRENCY_AMOUNT.intValue()));
    }

    @Test
    public void getNonExistingCause() throws Exception {
        // Get the cause
        restCauseMockMvc.perform(get("/api/causes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCause() throws Exception {
        // Initialize the database
        causeService.save(cause);

        int databaseSizeBeforeUpdate = causeRepository.findAll().size();

        // Update the cause
        Cause updatedCause = causeRepository.findById(cause.getId()).get();
        updatedCause
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .unlockLevel(UPDATED_UNLOCK_LEVEL)
            .target(UPDATED_TARGET)
            .currencyAmount(UPDATED_CURRENCY_AMOUNT);

        restCauseMockMvc.perform(put("/api/causes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCause)))
            .andExpect(status().isOk());

        // Validate the Cause in the database
        List<Cause> causeList = causeRepository.findAll();
        assertThat(causeList).hasSize(databaseSizeBeforeUpdate);
        Cause testCause = causeList.get(causeList.size() - 1);
        assertThat(testCause.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCause.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCause.getUnlockLevel()).isEqualTo(UPDATED_UNLOCK_LEVEL);
        assertThat(testCause.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testCause.getCurrencyAmount()).isEqualTo(UPDATED_CURRENCY_AMOUNT);
    }

    @Test
    public void updateNonExistingCause() throws Exception {
        int databaseSizeBeforeUpdate = causeRepository.findAll().size();

        // Create the Cause

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCauseMockMvc.perform(put("/api/causes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cause)))
            .andExpect(status().isBadRequest());

        // Validate the Cause in the database
        List<Cause> causeList = causeRepository.findAll();
        assertThat(causeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCause() throws Exception {
        // Initialize the database
        causeService.save(cause);

        int databaseSizeBeforeDelete = causeRepository.findAll().size();

        // Delete the cause
        restCauseMockMvc.perform(delete("/api/causes/{id}", cause.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Cause> causeList = causeRepository.findAll();
        assertThat(causeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cause.class);
        Cause cause1 = new Cause();
        cause1.setId("id1");
        Cause cause2 = new Cause();
        cause2.setId(cause1.getId());
        assertThat(cause1).isEqualTo(cause2);
        cause2.setId("id2");
        assertThat(cause1).isNotEqualTo(cause2);
        cause1.setId(null);
        assertThat(cause1).isNotEqualTo(cause2);
    }
}
