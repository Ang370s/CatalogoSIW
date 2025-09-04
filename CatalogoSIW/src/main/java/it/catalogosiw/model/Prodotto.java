package it.catalogosiw.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Prodotto {
    
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodotto_seq_gen")
    @SequenceGenerator(name = "prodotto_seq_gen", sequenceName = "prodotto_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Il nome del prodotto è obbligatorio")
    private String nome;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0", message = "Il prezzo deve essere maggiore di 0")
    private Double prezzo;

    @Size(max = 2000, message = "La descrizione non può superare 2000 caratteri")
    @Column(length = 2000)
    private String descrizione;

    @NotBlank(message = "La tipologia è obbligatoria")
    private String tipologia;
    
    private String immagine;

    @ManyToMany
    private List<Prodotto> prodottiSimili = new ArrayList<>();

    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true)
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

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public List<Prodotto> getProdottiSimili() {
		return prodottiSimili;
	}

	public void setProdottiSimili(List<Prodotto> prodottiSimili) {
		this.prodottiSimili = prodottiSimili;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + ", descrizione=" + descrizione
				+ ", tipologia=" + tipologia + ", prodottiSimili=" + prodottiSimili + ", commenti=" + commenti + "]";
	}

	
	public void addCommento(Commento commento) {
	    this.commenti.add(commento);
	    commento.setProdotto(this);
	}

	
}