package com.football.pitch.data.repository;

import com.football.pitch.data.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN Manager m on u.id = m.user.id " +
            "LEFT JOIN Customer c on u.id = c.user.id " +
            "WHERE " +
            "(:keyword = '' OR m.fullName LIKE %:keyword% OR c.fullName LIKE %:keyword% " +
            "OR m.phoneNumber LIKE %:keyword% OR c.phoneNumber LIKE %:keyword% " +
            "OR u.email LIKE %:keyword%) " +
            "AND (u.role.id = :roleId OR :roleId = 0) " +
            "AND u.role.id != 1 " +
            "AND u.deleted = false " +
            "ORDER BY u.id DESC")
    Page<User> filterUser(@Param("keyword") String keyword, @Param("roleId") Long roleId, Pageable pageable);
}
