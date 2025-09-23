package org.test.testspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;
    private boolean desactive;
    private boolean expire;
    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private AppUser user;
}
