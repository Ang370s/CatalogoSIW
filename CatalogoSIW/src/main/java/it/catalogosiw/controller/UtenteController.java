package it.catalogosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.catalogosiw.model.Credentials;
import it.catalogosiw.model.Utente;
import it.catalogosiw.service.CredentialsService;
import it.catalogosiw.service.ProdottoService;
import it.catalogosiw.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UtenteController {
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/utente")
	public String mostraCatalogoUtente(Model model) {
	    Utente utente = utenteService.getUtenteCorrente();
	    if (utente == null) {
	        return "redirect:/login";
	    }

	    model.addAttribute("prodotti", prodottoService.findAll());
	    return "utente/catalogo.html";
	}

	
	
	@GetMapping("/utente/profilo")
	public String mostraProfilo(@RequestParam(value = "showPasswordModal", required = false, defaultValue = "false") boolean showPasswordModal,
								Model model) {
		
		Utente utente = utenteService.getUtenteCorrente();
		model.addAttribute("utente", utente);
		model.addAttribute("credentials", this.credentialsService.findByUtente(utente));
		model.addAttribute("showPasswordModal", showPasswordModal);
		return "utente/profilo.html";
	}
	
	
	@GetMapping("/utente/modificaProfilo")
	public String mostraModificaProfilo(Model model) {

		
		Utente utente = utenteService.getUtenteCorrente();
		model.addAttribute("utente", utente);
		model.addAttribute("credentials", this.credentialsService.findByUtente(utente));
		
		return "utente/modificaProfilo.html";
	}
	
	@PostMapping("/utente/modificaProfilo")
	public String modificaProfilo(@Valid @ModelAttribute Utente utenteModificato,
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
	        return "utente/modificaProfilo.html";
	    }

	    if (!(credentials.getUsername().equals(credentialsModificate.getUsername())) && credentialsService.existsByUsername(credentialsModificate.getUsername())) {
	    	model.addAttribute("msgError", "Username già in uso");
            model.addAttribute("utente", utenteModificato);
            model.addAttribute("credentials", credentialsModificate);
	        return "utente/modificaProfilo.html";
	    }

	    if (!(utente.getEmail().equals(utenteModificato.getEmail())) && utenteService.existsByEmail(utenteModificato.getEmail())) {
	    	model.addAttribute("msgError", "Email già in uso");
            model.addAttribute("utente", utenteModificato);
            model.addAttribute("credentials", credentialsModificate);
	        return "utente/modificaProfilo.html";
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
	    	
	    	/*return "redirect:/logout";*/
	    }
	    
	    redirectAttributes.addFlashAttribute("msgSuccess", "Modifiche effettuate con successo");
	    
	    return "redirect:/utente/profilo";
	}
	
	
	@PostMapping("/utente/cambiaPassword")
	public String updateCredentialsUser(@RequestParam @Valid String confirmPwd,
										@RequestParam @Valid String newPwd, Model model,
										RedirectAttributes redirectAttributes) {
		
		Utente utente = this.utenteService.getUtenteCorrente();
		Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);
		
		if (newPwd == null || confirmPwd == null || newPwd.equals("") || confirmPwd.equals("")) {
			model.addAttribute("msgError", "Il campo della nuova password è vuota");
			model.addAttribute("showPasswordModal", true);
    		model.addAttribute("utente", utente);
    		model.addAttribute("credentials", credentials);
			return "utente/profilo.html";
		}
		if(!newPwd.equals(confirmPwd)) {
			model.addAttribute("msgError", "Le due password non corrispondono");
			model.addAttribute("showPasswordModal", true);
    		model.addAttribute("utente", utente);
    		model.addAttribute("credentials", credentials);
			return "utente/profilo.html";
		}
		
		credentials.setPassword(newPwd);
		this.credentialsService.saveCredentials(credentials);
		
		redirectAttributes.addFlashAttribute("msgSuccess", "Password modificata con successo");
		
		return "redirect:/utente/profilo";
	}
	
	
	/*@GetMapping("/utente/eliminaProfilo")
	public String cancellaProfilo() {
		
		Utente utente = utenteService.getUtenteCorrente();
		Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);
		
		credentialsService.deleteById(credentials.getId());
		
		return "redirect:/logout";
	}*/
	
	@GetMapping("/utente/eliminaProfilo")
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

	
}
