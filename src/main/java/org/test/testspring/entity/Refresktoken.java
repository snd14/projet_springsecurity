package org.test.testspring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Refresktoken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String valeur;
    private Instant creation;
    private Instant expiration;
    private boolean expire;

}
