package org.test.testspring.repository;

import jakarta.persistence.criteria.From;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.test.testspring.entity.Jwt;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends JpaRepository<Jwt,Integer> {

    Optional<Jwt> findByValue(String token);
    @Query("FROM Jwt j WHERE j.expire =:expire AND j.desactive= :desactive AND j.user.username= :user")
    Optional<Jwt>findUtilisateurValideToken(String user,boolean desactive,boolean expire);

    @Query("FROM Jwt j WHERE j.user.username= :username")
    Stream<Jwt> findUtilisateur(String username);

    //@Query("FROM Jwt j WHERE j.expire =:expire AND j.desactive= :desactive")
    void deleteAllByExpireAndDesactive(boolean expire,boolean desactive);

}
