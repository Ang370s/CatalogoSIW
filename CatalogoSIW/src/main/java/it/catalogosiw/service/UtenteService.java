package it.catalogosiw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Utente;
import it.catalogosiw.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
	
    @Autowired
    private CredentialsService credentialsService;

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    public Optional<Utente> findByNomeAndCognome(String nome, String cognome) {
        return utenteRepository.findByNomeAndCognome(nome, cognome);
    }
    
    public boolean existsByEmail(String email) {
        return this.utenteRepository.existsByEmail(email);
    }

	public Utente getUtenteCorrente() {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return this.credentialsService.findByUsername(userDetails.getUsername()).getUtente();
    }
}