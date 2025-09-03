package it.catalogosiw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Credentials;
import it.catalogosiw.repository.CredentialsRepository;
import it.catalogosiw.model.Utente;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Credentials save(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }

    public Credentials findByUsername(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }
    
    public Credentials findByUtente(Utente utente) {
        return credentialsRepository.findByUtente(utente).orElse(null);
    }

    public List<Credentials> findAll() {
        return (List<Credentials>)credentialsRepository.findAll();
    }

    public void deleteById(Long id) {
        credentialsRepository.deleteById(id);
    }

    public Credentials findById(Long id) {
        return credentialsRepository.findById(id).orElse(null);
    }
    
    public boolean existsByUsername(String username) {
        return this.credentialsRepository.existsByUsername(username);
    }

    @Transactional
    public Credentials saveCredentials(@Valid Credentials credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return credentialsRepository.save(credentials);
    }

    public Credentials getCredentials(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }

    public Credentials getCredentialsByUtente(Utente utente) {
        return credentialsRepository.findByUtente(utente).orElse(null);
    }
    
    public boolean isAdmin(Credentials credentials) {
        return "ADMIN".equals(credentials.getRole());
    }

    public boolean isPilota(Credentials credentials) {
        return "PILOTE".equals(credentials.getRole());
    }
    
}