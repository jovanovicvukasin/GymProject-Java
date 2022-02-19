package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.Korpa;

public interface KorpaDAO {
	
	public int save(Korpa korpa); 
	public List<Korpa> findAll();
	public Korpa findOne(Long id);
	public List<Korpa> findForOne(Long id);
	public int delete(Long id);
	List<Korpa> find(Long terminId, Long korisnikId, Double cena, Boolean aktivna);
	public int update(Korpa korpa);
	public List<Korpa> findForOne(boolean aktivna, Long id);




}
