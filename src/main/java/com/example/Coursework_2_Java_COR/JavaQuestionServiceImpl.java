package org.skypro.Coursework2.Java.COR.service;

import org.skypro.Coursework2.Java.COR.exception.NoQuestionsAvailableException;
import org.skypro.Coursework2.Java.COR.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class JavaQuestionServiceImpl implements QuestionService {

    private final List<Question> questions = new ArrayList<>();

    @Override
    public Question addQuestion(String questionText, String answerText) {
        Question question = new Question(questionText, answerText);
        if (!questions.contains(question)) {
            questions.add(question);
        }
        return question;
    }

    @Override
    public Question removeQuestion(String questionText, String answerText) {
        questions.removeIf(q -> q.getQuestion().equals(questionText) && q.getAnswer().equals(answerText));
        return null;
    }

    @Override
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw null;
        }
        int randomIndex = new Random().nextInt(questions.size());
        return questions.get(randomIndex);

    }
}