package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.WeeklyChallengeStatus;
import com.db.hackathon.repository.WeeklyChallengeStatusRepository;
import com.db.hackathon.service.WeeklyChallengeStatusService;
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
 * Integration tests for the {@Link WeeklyChallengeStatusResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class WeeklyChallengeStatusResourceIT {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Autowired
    private WeeklyChallengeStatusRepository weeklyChallengeStatusRepository;

    @Autowired
    private WeeklyChallengeStatusService weeklyChallengeStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restWeeklyChallengeStatusMockMvc;

    private WeeklyChallengeStatus weeklyChallengeStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeeklyChallengeStatusResource weeklyChallengeStatusResource = new WeeklyChallengeStatusResource(weeklyChallengeStatusService);
        this.restWeeklyChallengeStatusMockMvc = MockMvcBuilders.standaloneSetup(weeklyChallengeStatusResource)
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
    public static WeeklyChallengeStatus createEntity() {
        WeeklyChallengeStatus weeklyChallengeStatus = new WeeklyChallengeStatus()
            .count(DEFAULT_COUNT);
        return weeklyChallengeStatus;
    }

    @BeforeEach
    public void initTest() {
        weeklyChallengeStatusRepository.deleteAll();
        weeklyChallengeStatus = createEntity();
    }

    @Test
    public void createWeeklyChallengeStatus() throws Exception {
        int databaseSizeBeforeCreate = weeklyChallengeStatusRepository.findAll().size();

        // Create the WeeklyChallengeStatus
        restWeeklyChallengeStatusMockMvc.perform(post("/api/weekly-challenge-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyChallengeStatus)))
            .andExpect(status().isCreated());

        // Validate the WeeklyChallengeStatus in the database
        List<WeeklyChallengeStatus> weeklyChallengeStatusList = weeklyChallengeStatusRepository.findAll();
        assertThat(weeklyChallengeStatusList).hasSize(databaseSizeBeforeCreate + 1);
        WeeklyChallengeStatus testWeeklyChallengeStatus = weeklyChallengeStatusList.get(weeklyChallengeStatusList.size() - 1);
        assertThat(testWeeklyChallengeStatus.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    public void createWeeklyChallengeStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weeklyChallengeStatusRepository.findAll().size();

        // Create the WeeklyChallengeStatus with an existing ID
        weeklyChallengeStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeeklyChallengeStatusMockMvc.perform(post("/api/weekly-challenge-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyChallengeStatus)))
            .andExpect(status().isBadRequest());

        // Validate the WeeklyChallengeStatus in the database
        List<WeeklyChallengeStatus> weeklyChallengeStatusList = weeklyChallengeStatusRepository.findAll();
        assertThat(weeklyChallengeStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllWeeklyChallengeStatuses() throws Exception {
        // Initialize the database
        weeklyChallengeStatusRepository.save(weeklyChallengeStatus);

        // Get all the weeklyChallengeStatusList
        restWeeklyChallengeStatusMockMvc.perform(get("/api/weekly-challenge-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weeklyChallengeStatus.getId())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }
    
    @Test
    public void getWeeklyChallengeStatus() throws Exception {
        // Initialize the database
        weeklyChallengeStatusRepository.save(weeklyChallengeStatus);

        // Get the weeklyChallengeStatus
        restWeeklyChallengeStatusMockMvc.perform(get("/api/weekly-challenge-statuses/{id}", weeklyChallengeStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weeklyChallengeStatus.getId()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }

    @Test
    public void getNonExistingWeeklyChallengeStatus() throws Exception {
        // Get the weeklyChallengeStatus
        restWeeklyChallengeStatusMockMvc.perform(get("/api/weekly-challenge-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWeeklyChallengeStatus() throws Exception {
        // Initialize the database
        weeklyChallengeStatusService.save(weeklyChallengeStatus);

        int databaseSizeBeforeUpdate = weeklyChallengeStatusRepository.findAll().size();

        // Update the weeklyChallengeStatus
        WeeklyChallengeStatus updatedWeeklyChallengeStatus = weeklyChallengeStatusRepository.findById(weeklyChallengeStatus.getId()).get();
        updatedWeeklyChallengeStatus
            .count(UPDATED_COUNT);

        restWeeklyChallengeStatusMockMvc.perform(put("/api/weekly-challenge-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeeklyChallengeStatus)))
            .andExpect(status().isOk());

        // Validate the WeeklyChallengeStatus in the database
        List<WeeklyChallengeStatus> weeklyChallengeStatusList = weeklyChallengeStatusRepository.findAll();
        assertThat(weeklyChallengeStatusList).hasSize(databaseSizeBeforeUpdate);
        WeeklyChallengeStatus testWeeklyChallengeStatus = weeklyChallengeStatusList.get(weeklyChallengeStatusList.size() - 1);
        assertThat(testWeeklyChallengeStatus.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    public void updateNonExistingWeeklyChallengeStatus() throws Exception {
        int databaseSizeBeforeUpdate = weeklyChallengeStatusRepository.findAll().size();

        // Create the WeeklyChallengeStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeeklyChallengeStatusMockMvc.perform(put("/api/weekly-challenge-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyChallengeStatus)))
            .andExpect(status().isBadRequest());

        // Validate the WeeklyChallengeStatus in the database
        List<WeeklyChallengeStatus> weeklyChallengeStatusList = weeklyChallengeStatusRepository.findAll();
        assertThat(weeklyChallengeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteWeeklyChallengeStatus() throws Exception {
        // Initialize the database
        weeklyChallengeStatusService.save(weeklyChallengeStatus);

        int databaseSizeBeforeDelete = weeklyChallengeStatusRepository.findAll().size();

        // Delete the weeklyChallengeStatus
        restWeeklyChallengeStatusMockMvc.perform(delete("/api/weekly-challenge-statuses/{id}", weeklyChallengeStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<WeeklyChallengeStatus> weeklyChallengeStatusList = weeklyChallengeStatusRepository.findAll();
        assertThat(weeklyChallengeStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeeklyChallengeStatus.class);
        WeeklyChallengeStatus weeklyChallengeStatus1 = new WeeklyChallengeStatus();
        weeklyChallengeStatus1.setId("id1");
        WeeklyChallengeStatus weeklyChallengeStatus2 = new WeeklyChallengeStatus();
        weeklyChallengeStatus2.setId(weeklyChallengeStatus1.getId());
        assertThat(weeklyChallengeStatus1).isEqualTo(weeklyChallengeStatus2);
        weeklyChallengeStatus2.setId("id2");
        assertThat(weeklyChallengeStatus1).isNotEqualTo(weeklyChallengeStatus2);
        weeklyChallengeStatus1.setId(null);
        assertThat(weeklyChallengeStatus1).isNotEqualTo(weeklyChallengeStatus2);
    }
}
