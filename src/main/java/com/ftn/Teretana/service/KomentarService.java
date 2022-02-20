package com.ftn.Teretana.service;

import java.time.LocalDate;
import java.util.List;

import com.ftn.Teretana.model.Komentar;

public interface KomentarService {
	
	List<Komentar> findAll();
	Komentar save(Komentar komentar);
	Komentar update(Komentar komentar);
	Komentar findOne(Long id);
	List<Komentar> find(String tekst, Integer ocena, LocalDate datum, Long korisnikId, Long treningId, String statusKomentara);


}
