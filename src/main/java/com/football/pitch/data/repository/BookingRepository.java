package com.football.pitch.data.repository;

import com.football.pitch.data.enity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByPitchDetailsIdAndDateAndStatusId(Long pitchDetailsId, Date date, Long statusId);

    @Query("SELECT b FROM Booking b WHERE b.date < :inputDate AND b.status.id = :statusId")
    List<Booking> findAllByDateBeforeAndStatusId(@Param("inputDate") Date inputDate, @Param("statusId") Long statusId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
            "WHERE b.customer.user.id = :userId AND b.status.id = :statusId")
    boolean existsByUserIdAndStatusId(@Param("userId") Long userId, @Param("statusId") Long statusId);

    @Query("SELECT b FROM Booking b " +
            "WHERE (:date IS NULL OR DATE(b.date) = DATE(:date)) " +
            "AND (:typeId IS NULL OR b.pitchDetails.price.type.id = :typeId) " +
            "AND (:durationId IS NULL OR b.pitchDetails.duration.id = :durationId) " +
            "AND (:statusId IS NULL OR b.status.id = :statusId) " +
            "AND (:managerId IS NULL OR b.pitchDetails.pitch.manager.id = :managerId)")
    Page<Booking> filterBooking(@Param("date") Date date, @Param("typeId") Long typeId,
                                @Param("durationId") Long durationId, @Param("managerId") Long managerId,
                                @Param("statusId") Long statusId, Pageable pageable);


    List<Booking> findAllByCustomerId(long customerId);

}
