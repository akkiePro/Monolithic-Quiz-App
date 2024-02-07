package com.akki.quiz.controller;

import com.akki.quiz.model.Question;
import com.akki.quiz.model.QuestionWrapper;
import com.akki.quiz.model.Response;
import com.akki.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService service;

    @PostMapping("generate")
    public ResponseEntity<String> generateQuizByCategory(@RequestParam String category, @RequestParam("numQ") Integer limit, @RequestParam String title) {
        return service.createQuizByCategory(category, limit, title);
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("quizId") Integer id) {
        return service.fetchQuizQuestions(id);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("quizId") Integer id, @RequestBody List<Response> responses) {
        return service.getResult(id, responses);
    }

}
