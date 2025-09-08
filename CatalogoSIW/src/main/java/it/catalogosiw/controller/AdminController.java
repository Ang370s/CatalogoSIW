package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.catalogosiw.model.Credentials;
import it.catalogosiw.model.Prodotto;
import it.catalogosiw.model.Utente;
import it.catalogosiw.service.CredentialsService;
import it.catalogosiw.service.ProdottoService;
import it.catalogosiw.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AdminController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ProdottoService prodottoService;
	
	@GetMapping("/admin/profilo")
	public String mostraProfiloAdmin(@RequestParam(value = "showPasswordModal", required = false, defaultValue = "false") boolean showPasswordModal,
								@RequestParam(value = "annulla", required = false, defaultValue = "false") boolean annulla,
								Model model) {
		
		Utente utente = utenteService.getUtenteCorrente();
		model.addAttribute("utente", utente);
		model.addAttribute("credentials", this.credentialsService.findByUtente(utente));
		model.addAttribute("showPasswordModal", showPasswordModal);
		
		if (annulla)
	        model.addAttribute("msgSuccess", "Operazione annullata");
		
		return "admin/profilo.html";
	}
	
	@GetMapping("/admin/modificaProfilo")
	public String mostraModificaProfiloAdmin(Model model) {

		
		Utente utente = utenteService.getUtenteCorrente();
		model.addAttribute("utente", utente);
		model.addAttribute("credentials", this.credentialsService.findByUtente(utente));
		
		return "admin/modificaProfilo.html";
	}
	
	@PostMapping("/admin/modificaProfilo")
	public String modificaProfiloAdmin(@Valid @ModelAttribute Utente utenteModificato,
			 					  BindingResult utenteBindingResult, 
			 					  @Valid @ModelAttribute("credentials") Credentials credentialsModificate,
			 					  BindingResult credentialsBindingResult, 
			 					  Model model,
			 					  RedirectAttributes redirectAttributes,
			 					  HttpServletRequest request) {
		// Recupero dal DB
	    Utente utente = utenteService.getUtenteCorrente();
	    Credentials credentials = credentialsService.findByUtente(utente);

	    // Validazioni base
	    if (utenteBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
	    	System.out.println("\n\n\n Utente: " + utenteBindingResult.getAllErrors() + "\n\n\n");
	    	System.out.println("\n\n\n Utente: " + credentialsBindingResult.getAllErrors() + "\n\n\n");
	    	model.addAttribute("msgError", "Campi non validi");
            model.addAttribute("utente", utenteModificato);
            model.addAttribute("credentials", credentialsModificate);
	        return "admin/modificaProfilo.html";
	    }

	    if (!(credentials.getUsername().equals(credentialsModificate.getUsername())) && credentialsService.existsByUsername(credentialsModificate.getUsername())) {
	    	model.addAttribute("msgError", "Username già in uso");
            model.addAttribute("utente", utenteModificato);
            model.addAttribute("credentials", credentialsModificate);
	        return "admin/modificaProfilo.html";
	    }

	    if (!(utente.getEmail().equals(utenteModificato.getEmail())) && utenteService.existsByEmail(utenteModificato.getEmail())) {
	    	model.addAttribute("msgError", "Email già in uso");
            model.addAttribute("utente", utenteModificato);
            model.addAttribute("credentials", credentialsModificate);
	        return "admin/modificaProfilo.html";
	    }

	    // Aggiorno solo i campi che possono essere modificati dal form
	    utente.setNome(utenteModificato.getNome());
	    utente.setCognome(utenteModificato.getCognome());
	    utente.setEmail(utenteModificato.getEmail());
	    
	    String username = credentials.getUsername();

	    // Aggiorno lo username
	    credentials.setUsername(credentialsModificate.getUsername());

	    // Salvo prima il pilota e poi le credenziali (se non hai cascade)
	    utenteService.save(utente);
	    credentialsService.save(credentials);
	    
	    if(!username.equals(credentialsModificate.getUsername())) {
	    	
	    	// Invalido la sessione (logout manuale)
		    request.getSession().invalidate();

		    // Flash message
		    redirectAttributes.addFlashAttribute("msgSuccess", "Modificato username con successo. Rieffettuare il login");
	    	
	    	return "redirect:/login";
	    }
	    
	    redirectAttributes.addFlashAttribute("msgSuccess", "Modifiche effettuate con successo");
	    
	    return "redirect:/admin/profilo";
	}
	
	
	@PostMapping("/admin/cambiaPassword")
	public String updateCredentialsUserAdmin(@RequestParam @Valid String confirmPwd,
										@RequestParam @Valid String newPwd, Model model,
										RedirectAttributes redirectAttributes,
										@RequestParam(value = "annulla", required = false, defaultValue = "false") boolean annulla) {
		
		Utente utente = this.utenteService.getUtenteCorrente();
		Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);
		
		if (newPwd == null || confirmPwd == null || newPwd.equals("") || confirmPwd.equals("")) {
			model.addAttribute("msgError", "Il campo della nuova password è vuota");
			model.addAttribute("showPasswordModal", true);
    		model.addAttribute("utente", utente);
    		model.addAttribute("credentials", credentials);
			return "admin/profilo.html";
		}
		if(!newPwd.equals(confirmPwd)) {
			model.addAttribute("msgError", "Le due password non corrispondono");
			model.addAttribute("showPasswordModal", true);
    		model.addAttribute("utente", utente);
    		model.addAttribute("credentials", credentials);
			return "admin/profilo.html";
		}
		
		credentials.setPassword(newPwd);
		this.credentialsService.saveCredentials(credentials);
		
		if (annulla)
	        model.addAttribute("msgSuccess", "Operazione annullata");
		
		redirectAttributes.addFlashAttribute("msgSuccess", "Password modificata con successo");
		
		return "redirect:/admin/profilo";
	}
	
	@GetMapping("/admin/eliminaProfilo")
	public String cancellaProfilo(HttpServletRequest request, RedirectAttributes redirectAttributes) {

	    Utente utente = utenteService.getUtenteCorrente();
	    Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);

	    credentialsService.deleteById(credentials.getId());

	    // Invalido la sessione (logout manuale)
	    request.getSession().invalidate();

	    // Flash message
	    redirectAttributes.addFlashAttribute("msgSuccess", "Account eliminato con successo");

	    return "redirect:/";
	}
	
	
	/* OPERAZIONI ADMIN */
	
	@GetMapping("/admin/aggiungiProdotto")
	public String mostraFormAggiungiProdotto(Model model) {
	    model.addAttribute("prodotto", new Prodotto());
	    return "admin/formAggiungiProdotto.html";
	}

	@PostMapping("/admin/aggiungiProdotto")
	public String aggiungiProdotto(@Valid @ModelAttribute Prodotto prodotto,
								   BindingResult prodottoBindingResult,
	                               RedirectAttributes redirectAttributes,
	                               Model model) {
		
		if (prodottoBindingResult.hasErrors()) {
			System.out.println("\n\n\n Utente: " + prodottoBindingResult.getAllErrors() + "\n\n\n");
	    	model.addAttribute("msgError", "Campi non validi");
            model.addAttribute("prodotto", prodotto);
	        return "admin/formAggiungiProdotto.html";
	    }
		
	    // Controllo se esiste già (nome + prezzo uguali)
	    if (prodottoService.existsByNomeAndTipologia(prodotto.getNome(), prodotto.getTipologia())) {
	        model.addAttribute("msgError", "Prodotto già presente!");
	        return "admin/formAggiungiProdotto.html";
	    }

	    prodottoService.save(prodotto);
	    redirectAttributes.addFlashAttribute("msgSuccess", "Prodotto aggiunto con successo!");
	    return "redirect:/admin";
	}
	
	
	
	@GetMapping("/admin/prodotti/{id}/modificaProdotto")
	public String mostraFormModificaProdotto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
	    Prodotto prodotto = prodottoService.findById(id);
	    if (prodotto == null) {
	        redirectAttributes.addFlashAttribute("msgError", "Prodotto non trovato");
	        return "redirect:/admin";
	    }
	    model.addAttribute("prodotto", prodotto);
	    return "admin/modificaProdotto.html";
	}
	
	@PostMapping("/admin/prodotti/{id}/modificaProdotto")
	public String modificaProdotto(
	        @PathVariable Long id,
	        @Valid @ModelAttribute("prodotto") Prodotto prodotto,
	        BindingResult prodottoBindingResult,
	        RedirectAttributes redirectAttributes,
	        Model model) {

	    // validazione form
	    if (prodottoBindingResult.hasErrors()) {
	        model.addAttribute("msgError", "Campi non validi");
	        return "admin/modificaProdotto.html";
	    }

	    // controllo duplicato: cerca un prodotto con stesso nome+tipologia
	    if (prodottoService.existsByNomeAndTipologia(prodotto.getNome(), prodotto.getTipologia())) {
	        model.addAttribute("msgError", "Prodotto già presente!");
	        return "admin/formAggiungiProdotto.html";
	    }

	    // assicuriamoci di aggiornare il record corretto
	    prodotto.setId(id);

	    prodottoService.save(prodotto);
	    redirectAttributes.addFlashAttribute("msgSuccess", "Prodotto modificato con successo!");
	    return "redirect:/admin";
	}
	
	
	@GetMapping("/admin/prodotti/{id}/eliminaProdotto")
	public String eliminaProdotto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    prodottoService.deleteById(id);
	    redirectAttributes.addFlashAttribute("msgSuccess", "Prodotto eliminato con successo");
	    return "redirect:/admin";
	}

	
}