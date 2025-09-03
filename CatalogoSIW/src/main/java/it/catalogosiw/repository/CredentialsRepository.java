package it.catalogosiw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.catalogosiw.model.Credentials;
import it.formulasiw.model.Utente;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	Optional<Credentials> findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public Optional<Credentials> findByUtente(Utente utente);
	
}