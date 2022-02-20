package com.ftn.Teretana.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.TreningDAO;
import com.ftn.Teretana.model.Trening;
import com.ftn.Teretana.service.TreningService;

@Service
public class DatabaseTreningService implements TreningService {

	@Autowired
	private TreningDAO treningDAO;
	
	@Override
	public List<Trening> findAll() {
		// TODO Auto-generated method stub
		return treningDAO.findAll();
	}

	@Override
	public List<Trening> find(String naziv, Long tipTreningaId, String trener, Double cenaOd, Double cenaDo,
			String vrstaTreninga, String nivoTreninga) {
		// TODO Auto-generated method stub
		return treningDAO.find(naziv, tipTreningaId, trener, cenaOd, cenaDo, vrstaTreninga, nivoTreninga);
	}

	@Override
	public Trening findOne(Long id) {
		// TODO Auto-generated method stub
		return treningDAO.findOne(id);
	}

	@Override
	public Trening edit(Trening trening) {
		// TODO Auto-generated method stub
		treningDAO.edit(trening);
		return trening;
	}

	@Override
	public Trening save(Trening trening) {
		// TODO Auto-generated method stub
		treningDAO.save(trening);
		return trening;
	}

	@Override
	public List<Trening> find(Long[] ids) {
		// TODO Auto-generated method stub
		List<Trening> rez = new ArrayList<>();
		for(Long id: ids) {
			Trening t = treningDAO.findOne(id);
			rez.add(t);
		}
		return rez;
	}

	@Override
	public Trening update(Trening trening) {
		// TODO Auto-generated method stub
		treningDAO.update(trening);
		return trening;
	}

}
