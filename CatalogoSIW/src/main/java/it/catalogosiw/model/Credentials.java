package it.catalogosiw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credentials {
    
	public static final String DEFAULT_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_seq_gen")
    @SequenceGenerator(name = "credentials_seq_gen", sequenceName = "credentials_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Username obbligatorio")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password obbligatoria")
    private String password;

    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    private Utente utente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}

	public static String getAdminRole() {
		return ADMIN_ROLE;
	}

	@Override
	public String toString() {
		return "Credentials [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", utente=" + utente + "]";
	}
    
}