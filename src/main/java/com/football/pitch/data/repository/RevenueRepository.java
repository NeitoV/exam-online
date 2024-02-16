package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Manager;
import com.football.pitch.data.enity.Revenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    Optional<Revenue> findByDateAndManagerIdAndTypeId(Date date, long managerId, long typeId);

    @Query("SELECT t.id AS type_id, COALESCE(SUM(r.dailyRevenue), 0) AS total_revenue, r.manager.location.id AS location_id " +
            "FROM Type t " +
            "LEFT JOIN Revenue r ON t.id = r.type.id AND DATE_FORMAT(r.date, '%Y-%m') = :monthOfYear " +
            "WHERE (:day is null OR DAY(r.date) = :day) " +
            "GROUP BY t.id, r.manager.location.id " +
            "ORDER BY t.id")
    List<Object[]> revenueStatistics(@Param("monthOfYear") String monthOfYear, @Param("day") Integer day);

    @Query("SELECT t.id AS type_id, COALESCE(SUM(r.dailyRevenue), 0) AS total_revenue " +
            "FROM Type t " +
            "LEFT JOIN Revenue r ON t.id = r.type.id AND DATE_FORMAT(r.date, '%Y-%m') = :monthOfYear " +
            "WHERE (:day is null OR DAY(r.date) = :day) " +
            "AND r.manager.id = :managerId " +
            "GROUP BY t.id " +
            "ORDER BY t.id")
    List<Object[]> revenueStatisticsForManager(@Param("monthOfYear") String monthOfYear, @Param("day") Integer day,
                                               @Param("managerId") Long managerId);
}
