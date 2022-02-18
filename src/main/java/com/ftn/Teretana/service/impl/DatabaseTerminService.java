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

	@Override
	public List<TerminTreninga> findSalaDatum(Long id, LocalDateTime datumOd, LocalDateTime datumDo, Long id1, LocalDateTime datumDo1, LocalDateTime datumOd1, LocalDateTime datumDo2, LocalDateTime datumOd2) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findSalaDatum(id, datumOd, datumDo, id1, datumDo1, datumOd1, datumDo2, datumOd2);
	}

	@Override
	public List<TerminTreninga> findTerminSala(Long id) {
		// TODO Auto-generated method stub
		return terminTreningaDAO.findTerminSala(id);
	}

}
