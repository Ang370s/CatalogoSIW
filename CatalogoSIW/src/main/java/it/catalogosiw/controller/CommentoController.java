package it.catalogosiw.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.catalogosiw.model.Commento;
import it.catalogosiw.model.Prodotto;
import it.catalogosiw.model.Utente;
import it.catalogosiw.service.CommentoService;
import it.catalogosiw.service.ProdottoService;
import it.catalogosiw.service.UtenteService;

@Controller
public class CommentoController {
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CommentoService commentoService;

	@PostMapping("/utente/prodotti/{id}/commenti")
	public String aggiungiOmodificaCommento(@PathVariable Long id,
	                                        @RequestParam("nuovoCommento") String nuovoCommento) {
	    Prodotto prodotto = prodottoService.findById(id);
	    Utente autore = utenteService.findById(utenteService.getUtenteCorrente().getId());

	    // Se esiste già un commento dell'utente su questo prodotto → aggiorno
	    Commento commento = commentoService.findByProdottoAndAutore(prodotto, autore);
	    if (commento == null) {
	        commento = new Commento();
	        commento.setProdotto(prodotto);
	        commento.setAutore(autore);
	        commento.setDataCreazione(LocalDateTime.now());
	    }

	    commento.setTesto(nuovoCommento);
	    commentoService.save(commento);

	    return "redirect:/utente/prodotti/" + id;
	}

	@PostMapping("/utente/prodotti/{id}/commenti/{commentoId}/elimina")
	public String eliminaCommento(@PathVariable Long id,
	                              @PathVariable Long commentoId) {
	    Utente utente = utenteService.findById(utenteService.getUtenteCorrente().getId());
	    Commento commento = commentoService.findById(commentoId);

	    if (commento != null && commento.getAutore().getId().equals(utente.getId())) {
	        commentoService.deleteById(commentoId);
	    }

	    return "redirect:/utente/prodotti/" + id;
	}
	
}
