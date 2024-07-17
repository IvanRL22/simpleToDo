package com.ivanrl.simpleToDo;

import com.ivanrl.simpleToDo.controller.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                    .requestMatchers(Paths.MAIN, Paths.HOME, "/error", "/webjars/**").permitAll()
                    .requestMatchers(toH2Console()).permitAll() // TODO Remove when using proper DB
                    .anyRequest().authenticated()
            )
            .csrf(c -> c.ignoringRequestMatchers(toH2Console())) // TODO Remove when using proper DB
            .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // TODO Remove when using proper DB
            .logout(l -> l
                    .logoutSuccessUrl(Paths.MAIN).permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
        ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
