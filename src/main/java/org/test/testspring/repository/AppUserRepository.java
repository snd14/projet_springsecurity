package org.test.testspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.testspring.entity.AppRole;
import org.test.testspring.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository <AppUser,Long> {
    AppUser findByUsername(String username);
    Optional<AppUser>  findById(Long id);
    //Optional<AppUser>  findByemail(String email);


   Optional<AppUser> findByEmail(String email);
}
