package it.catalogosiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.catalogosiw.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {

	List<Prodotto> findByNomeContainingIgnoreCase(String nome);

    List<Prodotto> findByTipologiaIgnoreCase(String tipologia);

	boolean existsByNomeAndPrezzo(String nome, Double prezzo);

	boolean existsByNomeAndTipologia(String nome, String tipologia);
	
}