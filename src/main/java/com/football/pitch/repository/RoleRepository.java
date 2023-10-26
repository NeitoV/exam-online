package com.football.pitch.repository;

import com.football.pitch.data.enity.Role;
import com.football.pitch.data.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
