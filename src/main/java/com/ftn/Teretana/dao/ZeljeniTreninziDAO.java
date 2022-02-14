package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.ZeljeniTreninzi;

public interface ZeljeniTreninziDAO {
	
	public int save(ZeljeniTreninzi zeljeni);
	public int delete(Long id);
	public ZeljeniTreninzi findOne(Long id);
	List<ZeljeniTreninzi> find(Long treningId, Long korisnikId);

}
