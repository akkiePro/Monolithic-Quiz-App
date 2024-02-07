package com.akki.quiz.dao;

import com.akki.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT *FROM Question q WHERE q.category= :category LIMIT :limit", nativeQuery = true)
    List<Question> getQuestionsForQuizByCategory(String category, Integer limit);
}
