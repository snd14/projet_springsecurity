package org.test.testspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.testspring.entity.AppRole;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository <AppRole,Long> {
    AppRole findByRolename(String rolename);
    Optional<AppRole> findById(Long id);
}
