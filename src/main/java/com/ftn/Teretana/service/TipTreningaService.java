package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.TipTreninga;

public interface TipTreningaService {
	
	TipTreninga findOne(Long id);
	List<TipTreninga> findAll();
	List<TipTreninga> find(String ime, String opis);
	List<TipTreninga> find(Long[] ids);

}
