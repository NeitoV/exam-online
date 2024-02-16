package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    boolean existsByPeriodIdAndTypeId(long periodId, long typeId);

    Optional<Price> findByPeriodIdAndTypeId(long periodId, long typeId);

    @Query("SELECT p FROM Price p WHERE (:typeId IS NULL OR p.type.id = :typeId) " +
            "AND (:periodId IS NULL OR p.period.id = :periodId)")
    Page<Price> filterByTypeAndPeriod(Pageable pageable, Long typeId, Long periodId);

}
