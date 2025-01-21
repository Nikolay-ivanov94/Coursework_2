package org.skypro.Coursework2.Java.COR.service;

import org.skypro.Coursework2.Java.COR.exception.QuestionNotFoundException;
import org.skypro.Coursework2.Java.COR.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public List<Question> getQuestions(Integer amount) {
        List<Question> allQuestions = questionService.getAllQuestions();
        if (amount > allQuestions.size()) {
            throw new IllegalArgumentException("Запрошено больше вопросов, чем доступно.");
        }

        Set<Question> uniqueQuestions = new HashSet<>();
        while (uniqueQuestions.size() < amount) {
            uniqueQuestions.add(questionService.getRandomQuestion());
        }
        return new ArrayList<>(uniqueQuestions);
    }
}