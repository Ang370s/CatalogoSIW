package it.catalogosiw.service;

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

    public Credentials findByUsername(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }
}