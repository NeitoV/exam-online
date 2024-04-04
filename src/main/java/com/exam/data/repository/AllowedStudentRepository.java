package com.exam.data.repository;

import com.exam.data.enity.AllowedStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllowedStudentRepository extends JpaRepository<AllowedStudent, Long> {
    boolean existsByExamIdAndStudentId(Long examId, Long studentId);

    List<AllowedStudent> findAllByStudentId(Long studentId);
}
