package com.ftn.Teretana.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.Teretana.model.TerminTreninga;

public interface TerminTreningaDAO {
	
	public List<TerminTreninga> findAll();
	public List<TerminTreninga> findTrening(Long id, LocalDateTime datum);
	public TerminTreninga findOne(Long id);
	public void save(TerminTreninga terminTreninga);
	public List<TerminTreninga> findSalaDatum(Long id, LocalDateTime datumOd, LocalDateTime datumDo, Long id1, LocalDateTime datumDo1, LocalDateTime datumOd1, LocalDateTime datumDo2, LocalDateTime datumOd2);
	public List<TerminTreninga> findTerminSala(Long id);
	public int updateKapacitet(TerminTreninga terminTreninga);
 


}
