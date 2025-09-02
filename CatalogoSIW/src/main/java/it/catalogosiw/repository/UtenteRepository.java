package it.catalogosiw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.catalogosiw.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	Optional<Utente> findByNomeAndCognome(String nome, String cognome);
	
}