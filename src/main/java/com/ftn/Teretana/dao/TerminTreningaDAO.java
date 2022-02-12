package com.ftn.Teretana.dao;

import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminTreningaDAO {
	
	public List<TerminTreninga> findAll();
	public List<TerminTreninga> findTrening(Long id);
	


}
