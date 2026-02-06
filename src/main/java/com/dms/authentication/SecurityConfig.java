package com.dms.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index2.html").permitAll()
                        .requestMatchers("/dms/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/tasks/dashboard/insert").hasAuthority("EDITS_TASKS")
                        .requestMatchers(HttpMethod.GET, "/tasks/dashboard/edit/{uuid}").hasAuthority("EDITS_TASKS")
                        .requestMatchers(HttpMethod.POST, "/tasks/dashboard/edit").hasAuthority("EDITS_TASKS")
                        .requestMatchers(HttpMethod.GET, "/tasks/dashboard/delete/{uuid}").hasAuthority("EDITS_TASKS")
                        .requestMatchers("/tasks/dashboard**").hasAnyRole("ADMIN", "SUPER_ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST,"/clients/dashboard/insert").hasAuthority("EDITS_CLIENTS")
                        .requestMatchers(HttpMethod.GET, "/clients/dashboard/edit/{cid}").hasAuthority("EDITS_CLIENTS")
                        .requestMatchers(HttpMethod.POST, "/clients/dashboard/edit").hasAuthority("EDITS_CLIENTS")
                        .requestMatchers(HttpMethod.GET,"/clients/dashboard/delete/{cid}").hasAuthority("EDITS_CLIENTS")
                        .requestMatchers("/clients/dashboard/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/users/dashboard**").hasAnyRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/users/dashboard/insert").hasAuthority("EDITS_USERS")
                        .requestMatchers(HttpMethod.GET,"/users/dashboard/edit/{uuid}").hasAuthority("EDITS_USERS")
                        .requestMatchers(HttpMethod.GET, "/users/dashboard/delete/{uuid}").hasAuthority("EDITS_USERS")
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/bootstrap-5.3.8/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/tasks/dashboard", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
                return http.build();
    }


}
