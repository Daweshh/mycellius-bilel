package fr.mycellius.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import java.util.Arrays;

import java.util.List;
@Configuration
public class SecurityConfig {
    @Value("${mycellius.cors.allowed-origins}")
    private String allowedOrigins;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.deny())
                        .contentTypeOptions(Customizer.withDefaults())
                        .referrerPolicy(ref -> ref.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER))
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                        .cacheControl(Customizer.withDefaults())
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(b -> b.disable())
                .formLogin(f -> f.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // Public
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/health", "/actuator/health").permitAll()
                                .requestMatchers("/api/version").permitAll()
                                .requestMatchers("/error").permitAll()
                                // Swagger/OpenAPI
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                // Audit: ADMIN only
                                .requestMatchers(HttpMethod.GET, "/api/v1/audit/**").hasRole("ADMIN")
                                // RBAC Pages
                                .requestMatchers(HttpMethod.GET, "/api/v1/pages/**")
                                .hasAnyRole("STAGIAIRE", "DEV", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/pages/**")
                                .hasAnyRole("DEV", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/pages/**")
                                .hasAnyRole("DEV", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/pages/**")
                                .hasAnyRole("DEV", "ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(eh -> eh
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((req, res, ex) -> res.sendError(HttpStatus.FORBIDDEN.value()))
        );
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(origins);
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}