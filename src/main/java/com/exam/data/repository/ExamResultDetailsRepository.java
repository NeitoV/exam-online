package com.exam.data.repository;

import com.exam.data.enity.ExamResultDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultDetailsRepository extends JpaRepository<ExamResultDetails, Long> {
}
