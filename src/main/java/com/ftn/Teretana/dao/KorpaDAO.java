package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.Korpa;

public interface KorpaDAO {
	
	public int save(Korpa korpa); 
	public List<Korpa> findAll();
	public Korpa findOne(Long id);

}
