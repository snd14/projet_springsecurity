package org.test.testspring.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.test.testspring.entity.AppUser;

@Service
@AllArgsConstructor
public class UserDetailImpl implements UserDetailsService {

  private AccountServiceImpl accountService;
@Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser=new AppUser();
    appUser=accountService.loadUserByUsername(username);
    if (appUser==null) throw new UsernameNotFoundException(String.format("utilisateur %s n'existe pas",username));
    String[] role=  appUser.getAppRole().stream().map(u->u.getRolename()).toArray(String[]::new);
    UserDetails userDetails= User
            .withUsername(appUser.getUsername())
            .password(appUser.getPassword())
            .roles(role).build();
    return userDetails;
    }
}
