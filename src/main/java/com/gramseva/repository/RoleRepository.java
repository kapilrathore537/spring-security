package com.gramseva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gramseva.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
  public Optional<Role> findByRoleName(String roleName);
}
