package com.ftn.Teretana.service.impl;

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

}
