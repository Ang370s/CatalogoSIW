package it.catalogosiw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Commento;
import it.catalogosiw.model.Prodotto;
import it.catalogosiw.model.Utente;
import it.catalogosiw.repository.CommentoRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentoService {

    @Autowired
    private CommentoRepository commentoRepository;
    
    public Commento save(Commento commento) {
        return commentoRepository.save(commento);
    }

    public void deleteById(Long id) {
        if (commentoRepository.existsById(id)) {
            commentoRepository.deleteById(id);
        }
    }

    
    public Commento findById(Long id) {
    	return commentoRepository.findById(id).orElse(null);
    }

    public void delete(Commento commento, Utente utenteCorrente) {
        if (commento.getAutore().equals(utenteCorrente)) {
            commentoRepository.delete(commento);
        } else {
            throw new SecurityException("Non puoi eliminare un commento non tuo");
        }
    }

    public List<Commento> findByProdotto(Prodotto prodotto) {
        return commentoRepository.findByProdotto(prodotto);
    }

    public List<Commento> findByAutore(Utente autore) {
        return commentoRepository.findByAutore(autore);
    }

}