package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.TipTreninga;

public interface TipTreningaDAO {
	
	public List<TipTreninga> findAll();
	
	public TipTreninga findOne(Long id);

	public List<TipTreninga> find(String ime, String opis);

}
