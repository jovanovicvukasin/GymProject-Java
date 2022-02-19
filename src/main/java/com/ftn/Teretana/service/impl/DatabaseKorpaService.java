package com.ftn.Teretana.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.KorpaDAO;
import com.ftn.Teretana.model.Korpa;
import com.ftn.Teretana.service.KorpaService;

@Service
public class DatabaseKorpaService implements KorpaService {
	
	@Autowired
	private KorpaDAO korpaDAO;

	@Override
	public Korpa save(Korpa korpa) {
		// TODO Auto-generated method stub
		korpaDAO.save(korpa);
		return korpa;
	}

	@Override
	public List<Korpa> findAll() {
		// TODO Auto-generated method stub
		return korpaDAO.findAll();
	}

	@Override
	public Korpa findOne(Long id) {
		// TODO Auto-generated method stub
		return korpaDAO.findOne(id);
	}

	@Override
	public List<Korpa> findForOne(Long id) {
		// TODO Auto-generated method stub
		return korpaDAO.findForOne(id);
	}

	@Override
	public Korpa delete(Long id) {
		// TODO Auto-generated method stub
		Korpa korpa = findOne(id);
		if(korpa != null) {
			korpaDAO.delete(id);
		}
		return korpa;
		
	}

	@Override
	public List<Korpa> find(Long terminId, Long korisnikId, Double cena, Boolean aktivna) {
		// TODO Auto-generated method stub
		return korpaDAO.find(terminId, korisnikId, cena, aktivna);
	}

	@Override
	public List<Korpa> find(Long[] ids) {
		// TODO Auto-generated method stub
		List<Korpa> rezultat = new ArrayList<>();
		for(Long id : ids) {
			Korpa korpa = korpaDAO.findOne(id);
			rezultat.add(korpa);
		}
		return rezultat;
	}

	@Override
	public Korpa update(Korpa korpa) {
		// TODO Auto-generated method stub
		korpaDAO.update(korpa);
		return korpa;
	}

	@Override
	public List<Korpa> findForOne(boolean aktivna, Long id) {
		// TODO Auto-generated method stub
		return korpaDAO.findForOne(aktivna, id);

	}

	

}
