package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.LocationDTO;
import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.manager.ManagerCreationDTO;
import com.football.pitch.data.dto.manager.ManagerDTO;
import com.football.pitch.data.enity.Location;
import com.football.pitch.data.enity.Manager;
import com.football.pitch.data.enity.User;
import com.football.pitch.data.mapper.LocationMapper;
import com.football.pitch.data.mapper.ManagerMapper;
import com.football.pitch.enumeration.ERole;
import com.football.pitch.data.repository.LocationRepository;
import com.football.pitch.data.repository.ManagerRepository;
import com.football.pitch.data.repository.UserRepository;
import com.football.pitch.exception.AccessDeniedException;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.ManagerService;
import com.football.pitch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public MessageResponse createManager(ManagerCreationDTO managerCreationDTO) {

        Manager manager = managerMapper.toEntity(managerCreationDTO.getManagerDTO());
        User user = userService.createUser(managerCreationDTO.getLoginDTO(), ERole.roleManager);
        Location location = mapLocation(managerCreationDTO.getManagerDTO().getLocationDTO());

        manager.setLocation(location);
        manager.setUser(user);
        managerRepository.save(manager);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

    @Override
    public MessageResponse updateManager(long id, ManagerDTO managerDTO) {
        Manager manager = managerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("manager id: ", id)));

        Manager managerUpdated = managerMapper.toEntity(managerDTO);
        Location location = mapLocation(managerDTO.getLocationDTO());

        managerUpdated.setLocation(location);
        managerUpdated.setTotalPitch(manager.getTotalPitch());
        managerUpdated.setId(manager.getId());
        managerUpdated.setUser(manager.getUser());

        managerRepository.save(managerUpdated);


        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public ManagerDTO findById(long id) {

        return managerRepository.findById(id).map(manager -> managerMapper.toDTO(manager)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("manager id: ", id))
        );
    }

    @Override
    public Manager getManagerFromToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("email: ", email))
        );

        Manager manager;
        if (!user.getRole().getName().equals("Role_Manager")) {
            manager = null;
        } else {
            manager = managerRepository.findByUserId(user.getId()).orElse(null);
        }

        return manager;
    }

    @Override
    public ManagerDTO findByUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("user id: ", id))
        );

        if (user.getRole().getId() == ERole.roleManager) {
            Manager manager = managerRepository.findByUserId(user.getId()).orElse(null);

            return managerMapper.toDTO(manager);
        } else {
            throw new AccessDeniedException(Collections.singletonMap("user is not manager", null));
        }
    }

    @Override
    public List<ManagerDTO> findByLocation(long locationId) {

        List<ManagerDTO> managers = managerRepository.findAllByLocationId(locationId).stream().map(
                manager -> managerMapper.toDTO(manager)).collect(Collectors.toList());

        return managers;
    }


    private Location mapLocation(LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findByName(locationDTO.getName()).orElse(null);

        Location location;

        if (existingLocation != null) {
            location = existingLocation;
        } else {
            location = locationRepository.save(locationMapper.toEntity(locationDTO));
        }

        return location;
    }

}
