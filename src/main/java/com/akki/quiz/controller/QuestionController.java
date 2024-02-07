package com.akki.quiz.controller;

import com.akki.quiz.model.Question;
import com.akki.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("question")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.fetchAllQuestions();
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<List<Question>> getAllQuestionsByCategory(@PathVariable("cat") String category) {
        return questionService.fetchAllQuestionsByCategory(category);
    }

    @PutMapping("update/{qId}")
    public ResponseEntity<String> updateQuestion(@PathVariable("qId") Integer id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("delete/{qId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("qId") Integer id) {
        return questionService.deleteQuestion(id);
    }

}
