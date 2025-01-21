package org.skypro.Coursework2.Java.COR.service;

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

    private QuestionService questionService;  // Мок-объект сервиса вопросов
    private ExaminerServiceImpl examinerService; // Тестируемый класс

    @BeforeEach
    void setUp() {
        // Инициализируем мок-объект QuestionService
        questionService = Mockito.mock(QuestionService.class);
        examinerService = new ExaminerServiceImpl(questionService);
    }

    @Test
    void testGetQuestions_WithValidAmount() {
        // Создание списка вопросов для возврата
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        Question question2 = new Question("Что такое OOP?", "Объектно-ориентированное программирование");

        // Настраиваем мок для возврата всех вопросов
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2));

        // Настраиваем мок для возврата случайного вопроса
        when(questionService.getRandomQuestion()).thenReturn(question1, question2);

        // Проверяем, что получаем 2 уникальных вопроса
        List<Question> questions = examinerService.getQuestions(2);

        assertEquals(2, questions.size());
        assertTrue(new HashSet<>(questions).containsAll(Arrays.asList(question1, question2)));
    }

    @Test
    void testGetQuestions_WithMoreThanAvailable() {
        // Настраиваем мок для возврата меньшего числа вопросов
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1));

        // Ожидаем исключение, если запрашиваем больше вопросов, чем доступно
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(2);
        });
        assertEquals("Запрошено больше вопросов, чем доступно.", exception.getMessage());
    }

    @Test
    void testGetQuestions_WithUniqueRandomQuestions() {
        // Создание вопросов
        Question question1 = new Question("Что такое Java?", "Язык программирования");
        Question question2 = new Question("Что такое Python?", "Язык программирования");
        Question question3 = new Question("Что такое C++?", "Язык программирования");

        // Настраиваем мок для возврата вопросов
        when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2, question3));
        when(questionService.getRandomQuestion()).thenReturn(question1, question2, question3, question1, question2); // Имитация возможных повторов

        // Запрашиваем 2 уникальных вопроса
        List<Question> questions = examinerService.getQuestions(2);

        assertEquals(2, questions.size());
        assertTrue(new HashSet<>(questions).containsAll(Arrays.asList(question1, question2))
                || new HashSet<>(questions).containsAll(Arrays.asList(question1, question3))
                || new HashSet<>(questions).containsAll(Arrays.asList(question2, question3)));
    }
}