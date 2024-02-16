package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    boolean existsByName(String name);

    List<Period> findAllByOrderByIdDesc();

    Optional<Period> findByName(String name);
}
