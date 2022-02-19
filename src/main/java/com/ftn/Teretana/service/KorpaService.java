package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.Korpa;

public interface KorpaService {
	
	Korpa save(Korpa korpa);
	List<Korpa> findAll();
	Korpa findOne(Long id);
	List<Korpa> findForOne(Long id);
	Korpa delete(Long id);
	List<Korpa> find(Long terminId, Long korisnikId, Double cena, Boolean aktivna);
	List<Korpa> find(Long[] ids);
	Korpa update(Korpa korpa);
	List<Korpa> findForOne(boolean aktivna, Long id);




}
