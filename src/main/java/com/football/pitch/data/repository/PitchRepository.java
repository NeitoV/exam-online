package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Pitch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {

    @Query("SELECT p FROM Pitch p " +
            "WHERE (:typeId is null OR p.type.id = :typeId) " +
            "AND (:locationId is null OR p.manager.location.id = :locationId ) " +
            "AND (p.averageRating >= :rating)")
    Page<Pitch> filterPitch(@Param("typeId") Long typeId,
                            @Param("locationId") Long locationId,
                            @Param("rating") Float rating,
                            Pageable pageable);

    @Query("SELECT p FROM Pitch p " +
            "WHERE (:typeId is null OR p.type.id = :typeId) " +
            "AND (:locationId is null OR p.manager.location.id = :locationId ) " +
            "AND (p.averageRating >= :rating) " +
            "AND p.manager.id = :managerId or :managerId is null")
    Page<Pitch> filterPitchByManger(@Param("typeId") Long typeId,
                            @Param("locationId") Long locationId,
                            @Param("rating") Float rating,
                            @Param("managerId") Long managerId,
                            Pageable pageable);
}
