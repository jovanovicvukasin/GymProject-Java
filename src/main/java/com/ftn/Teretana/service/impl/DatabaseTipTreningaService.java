package com.ftn.Teretana.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.TipTreningaDAO;
import com.ftn.Teretana.model.TipTreninga;
import com.ftn.Teretana.service.TipTreningaService;

@Service
public class DatabaseTipTreningaService implements TipTreningaService{

	@Autowired
	private TipTreningaDAO tripTreningaDAO;
	
	@Override
	public TipTreninga findOne(Long id) {
		// TODO Auto-generated method stub
		return tripTreningaDAO.findOne(id);
	}

	@Override
	public List<TipTreninga> findAll() {
		// TODO Auto-generated method stub
		return tripTreningaDAO.findAll();
	}

	@Override
	public List<TipTreninga> find(String ime, String opis) {
		// TODO Auto-generated method stub
		if (ime == null && opis == null) {
			return tripTreningaDAO.findAll();
		}
		return tripTreningaDAO.find(ime, opis);
	}

	@Override
	public List<TipTreninga> find(Long[] ids) {
		// TODO Auto-generated method stub
		List<TipTreninga> rezultat = new ArrayList<>();
		for (Long id: ids) {
			TipTreninga tt = tripTreningaDAO.findOne(id);
			rezultat.add(tt);
		}

		return rezultat;
	}

}
