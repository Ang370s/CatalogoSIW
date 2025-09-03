package it.catalogosiw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Utente;
import it.catalogosiw.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    public Optional<Utente> findByNomeAndCognome(String nome, String cognome) {
        return utenteRepository.findByNomeAndCognome(nome, cognome);
    }
    
    public boolean existsByEmail(String email) {
        return this.utenteRepository.existsByEmail(email);
    }
}