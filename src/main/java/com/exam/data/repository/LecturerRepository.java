package com.exam.data.repository;



import com.exam.data.enity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Lecturer findByUserId(Long userId);

    Optional<Lecturer> findByName(String name);

    Boolean existsByName(String name);

}
