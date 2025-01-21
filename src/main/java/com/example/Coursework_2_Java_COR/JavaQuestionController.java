package org.skypro.Coursework2.Java.COR.controller;

import org.skypro.Coursework2.Java.COR.model.Question;
import org.skypro.Coursework2.Java.COR.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final QuestionService questionService;

    public JavaQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping ("/add")
    public Question addQuestion(@RequestParam String question,
                                @RequestParam String answer) {
        return questionService.addQuestion(question, answer);
    }

    @GetMapping("/remove")
    public Question removeQuestion(@RequestParam String question,
                                   @RequestParam String answer) {
        return questionService.removeQuestion(question, answer);
    }

    @GetMapping("/random")
    public Question getRandomQuestion() {
        return questionService.getRandomQuestion();
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}