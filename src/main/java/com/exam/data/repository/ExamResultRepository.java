package com.exam.data.repository;

import com.exam.data.enity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    boolean existsByExamIdAndStudentId(Long examId, Long studentId);

    boolean existsByExamId(long examId);

}
