package com.example.security.user.enduser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    boolean existsByEmail(String email);

    EndUser findByEmail(String email);

    List<EndUser> findAll();
}
