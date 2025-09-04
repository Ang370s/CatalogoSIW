package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.catalogosiw.model.Commento;
import it.catalogosiw.model.Prodotto;
import it.catalogosiw.model.Utente;
import it.catalogosiw.service.ProdottoService;
import it.catalogosiw.service.UtenteService;
import it.catalogosiw.service.CommentoService;

@Controller
public class ProdottoController {
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired
	private UtenteService utenteService;
	
	/* UTENTE GENERALE */
	
	@GetMapping("/prodotti/{id}")
	public String mostraProdotto(@PathVariable Long id, Model model) {
	    Prodotto prodotto = prodottoService.findById(id);
	    model.addAttribute("prodotto", prodotto);
	    model.addAttribute("commenti", commentoService.findByProdotto(prodotto));
	    return "prodotto.html";
	}
	
	/* UTENTE AUTENTICATO */
	
	@GetMapping("/utente/prodotti/{id}")
	public String visualizzaProdottoUtente(@PathVariable Long id, Model model) {
		Prodotto prodotto = prodottoService.findById(id);
	    model.addAttribute("prodotto", prodotto);
	    model.addAttribute("commenti", commentoService.findByProdotto(prodotto));
	    model.addAttribute("nuovoCommento", new Commento());
	    return "utente/prodotto.html";
	}
	

	@PostMapping("/utente/prodotti/{id}/commenti")
	public String aggiungiCommento(@PathVariable Long id,
	                               @ModelAttribute("nuovoCommento") Commento commento) {
	    Prodotto prodotto = prodottoService.findById(id);
	    Utente autore = utenteService.findById(utenteService.getUtenteCorrente().getId());

	    commento.setProdotto(prodotto);
	    commento.setAutore(autore);
	    //prodotto.addCommento(commento);
	    //autore.addCommento(commento);
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
