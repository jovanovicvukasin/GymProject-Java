package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.Sala;

public interface SalaDAO {
	
	public List<Sala> findAll();
	public List<Sala> findOznakaSale(String oznakaSale);
	public Sala findOne(Long id);
	public void save(Sala sala);
	public Sala findOne(String oznakaSale);
	public void edit(Sala sala);


}
