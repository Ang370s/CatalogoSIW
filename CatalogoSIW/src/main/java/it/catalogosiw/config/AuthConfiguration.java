package it.catalogosiw.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.catalogosiw.model.Utente;
import it.catalogosiw.service.CredentialsService;



@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	public static final String DEFAULT_ROLE = "USER";
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
				.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(requests -> requests
                //.requestMatchers("/**").permitAll()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, campionati, gare, piloti ai css e alle immagini
                .requestMatchers(HttpMethod.GET,
                        "/**", "/index", "/register", "/css/**", "/images/**", // Cambiare "/**" in "/"
                        "/campionati/**",
                        "/gare/**",
                        "/piloti/**",
                        "/login", "/signup"
                    ).permitAll()

                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
                .requestMatchers(HttpMethod.POST, "/signup", "/login").permitAll()
                .requestMatchers("/admin/**").hasAuthority(ADMIN_ROLE)
                .requestMatchers("/utente/**").hasAuthority(DEFAULT_ROLE)
                // tutti gli utenti autenticati possono accedere alle pagine rimanenti 
                .anyRequest().authenticated()).formLogin(login -> login.loginPage("/login")
                // LOGIN: qui definiamo il login
                .loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("pwd")
                .successHandler((request, response, authentication) -> {
                    var principal = authentication.getPrincipal();
                    String username = null;
                    Long idUtente = null;

                    if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
                        username = userDetails.getUsername();
                    }

                    var credentials = this.credentialsService.findByUsername(username);
                    boolean isAdmin = credentials.getRole().equals(ADMIN_ROLE);
                    Utente utente = credentials.getUtente();
                    idUtente = utente.getId();

                    if (isAdmin) {
                        response.sendRedirect(idUtente != null ? "/admin" : "/home");
                    } else {
                        response.sendRedirect(idUtente != null ? "/utente" : "/home");
                    }
                })

                .failureUrl("/login?error=true")
                .permitAll())
                .logout(logout -> logout
                        // il logout è attivato con una richiesta GET a "/logout"
                        .logoutUrl("/logout")
                        // in caso di successo, si viene reindirizzati alla home
                        .logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll());
		
		return httpSecurity.build();
	}

	
}
