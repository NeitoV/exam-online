package com.football.pitch.data.repository;

import com.football.pitch.data.enity.PitchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitchDetailsRepository extends JpaRepository<PitchDetails, Long> {
    List<PitchDetails> findAllByPitch_Manager_IdAndPitch_Type_IdAndDuration_Id(
            Long managerId, Long typeId, Long durationId);
}
