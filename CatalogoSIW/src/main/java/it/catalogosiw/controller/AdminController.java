package it.catalogosiw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ProdottoService prodottoService;
	
	@GetMapping("/profilo")
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
	
	@GetMapping("/modificaProfilo")
	public String mostraModificaProfiloAdmin(Model model) {

		
		Utente utente = utenteService.getUtenteCorrente();
		model.addAttribute("utente", utente);
		model.addAttribute("credentials", this.credentialsService.findByUtente(utente));
		
		return "admin/modificaProfilo.html";
	}
	
	@PostMapping("/modificaProfilo")
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
	
	
	@PostMapping("/cambiaPassword")
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
	
	@GetMapping("/eliminaProfilo")
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
	
	@GetMapping("/aggiungiProdotto")
	public String mostraFormAggiungiProdotto(Model model) {
	    Prodotto nuovoProdotto = new Prodotto();
	    nuovoProdotto.setProdottiSimili(new ArrayList<>()); // inizializzo la lista dei prodotti simili
	    model.addAttribute("nuovoProdotto", nuovoProdotto);
	    model.addAttribute("prodotti", prodottoService.findAll());
	    return "admin/formAggiungiProdotto.html";
	}

	@PostMapping("/aggiungiProdotto")
	public String aggiungiProdotto(@Valid @ModelAttribute("nuovoProdotto") Prodotto nuovoProdotto,
            					   BindingResult prodottoBindingResult, RedirectAttributes redirectAttribute, Model model,
            					   @RequestParam(value = "prodottiSimiliIds", required = false) List<Long> prodottiSimiliIds) {
		
		if (prodottoBindingResult.hasErrors()) {
			System.out.println("\n\n\n Utente: " + prodottoBindingResult.getAllErrors() + "\n\n\n");
	    	model.addAttribute("msgError", "Campi non validi");
	    	
	    	// Ricostruisco la lista dei prodotti simili selezionati
	        List<Prodotto> prodottiSimiliSelezionati = new ArrayList<>();
	        if (prodottiSimiliIds != null) {
	            for (Long id : prodottiSimiliIds) {
	                Prodotto p = prodottoService.findById(id);
	                if (p != null) prodottiSimiliSelezionati.add(p);
	            }
	        }
	        
	        nuovoProdotto.setProdottiSimili(prodottiSimiliSelezionati);
	    	
            model.addAttribute("nuovoProdotto", nuovoProdotto);
            model.addAttribute("prodotti", prodottoService.findAll());
	        return "admin/formAggiungiProdotto.html";
	    }
		
	    // Controllo se esiste già (nome + prezzo uguali)
	    if (prodottoService.existsByNomeAndTipologia(nuovoProdotto.getNome(), nuovoProdotto.getTipologia())) {
	        model.addAttribute("msgError", "Prodotto già presente!");
	        model.addAttribute("nuovoProdotto", nuovoProdotto);
	        model.addAttribute("prodotti", prodottoService.findAll());
	        return "admin/formAggiungiProdotto.html";
	    }
	    
	    // Salvo subito il nuovo prodotto per avere l'ID
	    prodottoService.save(nuovoProdotto);

	    // Gestione prodotti simili se sono stati selezionati
	    if (prodottiSimiliIds != null && !prodottiSimiliIds.isEmpty()) {
	        List<Prodotto> prodottiSimili = new ArrayList<>();
	        for (Long id : prodottiSimiliIds) {
	            Prodotto prodottoSimile = prodottoService.findById(id);
	            if (prodottoSimile != null) {
	                prodottiSimili.add(prodottoSimile);

	                // 🔄 Simmetria: aggiungo anche dall'altra parte
	                if (!prodottoSimile.getProdottiSimili().contains(nuovoProdotto)) {
	                    prodottoSimile.getProdottiSimili().add(nuovoProdotto);
	                    prodottoService.save(prodottoSimile);
	                }
	            }
	        }
	        nuovoProdotto.setProdottiSimili(prodottiSimili);
	        prodottoService.save(nuovoProdotto); // risalvo aggiornato
	    }
	    
	    redirectAttribute.addFlashAttribute("msgSuccess", "Prodotto aggiunto con successo!");
	    return "redirect:/admin";
	}
	
	
	
	@GetMapping("/prodotti/{id}/modificaProdotto")
	public String mostraFormModificaProdotto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
	    Prodotto prodotto = prodottoService.findById(id);
	    if (prodotto == null) {
	        redirectAttributes.addFlashAttribute("msgError", "Prodotto non trovato");
	        return "redirect:/admin";
	    }
	    
	    List<Prodotto> prodotti = prodottoService.findAll();
	    prodotti.remove(prodotto);
	    model.addAttribute("prodotto", prodotto);
	    model.addAttribute("prodotti", prodotti);
	    return "admin/modificaProdotto.html";
	}
	
	@PostMapping("/prodotti/{id}/modificaProdotto")
	public String modificaProdotto(
	        @PathVariable Long id,
	        @Valid @ModelAttribute("prodotto") Prodotto prodottoModificato,
	        BindingResult prodottoBindingResult,
	        @RequestParam(value = "prodottiSimiliIds", required = false) List<Long> prodottiSimiliIds,
	        RedirectAttributes redirectAttributes,
	        Model model) {
	    
	    Prodotto vecchioProdotto = prodottoService.findById(id);

	    if (prodottoBindingResult.hasErrors()) {
	        model.addAttribute("msgError", "Campi non validi");
	        model.addAttribute("prodotti", prodottoService.findAll());
	        return "admin/modificaProdotto.html";
	    }

	    if ((!vecchioProdotto.getNome().equals(prodottoModificato.getNome()) ||
	         !vecchioProdotto.getTipologia().equals(prodottoModificato.getTipologia())) &&
	        prodottoService.existsByNomeAndTipologia(prodottoModificato.getNome(), prodottoModificato.getTipologia())) {
	        model.addAttribute("msgError", "Prodotto già presente!");
	        return "admin/modificaProdotto.html";
	    }

	    vecchioProdotto.setPrezzo(prodottoModificato.getPrezzo());
	    vecchioProdotto.setDescrizione(prodottoModificato.getDescrizione());

	 // aggiorno i prodotti simili
	    List<Prodotto> nuoviSimili = new ArrayList<>();
	    if (prodottiSimiliIds != null) {
	        for (Long simileId : prodottiSimiliIds) {
	            Prodotto simile = prodottoService.findById(simileId);
	            if (simile != null) {
	                nuoviSimili.add(simile);

	                // 🔄 Aggiungo la simmetria se non c’è
	                if (!simile.getProdottiSimili().contains(vecchioProdotto)) {
	                    simile.getProdottiSimili().add(vecchioProdotto);
	                    prodottoService.save(simile);
	                }
	            }
	        }
	    }

	    // 🔄 Rimuovo la simmetria se non è più presente
	    for (Prodotto simile : new ArrayList<>(vecchioProdotto.getProdottiSimili())) {
	        if (!nuoviSimili.contains(simile)) {
	            simile.getProdottiSimili().remove(vecchioProdotto);
	            prodottoService.save(simile);
	        }
	    }

	    vecchioProdotto.setProdottiSimili(nuoviSimili);
	    prodottoService.save(vecchioProdotto);
	    
	    redirectAttributes.addFlashAttribute("msgSuccess", "Prodotto modificato con successo!");
	    return "redirect:/admin";
	}

	@GetMapping("/prodotti/{id}/eliminaProdotto")
	public String eliminaProdotto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    // Recupero il prodotto
	    Prodotto prodotto = prodottoService.findById(id);

	    if (prodotto == null) {
	        redirectAttributes.addFlashAttribute("msgError", "Prodotto non trovato");
	        return "redirect:/admin";
	    }

	 // 1. Rimuovo questo prodotto dalle liste di simili degli altri prodotti
	    List<Prodotto> tuttiProdotti = prodottoService.findAll();
	    for (Prodotto p : tuttiProdotti) {
	        if (p.getProdottiSimili().contains(prodotto)) {
	            p.getProdottiSimili().remove(prodotto);
	            prodottoService.save(p); // aggiorno il prodotto senza il riferimento
	        }
	    }

	    // 2. Pulisco anche la lista dei simili del prodotto stesso
	    prodotto.getProdottiSimili().clear();
	    prodottoService.save(prodotto);

	    // 3. Ora posso eliminarlo senza violare il vincolo
	    prodottoService.deleteById(id);

	    redirectAttributes.addFlashAttribute("msgSuccess", "Prodotto eliminato con successo!");
	    return "redirect:/admin";
	}

	
}