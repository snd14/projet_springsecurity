package org.test.testspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.test.testspring.entity.Validation;

@Service
public class NotificationService {
    @Autowired
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("diawsiny77@gmail.com");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Votre code d'activation");

        String texte = String.format(
                "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
                validation.getUtilisateur().getUsername(),
                validation.getCode()
        );
        message.setText(texte);

        javaMailSender.send(message);
    }
}
