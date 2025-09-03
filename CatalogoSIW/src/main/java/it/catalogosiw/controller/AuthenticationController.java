package it.catalogosiw.controller;

import java.io.IOException;

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
import it.catalogosiw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) boolean error, Model model) {
		
		if(error)
			model.addAttribute("msgError", "Username o Password errati.");	
		
		return "login.html";
	}
	
	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("credentials", new Credentials());
	    model.addAttribute("utente", new Utente());
	    return "signup.html";
	}


	@PostMapping("/signup")
	public String processSignup(
	   @Valid @ModelAttribute("credentials") Credentials credentials,
	   BindingResult credentialsBindingResult,
	   @Valid @ModelAttribute("utente") Utente utente,
	   BindingResult utenteBindingResult,
	   @RequestParam(name = "confermaPwd") String confermaPwd,
	   Model model, 
	   RedirectAttributes redirectAttributes) throws IOException {
	    // Validazioni base
	    if (utenteBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
            model.addAttribute("utente", utente);
            model.addAttribute("credentials", credentials);
	        return "signup.html";
	    }

	    if (credentialsService.existsByUsername(credentials.getUsername())) {
	    	model.addAttribute("msgError", "Username già in uso");
            model.addAttribute("utente", utente);
            model.addAttribute("credentials", credentials);
	        return "signup.html";
	    }
	    
	    if (utenteService.existsByEmail(utente.getEmail())) {
	    	model.addAttribute("msgError", "Email già in uso");
            model.addAttribute("utente", utente);
            model.addAttribute("credentials", credentials);
	        return "signup.html";
	    }
	    
        credentials.setUtente(utente);
        credentials.setRole(Credentials.DEFAULT_ROLE);

        try {
            credentialsService.saveCredentials(credentials);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Errore durante la registrazione");
            model.addAttribute("utente", utente);
            model.addAttribute("credentials", credentials);
            return "signup.html";
        }
        
		redirectAttributes.addFlashAttribute("msgSuccess", "Registrazione completata con successo");
            
        return "redirect:/login";
           
	}
	
}