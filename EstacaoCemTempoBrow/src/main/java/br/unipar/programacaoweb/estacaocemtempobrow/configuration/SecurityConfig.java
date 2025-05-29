package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableSpringDataWebSupport
@EnableMethodSecurity
public class SecurityConfig
{

    @Value("${security.cors.origin}")
    private String cors_origin;

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter)
    {

        this.securityFilter = securityFilter;

    }

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource)
            throws Exception
    {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        (

                                authorizeRequests ->
                                        authorizeRequests
                                                .requestMatchers("auth/login").permitAll()
                                                .anyRequest().denyAll()

                        )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {

        String[] origins = cors_origin.split(",");

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList(origins));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Bearer"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;

    }

}
