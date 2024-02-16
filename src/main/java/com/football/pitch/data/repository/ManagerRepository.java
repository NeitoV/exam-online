package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findAllByLocationId(long locationId);

    Optional<Manager> findByUserId(long userId);

}
