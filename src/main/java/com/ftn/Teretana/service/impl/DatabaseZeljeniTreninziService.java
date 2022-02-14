package com.ftn.Teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.ZeljeniTreninziDAO;
import com.ftn.Teretana.model.ZeljeniTreninzi;
import com.ftn.Teretana.service.ZeljeniTreninziService;

@Service
public class DatabaseZeljeniTreninziService implements ZeljeniTreninziService {

	@Autowired
	private ZeljeniTreninziDAO zeljeniTreninziDAO;
	
	@Override
	public ZeljeniTreninzi save(ZeljeniTreninzi zeljeni) {
		// TODO Auto-generated method stub
		zeljeniTreninziDAO.save(zeljeni);
		return zeljeni;
	}

	@Override
	public ZeljeniTreninzi delete(Long id) {
		// TODO Auto-generated method stub
		ZeljeniTreninzi zt = findOne(id);
		if(zt!=null) {
			zeljeniTreninziDAO.delete(id);
		}
		return zt;
	}

	@Override
	public ZeljeniTreninzi findOne(Long id) {
		// TODO Auto-generated method stub
		return zeljeniTreninziDAO.findOne(id);
	}

	@Override
	public List<ZeljeniTreninzi> find(Long treningId, Long korisnikId) {
		// TODO Auto-generated method stub
		return zeljeniTreninziDAO.find(treningId, korisnikId);
	}

}
