package com.example.Coursework_2_Java_COR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skypro.Coursework2.Java.COR.model.Question;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExaminerServiceImplTest {

    private QuestionService questionService;
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    void setUp() {
        questionService = Mockito.mock(QuestionService.class);
        examinerService = new ExaminerServiceImpl(questionService);
    }

    @Test
    void testGetQuestions_WithValidAmount() {
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        Question question2 = new Question("Что такое OOP?", "Объектно-ориентированное программирование");
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2));
        when(questionService.getRandomQuestion()).thenReturn(question1, question2);

        List<Question> questions = examinerService.getQuestions(2);

        assertEquals(2, questions.size());
        assertTrue(new HashSet<>(questions).containsAll(Arrays.asList(question1, question2)));
    }

    @Test
    void testGetQuestions_WithMoreThanAvailable() {
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(2);
        });

        assertEquals("Запрошено больше вопросов, чем доступно.", exception.getMessage());
    }

    @Test
    void testGetQuestions_WithUniqueRandomQuestions() {
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        Question question2 = new Question("Что такое Python?", "Язык программирования");
        Question question3 = new Question("Что такое C++?", "Язык программирования");
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2, question3));
        when(questionService.getRandomQuestion()).thenReturn(question1, question2, question3, question1, question2);

        List<Question> questions = examinerService.getQuestions(2);

        assertEquals(2, questions.size());
        assertTrue(new HashSet<>(questions).containsAll(Arrays.asList(question1, question2))
                || new HashSet<>(questions).containsAll(Arrays.asList(question1, question3))
                || new HashSet<>(questions).containsAll(Arrays.asList(question2, question3)));
    }
}