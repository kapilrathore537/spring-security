package com.gramseva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gramseva.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);
    public Optional<User> findByUserIdAndIsActiveAndIsDeleted(String userId,boolean isActive,boolean isDeleted);
}
