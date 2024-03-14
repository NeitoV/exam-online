package com.exam.data.repository;

import com.exam.data.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Boolean existsByName(String name);

}
