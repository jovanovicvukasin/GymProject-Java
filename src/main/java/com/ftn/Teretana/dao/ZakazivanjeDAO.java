package com.ftn.Teretana.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.Zakazivanje;

public interface ZakazivanjeDAO {

	public int save(Zakazivanje zakazivanje);
	public Zakazivanje findOne(Long id);
	List<Zakazivanje> find(Long korpaId, Double ukupnaCena, LocalDateTime datumZakazivanja, Long korisnikId, Integer ukupanBroj);
	public List<Zakazivanje> findAll();

}
