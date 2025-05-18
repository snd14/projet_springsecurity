package org.test.testspring.controleur;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.testspring.entity.Avis;

@Slf4j
@RestController
@RequestMapping("/avis")
public class Aviscontroleur {
    public void avis(Avis avis) {
       // log.info("siny est mon nom");
        log.info("siny");

    }
}
