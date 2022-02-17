package com.ftn.Teretana.service.impl;

import java.time.LocalDateTime;
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
	public List<TerminTreninga> findAll() {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findAll();
	}

	@Override
	public TerminTreninga findOne(Long id) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findOne(id);
	}

	@Override
	public TerminTreninga save(TerminTreninga terminTreninga) {
		// TODO Auto-generated method stub
		terminTreningaDAO.save(terminTreninga);
		return terminTreninga;
	}

	@Override
	public List<TerminTreninga> findTrening(Long id, LocalDateTime datum) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findTrening(id, datum);
	}

}
