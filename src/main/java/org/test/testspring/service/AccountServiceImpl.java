package org.test.testspring.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.test.testspring.entity.AppRole;
import org.test.testspring.entity.AppUser;
import org.test.testspring.entity.Validation;
import org.test.testspring.repository.AppRoleRepository;
import org.test.testspring.repository.AppUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements UserDetailsService {
@Autowired
   private AppRoleRepository appRoleRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidationService validationService;

    // @Override
   public AppRole saveRole(AppRole appRole){
        return appRoleRepository.save(appRole);
    };
   // @Override
   public Optional<AppRole> getRole(Long id){
        return appRoleRepository.findById(id);

    };
   // @Override
    public List<AppRole> listRole(){
        return appRoleRepository.findAll();

    };
    //@Override
    public void saveUser(AppUser appUser){
        if(!appUser.getEmail().contains("@")){
            throw new RuntimeException("votre email n'est pas valide");

        }
        Optional<AppUser> email =appUserRepository.findByemail(appUser.getEmail());
        if(email.isPresent()){
            throw new RuntimeException("votre email existe deja");
        }

        String pw=appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        appUser=this.appUserRepository.save(appUser);
                validationService.enregistrer(appUser);

    }
    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())){
            throw  new RuntimeException("Votre code a expiré");
        }
        AppUser utilisateurActiver = this.appUserRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.appUserRepository.save(utilisateurActiver);
    }
   // @Override
    public Optional<AppUser> getUser(Long id){
        return appUserRepository.findById(id);

    };
   // @Override
    public List<AppUser> listUser(){
        return appUserRepository.findAll();

    };
   // @Override
    public void addRoleToUser (String username,String rolename){
       AppUser appUser= appUserRepository.findByUsername(username);
       AppRole appRole= appRoleRepository.findByRolename(rolename);
       appUser.getAppRole().add(appRole);
    };
    @Override
    public AppUser loadUserByUsername(String username){
        return appUserRepository.findByUsername(username);

    }
}
