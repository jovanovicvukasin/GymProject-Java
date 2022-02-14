package com.ftn.Teretana.service;

import java.util.List;

import com.ftn.Teretana.model.ZeljeniTreninzi;

public interface ZeljeniTreninziService {
	
	ZeljeniTreninzi save(ZeljeniTreninzi zeljeni);
	ZeljeniTreninzi delete(Long id);
	ZeljeniTreninzi findOne(Long id);
	List<ZeljeniTreninzi> find(Long treningId, Long korisnikId);

}
