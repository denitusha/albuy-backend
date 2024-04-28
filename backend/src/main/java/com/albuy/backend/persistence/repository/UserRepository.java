package com.albuy.backend.persistence.repository;


import com.albuy.backend.persistence.entity.Role;
import com.albuy.backend.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    User findByRole(Role role);




}
