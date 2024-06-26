package com.exam.data.repository;

import com.exam.data.enity.ExamResultDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamResultDetailsRepository extends JpaRepository<ExamResultDetails, Long> {
    List<ExamResultDetails> findAllByExamResultId(long ExamResultId);
}
