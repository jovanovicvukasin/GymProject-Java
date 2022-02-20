package com.ftn.Teretana.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.Zakazivanje;

public interface ZakazivanjeService {
	
	Zakazivanje save(Zakazivanje zakazivanje);
	Zakazivanje findOne(Long id);
	List<Zakazivanje> find(Long korpaId, Double ukupnaCena, LocalDateTime datumZakazivanja, Long korisnikId, Integer ukupanBroj);
	List<Zakazivanje> findAll();
	

}
