package com.exam.data.repository;

import com.exam.data.enity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    boolean existsByCode(String code);

    Optional<Exam> findByCode(String code);

    Page<Exam> findAllByLecturerId(long lecturerId, Pageable pageable);
}
