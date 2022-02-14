package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.Korpa;

public interface KorpaService {
	
	Korpa save(Korpa korpa);
	List<Korpa> findAll();
	Korpa findOne(Long id);
	List<Korpa> findForOne(Long id);


}
