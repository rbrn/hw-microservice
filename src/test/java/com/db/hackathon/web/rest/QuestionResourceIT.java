package com.db.hackathon.web.rest;

import com.db.hackathon.HelloworldApp;
import com.db.hackathon.config.SecurityBeanOverrideConfiguration;
import com.db.hackathon.domain.Question;
import com.db.hackathon.repository.QuestionRepository;
import com.db.hackathon.service.QuestionService;
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

import com.db.hackathon.domain.enumeration.Category;
/**
 * Integration tests for the {@Link QuestionResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, HelloworldApp.class})
public class QuestionResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Category DEFAULT_CATEGORY = Category.FOOD;
    private static final Category UPDATED_CATEGORY = Category.TRAVEL;

    private static final Boolean DEFAULT_IS_FOR_QUIZZ = false;
    private static final Boolean UPDATED_IS_FOR_QUIZZ = true;

    private static final String DEFAULT_ANSWERS = "AAAAAAAAAA";
    private static final String UPDATED_ANSWERS = "BBBBBBBBBB";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restQuestionMockMvc;

    private Question question;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionResource questionResource = new QuestionResource(questionService);
        this.restQuestionMockMvc = MockMvcBuilders.standaloneSetup(questionResource)
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
    public static Question createEntity() {
        Question question = new Question()
            .text(DEFAULT_TEXT)
            .category(DEFAULT_CATEGORY)
            .isForQuizz(DEFAULT_IS_FOR_QUIZZ);
        return question;
    }

    @BeforeEach
    public void initTest() {
        questionRepository.deleteAll();
        question = createEntity();
    }

    @Test
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQuestion.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testQuestion.isIsForQuizz()).isEqualTo(DEFAULT_IS_FOR_QUIZZ);
        assertThat(testQuestion.getAnswers()).isEqualTo(DEFAULT_ANSWERS);
    }

    @Test
    public void createQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question with an existing ID
        question.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        // Get all the questionList
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].isForQuizz").value(hasItem(DEFAULT_IS_FOR_QUIZZ.booleanValue())))
            .andExpect(jsonPath("$.[*].answers").value(hasItem(DEFAULT_ANSWERS.toString())));
    }
    
    @Test
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.isForQuizz").value(DEFAULT_IS_FOR_QUIZZ.booleanValue()))
            .andExpect(jsonPath("$.answers").value(DEFAULT_ANSWERS.toString()));
    }

    @Test
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionService.save(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        updatedQuestion
            .text(UPDATED_TEXT)
            .category(UPDATED_CATEGORY)
            .isForQuizz(UPDATED_IS_FOR_QUIZZ)
            ;

        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestion)))
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQuestion.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testQuestion.isIsForQuizz()).isEqualTo(UPDATED_IS_FOR_QUIZZ);
        assertThat(testQuestion.getAnswers()).isEqualTo(UPDATED_ANSWERS);
    }

    @Test
    public void updateNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Create the Question

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionService.save(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = new Question();
        question1.setId("id1");
        Question question2 = new Question();
        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);
        question2.setId("id2");
        assertThat(question1).isNotEqualTo(question2);
        question1.setId(null);
        assertThat(question1).isNotEqualTo(question2);
    }
}
