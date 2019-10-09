package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.Challenge;
import com.db.hackathon.repository.ChallengeRepository;
import com.db.hackathon.service.ChallengeService;
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
 * Integration tests for the {@Link ChallengeResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class ChallengeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREDIT_AMOUNT = 1L;
    private static final Long UPDATED_CREDIT_AMOUNT = 2L;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restChallengeMockMvc;

    private Challenge challenge;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChallengeResource challengeResource = new ChallengeResource(challengeService);
        this.restChallengeMockMvc = MockMvcBuilders.standaloneSetup(challengeResource)
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
    public static Challenge createEntity() {
        Challenge challenge = new Challenge()
            .title(DEFAULT_TITLE)
            .creditAmount(DEFAULT_CREDIT_AMOUNT);
        return challenge;
    }

    @BeforeEach
    public void initTest() {
        challengeRepository.deleteAll();
        challenge = createEntity();
    }

    @Test
    public void createChallenge() throws Exception {
        int databaseSizeBeforeCreate = challengeRepository.findAll().size();

        // Create the Challenge
        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isCreated());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate + 1);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testChallenge.getCreditAmount()).isEqualTo(DEFAULT_CREDIT_AMOUNT);
    }

    @Test
    public void createChallengeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = challengeRepository.findAll().size();

        // Create the Challenge with an existing ID
        challenge.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restChallengeMockMvc.perform(post("/api/challenges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllChallenges() throws Exception {
        // Initialize the database
        challengeRepository.save(challenge);

        // Get all the challengeList
        restChallengeMockMvc.perform(get("/api/challenges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(challenge.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT.intValue())));
    }
    
    @Test
    public void getChallenge() throws Exception {
        // Initialize the database
        challengeRepository.save(challenge);

        // Get the challenge
        restChallengeMockMvc.perform(get("/api/challenges/{id}", challenge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(challenge.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.creditAmount").value(DEFAULT_CREDIT_AMOUNT.intValue()));
    }

    @Test
    public void getNonExistingChallenge() throws Exception {
        // Get the challenge
        restChallengeMockMvc.perform(get("/api/challenges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChallenge() throws Exception {
        // Initialize the database
        challengeService.save(challenge);

        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Update the challenge
        Challenge updatedChallenge = challengeRepository.findById(challenge.getId()).get();
        updatedChallenge
            .title(UPDATED_TITLE)
            .creditAmount(UPDATED_CREDIT_AMOUNT);

        restChallengeMockMvc.perform(put("/api/challenges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChallenge)))
            .andExpect(status().isOk());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testChallenge.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
    }

    @Test
    public void updateNonExistingChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Create the Challenge

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMockMvc.perform(put("/api/challenges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteChallenge() throws Exception {
        // Initialize the database
        challengeService.save(challenge);

        int databaseSizeBeforeDelete = challengeRepository.findAll().size();

        // Delete the challenge
        restChallengeMockMvc.perform(delete("/api/challenges/{id}", challenge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Challenge.class);
        Challenge challenge1 = new Challenge();
        challenge1.setId("id1");
        Challenge challenge2 = new Challenge();
        challenge2.setId(challenge1.getId());
        assertThat(challenge1).isEqualTo(challenge2);
        challenge2.setId("id2");
        assertThat(challenge1).isNotEqualTo(challenge2);
        challenge1.setId(null);
        assertThat(challenge1).isNotEqualTo(challenge2);
    }
}
