package com.ftn.Teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.Teretana.dao.TerminTreningaDAO;
import com.ftn.Teretana.model.TerminTreninga;
import com.ftn.Teretana.service.TerminService;

@Service
public class DatabaseTerminService implements TerminService {
	
	@Autowired
	private TerminTreningaDAO terminTreningaDAO;

	@Override
	public List<TerminTreninga> findTrening(Long id) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findTrening(id);
	}

	@Override
	public List<TerminTreninga> findAll() {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findAll();
	}

	@Override
	public TerminTreninga findOne(Long id) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findOne(id);
	}

}
