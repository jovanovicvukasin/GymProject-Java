package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.Trening;

public interface TreningService {
	
	List<Trening> findAll();
	List<Trening> find(String naziv, Long tipTreningaId, String trener, Double cenaOd, Double cenaDo, String vrstaTreninga, String nivoTreninga);
	Trening findOne(Long id); 
	Trening edit(Trening trening);
	Trening save(Trening trening);
	List<Trening> find(Long[] ids);
	Trening update(Trening trening);




}
