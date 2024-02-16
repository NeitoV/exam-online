package com.football.pitch.service;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.manager.ManagerCreationDTO;
import com.football.pitch.data.dto.manager.ManagerDTO;
import com.football.pitch.data.enity.Manager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ManagerService {
    @Transactional
    MessageResponse createManager(ManagerCreationDTO managerCreationDTO);

    @Transactional
    MessageResponse updateManager(long id, ManagerDTO managerDTO);

    ManagerDTO findById(long id);

    Manager getManagerFromToken();

    ManagerDTO findByUserId(long id);

    List<ManagerDTO> findByLocation(long locationId);
}
