package com.onurkayhann.__minute_stoic_be.config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable() // Disable CSRF for testing purposes
            .authorizeRequests()
            .anyRequest().permitAll() // Allow all requests without authentication
            .and()
            .httpBasic().disable() // Disable HTTP Basic Authentication
            .formLogin().disable() // Disable Form Login
        return http.build()
    }
}