package it.catalogosiw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Utente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_seq_gen")
    @SequenceGenerator(name = "utente_seq_gen", sequenceName = "utente_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 3, max = 50, message = "Il nome deve avere minimo 3 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 3, max = 50, message = "Il cognome deve avere minimo 3 caratteri")
    private String cognome;

    @Email(message = "Email non valida")
    @NotBlank(message = "l'email è obbligatoria")
    @Size(min = 3, max = 100, message = "Email troppo corta")
    private String email;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Credentials credentials;

    @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> commenti = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", commenti=" + commenti + "]";
	}
    
}