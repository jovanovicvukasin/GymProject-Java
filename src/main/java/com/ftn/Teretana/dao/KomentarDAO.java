package com.ftn.Teretana.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.Teretana.model.Komentar;

public interface KomentarDAO {

	public List<Komentar> findAll();
	public int save(Komentar komentar);
	public int update(Komentar komentar);
	public Komentar findOne(Long id);
	List<Komentar> find(String tekst, Integer ocena, LocalDate datum, Long korisnikId, Long treningId, String statusKomentara);
}
