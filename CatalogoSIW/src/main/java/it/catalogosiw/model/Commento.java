package it.catalogosiw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Commento {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commento_seq_gen")
    @SequenceGenerator(name = "commento_seq_gen", sequenceName = "commento_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Il testo del commento non può essere vuoto")
    @Size(max = 2000, message = "Il commento non può superare i 2000 caratteri")
    @Column(length = 2000)
    private String testo;
    
    //@NotNull(message = "La data di creazione è obbligatoria")
    private LocalDateTime dataCreazione;

    //@NotNull(message = "L'autore del commento è obbligatorio")
    @ManyToOne
    private Utente autore;

    //@NotNull(message = "Il prodotto associato è obbligatorio")
    @ManyToOne
    private Prodotto prodotto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Override
	public String toString() {
		return "Commento [id=" + id + ", testo=" + testo + ", autore=" + autore + ", prodotto=" + prodotto + "]";
	}
    
}