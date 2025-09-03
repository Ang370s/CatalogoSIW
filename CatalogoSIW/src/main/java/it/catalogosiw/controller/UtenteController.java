package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.catalogosiw.service.ProdottoService;

@Controller
public class UtenteController {
	
	@Autowired
	private ProdottoService prodottoService;

	@GetMapping("/utente")
	public String mostraCatalogoUtente(Model model) {
		model.addAttribute("prodotti", prodottoService.findAll());
	    return "utente/catalogo.html";
	}
	
}
