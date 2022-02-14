package com.ftn.Teretana.service.impl;

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

	

}
