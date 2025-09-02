package it.catalogosiw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Credentials;
import it.catalogosiw.repository.CredentialsRepository;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    public Credentials save(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }

    public Optional<Credentials> findByUsername(String username) {
        return credentialsRepository.findByUsername(username);
    }
}