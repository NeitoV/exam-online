package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByName(String name);

    boolean existsByName(String name);
}
