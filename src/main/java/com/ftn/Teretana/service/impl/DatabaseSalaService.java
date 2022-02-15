package com.ftn.Teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.SalaDAO;
import com.ftn.Teretana.model.Sala;
import com.ftn.Teretana.service.SalaService;

@Service
public class DatabaseSalaService implements SalaService {
	
	@Autowired
	private SalaDAO salaDAO;

	@Override
	public List<Sala> findAll() {
		// TODO Auto-generated method stub
		return salaDAO.findAll();
	}

	@Override
	public List<Sala> findOznakaSale(String oznakaSale) {
		// TODO Auto-generated method stub
		return salaDAO.findOznakaSale(oznakaSale);
	}

	@Override
	public Sala findOne(Long id) {
		// TODO Auto-generated method stub
		return salaDAO.findOne(id);
	}

	@Override
	public Sala save(Sala sala) {
		// TODO Auto-generated method stub
		salaDAO.save(sala);
		return sala;
	}

	@Override
	public Sala findOne(String oznakaSale) {
		// TODO Auto-generated method stub
		return salaDAO.findOne(oznakaSale);
	}

	
}
