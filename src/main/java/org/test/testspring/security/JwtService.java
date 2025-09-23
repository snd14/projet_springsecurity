package org.test.testspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.testspring.entity.AppUser;
import org.test.testspring.entity.Jwt;
import org.test.testspring.repository.JwtRepository;
import org.test.testspring.service.AccountServiceImpl;


import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    private AccountServiceImpl utilisateurService;
    private JwtRepository jwtRepository;

    public Jwt findTokenByValue(String value){
        return jwtRepository.findByValue(value).orElseThrow(()->new RuntimeException("Token inconnue"));

    }
    
    public Map<String, String> generate(String username) {
        AppUser utilisateu = this.utilisateurService.loadUserByUsername(username);
        this.disabledToken(utilisateu);
       final Map<String, String> jwtMap = this.generateJwt(utilisateu);

        final Jwt jwt = Jwt
                            .builder()
                            .desactive(false)
                            .value(jwtMap.get(BEARER))
                            .expire(false)
                            .user(utilisateu)
                            .build();
        jwtRepository.save(jwt);
        return jwtMap;
    }
    public void disabledToken(AppUser user) {
        List<Jwt> jwtlist=jwtRepository.findUtilisateur(user.getUsername()).map(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                    return jwt;
                }
        ).collect(Collectors.toList());
        jwtRepository.saveAll(jwtlist);


    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(AppUser utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getUsername()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
    public void deconnexion(){
        AppUser user= (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Jwt jwt = jwtRepository.findUtilisateurValideToken(user.getUsername(), false,false )
                .orElseThrow(()->new RuntimeException("token inconnu"));
        jwt.setExpire(true);
        jwt.setDesactive(true);
                jwtRepository.save(jwt);

    }

    @Scheduled(cron="0 */1 * * * *")
    public void deleteTokenexpiredesactive(){
        log.info("Suppression des token a {}", Instant.now());
        jwtRepository.deleteAllByExpireAndDesactive(true,true);

    }

}
