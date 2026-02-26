
package com.worknest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;


@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // ✅ allow CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // public
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()

                // role-based
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/employee/**").hasRole("EMPLOYEE")
                
                

                // authenticated
                .requestMatchers("/api/tasks/my").hasRole("EMPLOYEE")
                .requestMatchers("/api/tasks/**").authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

















//package com.worknest.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//      http
//          .csrf(csrf -> csrf.disable())
//          .authorizeHttpRequests(auth -> auth
//              .requestMatchers(
//                  "/api/auth/register",
//                  "/api/auth/login",
//                  "/api/admin/**"
//              ).permitAll()
//              .anyRequest().authenticated()
//          )
//          .httpBasic(Customizer.withDefaults());
//
//      return http.build();
//  }
//}
