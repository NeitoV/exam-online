package com.exam.data.repository;

import com.exam.data.enity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByExamId(Long examId);
}
