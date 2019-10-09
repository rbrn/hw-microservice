package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.Status;
import com.db.hackathon.repository.StatusRepository;
import com.db.hackathon.service.StatusService;
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
 * Integration tests for the {@Link StatusResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class StatusResourceIT {

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final Float DEFAULT_SUB_LEVEL = 1F;
    private static final Float UPDATED_SUB_LEVEL = 2F;

    private static final Long DEFAULT_CURRENCY_AMOUNT = 1L;
    private static final Long UPDATED_CURRENCY_AMOUNT = 2L;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restStatusMockMvc;

    private Status status;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusResource statusResource = new StatusResource(statusService);
        this.restStatusMockMvc = MockMvcBuilders.standaloneSetup(statusResource)
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
    public static Status createEntity() {
        Status status = new Status()
            .level(DEFAULT_LEVEL)
            .subLevel(DEFAULT_SUB_LEVEL)
            .currencyAmount(DEFAULT_CURRENCY_AMOUNT);
        return status;
    }

    @BeforeEach
    public void initTest() {
        statusRepository.deleteAll();
        status = createEntity();
    }

    @Test
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testStatus.getSubLevel()).isEqualTo(DEFAULT_SUB_LEVEL);
        assertThat(testStatus.getCurrencyAmount()).isEqualTo(DEFAULT_CURRENCY_AMOUNT);
    }

    @Test
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].subLevel").value(hasItem(DEFAULT_SUB_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyAmount").value(hasItem(DEFAULT_CURRENCY_AMOUNT.intValue())));
    }
    
    @Test
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.subLevel").value(DEFAULT_SUB_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.currencyAmount").value(DEFAULT_CURRENCY_AMOUNT.intValue()));
    }

    @Test
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        updatedStatus
            .level(UPDATED_LEVEL)
            .subLevel(UPDATED_SUB_LEVEL)
            .currencyAmount(UPDATED_CURRENCY_AMOUNT);

        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testStatus.getSubLevel()).isEqualTo(UPDATED_SUB_LEVEL);
        assertThat(testStatus.getCurrencyAmount()).isEqualTo(UPDATED_CURRENCY_AMOUNT);
    }

    @Test
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Create the Status

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Status.class);
        Status status1 = new Status();
        status1.setId("id1");
        Status status2 = new Status();
        status2.setId(status1.getId());
        assertThat(status1).isEqualTo(status2);
        status2.setId("id2");
        assertThat(status1).isNotEqualTo(status2);
        status1.setId(null);
        assertThat(status1).isNotEqualTo(status2);
    }
}
