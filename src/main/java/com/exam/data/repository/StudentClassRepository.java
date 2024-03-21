package com.exam.data.repository;

import com.exam.data.enity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    Optional<StudentClass> findByName(String name);

    Boolean existsByName(String name);

    @Query("select count(*) from StudentClass  where code =:codeInput")
    int countByCode(String codeInput);
}
