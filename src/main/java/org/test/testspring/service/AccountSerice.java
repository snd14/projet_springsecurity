package org.test.testspring.service;

import org.test.testspring.entity.AppRole;
import org.test.testspring.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AccountSerice {
    AppRole saveRole(AppRole appRole);
    Optional<AppRole> getRole(Long id);
    List<AppRole> listRole();

    AppUser saveUser(AppUser appUser);
    Optional<AppUser> getUser(Long id);
    List<AppUser> listUser();

    void addRoleToUser (String username,String rolename);

    AppUser loadUserByUsername(String username);
}
