package com.ftn.Teretana.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminTreningaDAO {
	
	public List<TerminTreninga> findAll();
	public List<TerminTreninga> findTrening(Long id, LocalDateTime datum);
	public TerminTreninga findOne(Long id);
	public void save(TerminTreninga terminTreninga);


}
