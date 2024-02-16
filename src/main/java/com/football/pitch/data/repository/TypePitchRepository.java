package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypePitchRepository extends JpaRepository<Type, Long> {
    boolean existsByName(String name);

    List<Type> findAllByOrderByIdDesc();
}
