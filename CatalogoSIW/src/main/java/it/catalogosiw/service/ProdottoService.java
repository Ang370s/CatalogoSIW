package it.catalogosiw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.catalogosiw.model.Prodotto;
import it.catalogosiw.repository.ProdottoRepository;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public Prodotto save(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public void delete(Long id) {
        prodottoRepository.deleteById(id);
    }

    public Prodotto findById(Long id) {
        return prodottoRepository.findById(id).orElse(null);
    }

    public List<Prodotto> findAll() {
        List<Prodotto> result = new ArrayList<>();
        prodottoRepository.findAll().forEach(result::add);
        return result;
    }

    public List<Prodotto> findByNome(String nome) {
        return prodottoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Prodotto> findByTipologia(String tipologia) {
        return prodottoRepository.findByTipologiaIgnoreCase(tipologia);
    }

    // Gestione prodotti simili
    public void addProdottoSimile(Prodotto prodotto, Prodotto simile) {
        prodotto.getProdottiSimili().add(simile);
        prodottoRepository.save(prodotto);
    }

    public void removeProdottoSimile(Prodotto prodotto, Prodotto simile) {
        prodotto.getProdottiSimili().remove(simile);
        prodottoRepository.save(prodotto);
    }
}