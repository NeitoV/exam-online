package com.exam.data.repository;

import com.exam.data.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select u from User u " +
            "left join Student s on u.id = s.user.id " +
            "left join Lecturer l on u.id = l.user.id " +
            "where " +
            "(:keyword = '' " +
            "OR u.email LIKE %:keyword%) " +
            "AND  ( u.role.id = :roleId or :roleId = 0 ) " +
            "AND u.role.id != 1 " +
            "AND u.deleted = false " +
            "ORDER BY u.id DESC")
    Page<User> filterUser(@Param("keyword") String keyword, @Param("roleId") long roleId, Pageable pageable);

}
