package org.test.testspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.test.testspring.entity.Validation;

import java.time.Instant;
import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation ,Integer> {
    Optional<Validation> findByCode(String code);
    void deleteAllByExpirationBefore(Instant now);
}
