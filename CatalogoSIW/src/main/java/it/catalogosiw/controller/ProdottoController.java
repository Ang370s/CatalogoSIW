package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.catalogosiw.service.ProdottoService;

@Controller
public class ProdottoController {
	
	@Autowired
	private ProdottoService prodottoService;
	
	/* UTENTE GENERALE */
	
	@GetMapping("prodotti/{id}")
	public String visualizzaProdotto(@PathVariable Long id, Model model) {
		model.addAttribute("prodotto", prodottoService.findById(id));
		return "prodotto.html";
	}
	
	/* UTENTE AUTENTICATO */
	
	@GetMapping("utente/prodotti/{id}")
	public String visualizzaProdottoUtente(@PathVariable Long id, Model model) {
		model.addAttribute("prodotto", prodottoService.findById(id));
		return "utente/prodotto.html";
	}
	
}
