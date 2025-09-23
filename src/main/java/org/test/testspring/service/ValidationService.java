package org.test.testspring.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.test.testspring.entity.AppUser;
import org.test.testspring.entity.Validation;
import org.test.testspring.repository.AppUserRepository;
import org.test.testspring.repository.ValidationRepository;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@AllArgsConstructor

public class ValidationService {
    private ValidationRepository validationRepository;
    private NotificationService notificationService;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder ;

    public void nouveaumdp(Map<String, String> parametrage) {
        AppUser user=this.loadUserByUsername(parametrage.get("email"));
        final Validation validation =this.lireEnFonctionDuCode(parametrage.get("code"));
        if (validation.getUtilisateur().getEmail().equals(user.getEmail())){
            String mdp=passwordEncoder.encode(parametrage.get("password"));
            user.setPassword(mdp);
            this.appUserRepository.save(user);
        }

    }

    public void modifiermdp(Map<String, String> activation) {
        AppUser user=this.loadUserByUsername(activation.get("email"));
        this.enregistrer(user);

    }
    public AppUser loadUserByUsername(String email){
        return appUserRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("cet utilisateur n'existe pas"));

    }
    public void enregistrer(AppUser utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }
    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }

}
