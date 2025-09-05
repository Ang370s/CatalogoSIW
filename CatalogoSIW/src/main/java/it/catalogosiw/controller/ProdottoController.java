package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	    Utente utente = utenteService.getUtenteCorrente();

	    // Recupero commento dell'utente (se esiste)
	    Commento commentoUtente = commentoService.findByProdottoAndAutore(prodotto, utente);

	    model.addAttribute("prodotto", prodotto);
	    model.addAttribute("commenti", prodotto.getCommenti());
	    model.addAttribute("utente", utente);
	    model.addAttribute("commentoUtente", commentoUtente);

	    return "utente/prodotto.html";
	}
	
}
