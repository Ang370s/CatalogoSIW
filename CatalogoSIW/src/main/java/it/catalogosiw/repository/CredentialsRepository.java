package it.catalogosiw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.catalogosiw.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	Optional<Credentials> findByUsername(String username);
	
}