package it.catalogosiw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.catalogosiw.model.Prodotto;
import it.catalogosiw.service.ProdottoService;

@Controller
public class MainController {
	
	@Autowired
	private ProdottoService prodottoService;

	@GetMapping("/")
	public String mostraCatalogo(@RequestParam(value = "q", required = false) String query, Model model) {
	    List<Prodotto> prodotti;
	    if (query != null && !query.trim().isEmpty()) {
	        prodotti = prodottoService.findByNomeContainingIgnoreCase(query);
	    } else {
	        prodotti = prodottoService.findAll();
	    }
	    model.addAttribute("prodotti", prodotti);
	    model.addAttribute("q", query);
	    return "catalogo.html";
	}
	
}