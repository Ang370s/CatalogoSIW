package it.catalogosiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.catalogosiw.model.Commento;
import it.catalogosiw.model.Prodotto;
import it.catalogosiw.model.Utente;

public interface CommentoRepository extends CrudRepository<Commento, Long> {

	 List<Commento> findByProdotto(Prodotto prodotto);

	 List<Commento> findByAutore(Utente autore);

	 Commento findByProdottoAndAutore(Prodotto prodotto, Utente utente);
	
}