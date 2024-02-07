package com.akki.quiz.service;

import com.akki.quiz.dao.QuestionRepository;
import com.akki.quiz.dao.QuizRepository;
import com.akki.quiz.exception.NotFoundException;
import com.akki.quiz.model.Question;
import com.akki.quiz.model.QuestionWrapper;
import com.akki.quiz.model.Quiz;
import com.akki.quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public ResponseEntity<String> createQuizByCategory(String category, Integer limit, String title) {
        try {
            List<Question> questions = questionRepository.getQuestionsForQuizByCategory(category, limit);

            Quiz quiz = new Quiz();
            quiz.setQuizTitle(title);
            quiz.setQuestions(questions);

            quizRepository.save(quiz);

            return new ResponseEntity<>("quizTitle = " + title + " successfully saved.\n" + questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong...", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> fetchQuizQuestions(Integer id) {
        List<QuestionWrapper> quizQuestions = new ArrayList<>();
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new NotFoundException("quizId = " + id + "not found or missing records."));
            for (Question q : quiz.getQuestions()) {
                QuestionWrapper quizQuestion = new QuestionWrapper(q.getQId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                quizQuestions.add(quizQuestion);
            }
            return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(quizQuestions, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> getResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new NotFoundException("quizId = " + id + "not found or missing records."));
            List<Question> questions = quiz.getQuestions();
            int total = 0;
            for (int i = 0; i < questions.size(); i++) {
                if (responses.get(i).getResponse().equals(questions.get(i).getCorrectAnswer()))
                    total++;
            }
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
