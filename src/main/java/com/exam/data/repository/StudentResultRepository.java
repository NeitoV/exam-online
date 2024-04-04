package com.exam.data.repository;

import com.exam.data.enity.ExamResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResultRepository extends JpaRepository<ExamResult, Long> {

    @Query("SELECT er " +
            "FROM ExamResult er " +
            "WHERE er.student.id = :studentId")
    Page<ExamResult> getStudentResult(Long studentId, Pageable pageable);
}
