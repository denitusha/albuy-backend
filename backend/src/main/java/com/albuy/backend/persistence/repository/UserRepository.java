package com.albuy.backend.persistence.repository;


import com.albuy.backend.persistence.entity.Role;
import com.albuy.backend.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    User findByRole(Role role);




}
