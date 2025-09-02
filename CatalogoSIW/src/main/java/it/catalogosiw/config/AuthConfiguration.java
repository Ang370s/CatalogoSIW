package it.catalogosiw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(requests -> requests
                //.requestMatchers("/**").permitAll()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, campionati, gare, piloti ai css e alle immagini
                .requestMatchers(HttpMethod.GET,
                        "/**", "/index", "/register", "/css/**", "/images/**",
                        "/campionati/**",
                        "/gare/**",
                        "/piloti/**",
                        "/login", "/signup"
                    ).permitAll());
		
		return httpSecurity.build();
	}

	
}

