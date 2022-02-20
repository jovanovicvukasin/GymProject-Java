package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.Trening;

public interface TreningDAO {
	
	public List<Trening> findAll();
	List<Trening> find(String naziv, Long tipTreningaId,String trener, Double cenaOd, Double cenaDo, String vrstaTreninga, String nivoTreninga);
	public Trening findOne(Long id);
	public int edit(Trening trening);
	public int save(Trening trening);
	public int update(Trening trening);



}
