package com.exam.data.repository;

import com.exam.data.enity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResultRepository extends JpaRepository<ExamResult, Long> {
    
    @Query("SELECT e.code, l.name, er.point " +
            "FROM ExamResult er " +
            "JOIN er.exam e " +
            "JOIN e.lecturer l " +
            "WHERE er.student.id = :studentId")
    List<Object[]> getStudentResult(Long studentId);
}
