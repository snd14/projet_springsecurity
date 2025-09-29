package org.test.testspring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.test.testspring.service.UserDetailImpl;

import static org.springframework.http.HttpMethod.POST;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class Securityconfig {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .csrf(csrf -> csrf.disable())// Désactiver CSRF si nécessaire
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(POST,"/login").permitAll() // Routes publiques
                        .requestMatchers(POST,"/inscription").permitAll() // Routes publiques
                        .requestMatchers(POST,"/activation").permitAll() // Routes publiques
                        .requestMatchers(POST,"/modififiermdp").permitAll() // Routes publiques
                        .requestMatchers(POST,"/nouveaumdp").permitAll() // Routes publiques
                        .requestMatchers(POST,"/refresh-token").permitAll() // Routes publiques


                        .anyRequest().authenticated() // Tout le reste doit être authentifié
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        ).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
                //.httpBasic(Customizer.withDefaults())
                //.oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()));

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//@Bean
//     PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailImpl userDetailImpl){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

}
