package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.Sala;

public interface SalaService {
	
	List<Sala> findAll();
	List<Sala> findOznakaSale(String oznakaSale);
	Sala findOne(Long id);
	Sala save(Sala sala);
	Sala findOne(String oznakaSale);
	Sala edit(Sala sala);


}
