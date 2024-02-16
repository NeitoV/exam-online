package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Duration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface DurationRepository extends JpaRepository<Duration, Long> {
    boolean existsByStartTime(LocalTime startTime);
}
