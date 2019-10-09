package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.AnswerChoice;
import com.db.hackathon.repository.AnswerChoiceRepository;
import com.db.hackathon.service.AnswerChoiceService;
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
 * Integration tests for the {@Link AnswerChoiceResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class AnswerChoiceResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private AnswerChoiceRepository answerChoiceRepository;

    @Autowired
    private AnswerChoiceService answerChoiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAnswerChoiceMockMvc;

    private AnswerChoice answerChoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerChoiceResource answerChoiceResource = new AnswerChoiceResource(answerChoiceService);
        this.restAnswerChoiceMockMvc = MockMvcBuilders.standaloneSetup(answerChoiceResource)
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
    public static AnswerChoice createEntity() {
        AnswerChoice answerChoice = new AnswerChoice()
            .label(DEFAULT_LABEL);
        return answerChoice;
    }

    @BeforeEach
    public void initTest() {
        answerChoiceRepository.deleteAll();
        answerChoice = createEntity();
    }

    @Test
    public void createAnswerChoice() throws Exception {
        int databaseSizeBeforeCreate = answerChoiceRepository.findAll().size();

        // Create the AnswerChoice
        restAnswerChoiceMockMvc.perform(post("/api/answer-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerChoice)))
            .andExpect(status().isCreated());

        // Validate the AnswerChoice in the database
        List<AnswerChoice> answerChoiceList = answerChoiceRepository.findAll();
        assertThat(answerChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerChoice testAnswerChoice = answerChoiceList.get(answerChoiceList.size() - 1);
        assertThat(testAnswerChoice.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    public void createAnswerChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerChoiceRepository.findAll().size();

        // Create the AnswerChoice with an existing ID
        answerChoice.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerChoiceMockMvc.perform(post("/api/answer-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerChoice)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerChoice in the database
        List<AnswerChoice> answerChoiceList = answerChoiceRepository.findAll();
        assertThat(answerChoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllAnswerChoices() throws Exception {
        // Initialize the database
        answerChoiceRepository.save(answerChoice);

        // Get all the answerChoiceList
        restAnswerChoiceMockMvc.perform(get("/api/answer-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerChoice.getId())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    public void getAnswerChoice() throws Exception {
        // Initialize the database
        answerChoiceRepository.save(answerChoice);

        // Get the answerChoice
        restAnswerChoiceMockMvc.perform(get("/api/answer-choices/{id}", answerChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(answerChoice.getId()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    public void getNonExistingAnswerChoice() throws Exception {
        // Get the answerChoice
        restAnswerChoiceMockMvc.perform(get("/api/answer-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnswerChoice() throws Exception {
        // Initialize the database
        answerChoiceService.save(answerChoice);

        int databaseSizeBeforeUpdate = answerChoiceRepository.findAll().size();

        // Update the answerChoice
        AnswerChoice updatedAnswerChoice = answerChoiceRepository.findById(answerChoice.getId()).get();
        updatedAnswerChoice
            .label(UPDATED_LABEL);

        restAnswerChoiceMockMvc.perform(put("/api/answer-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnswerChoice)))
            .andExpect(status().isOk());

        // Validate the AnswerChoice in the database
        List<AnswerChoice> answerChoiceList = answerChoiceRepository.findAll();
        assertThat(answerChoiceList).hasSize(databaseSizeBeforeUpdate);
        AnswerChoice testAnswerChoice = answerChoiceList.get(answerChoiceList.size() - 1);
        assertThat(testAnswerChoice.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    public void updateNonExistingAnswerChoice() throws Exception {
        int databaseSizeBeforeUpdate = answerChoiceRepository.findAll().size();

        // Create the AnswerChoice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerChoiceMockMvc.perform(put("/api/answer-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerChoice)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerChoice in the database
        List<AnswerChoice> answerChoiceList = answerChoiceRepository.findAll();
        assertThat(answerChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAnswerChoice() throws Exception {
        // Initialize the database
        answerChoiceService.save(answerChoice);

        int databaseSizeBeforeDelete = answerChoiceRepository.findAll().size();

        // Delete the answerChoice
        restAnswerChoiceMockMvc.perform(delete("/api/answer-choices/{id}", answerChoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AnswerChoice> answerChoiceList = answerChoiceRepository.findAll();
        assertThat(answerChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerChoice.class);
        AnswerChoice answerChoice1 = new AnswerChoice();
        answerChoice1.setId("id1");
        AnswerChoice answerChoice2 = new AnswerChoice();
        answerChoice2.setId(answerChoice1.getId());
        assertThat(answerChoice1).isEqualTo(answerChoice2);
        answerChoice2.setId("id2");
        assertThat(answerChoice1).isNotEqualTo(answerChoice2);
        answerChoice1.setId(null);
        assertThat(answerChoice1).isNotEqualTo(answerChoice2);
    }
}
